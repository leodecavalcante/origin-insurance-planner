package origin.insuranceplanner.domain.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.RiskScore
import origin.insuranceplanner.domain.model.Vehicle
import java.time.LocalDate

@SpringBootTest
internal class AutoInsuranceServiceTest {

    @Autowired
    private lateinit var autoInsuranceService: AutoInsuranceService

    @Test
    fun `should return ineligible auto risk score`() {
        val riskScore = autoInsuranceService.plan(0, null)

        Assertions.assertEquals(RiskScore.INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic auto risk score`() {
        val riskScore = autoInsuranceService.plan(-1, Vehicle(LocalDate.now().year - 1))

        Assertions.assertEquals(RiskScore.ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular auto risk score`() {
        val riskScore = autoInsuranceService.plan(1, Vehicle(LocalDate.now().year - 1))

        Assertions.assertEquals(RiskScore.REGULAR, riskScore)
    }

    @Test
    fun `should return responsible auto risk score`() {
        val riskScore = autoInsuranceService.plan(2, Vehicle(LocalDate.now().year - 1))

        Assertions.assertEquals(RiskScore.RESPONSIBLE, riskScore)
    }
}