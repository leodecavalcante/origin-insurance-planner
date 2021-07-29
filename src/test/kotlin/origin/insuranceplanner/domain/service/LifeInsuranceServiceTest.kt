package origin.insuranceplanner.domain.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.MaritalStatusEnum
import origin.insuranceplanner.domain.model.RiskScore

@SpringBootTest
internal class LifeInsuranceServiceTest {

    @Autowired
    private lateinit var lifeInsuranceService: LifeInsuranceService

    @Test
    fun `should return ineligible life risk score`() {
        val riskScore = lifeInsuranceService.plan(0, 61, MaritalStatusEnum.MARRIED, 1)

        Assertions.assertEquals(RiskScore.INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic life risk score`() {
        val riskScore = lifeInsuranceService.plan(-1, 42, MaritalStatusEnum.MARRIED, 0)

        Assertions.assertEquals(RiskScore.ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular life risk score`() {
        val riskScore = lifeInsuranceService.plan(0, 42, MaritalStatusEnum.MARRIED, 1)

        Assertions.assertEquals(RiskScore.REGULAR, riskScore)
    }

    @Test
    fun `should return responsible life risk score`() {
        val riskScore = lifeInsuranceService.plan(1, 42, MaritalStatusEnum.MARRIED, 1)

        Assertions.assertEquals(RiskScore.RESPONSIBLE, riskScore)
    }
}