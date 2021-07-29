package origin.insuranceplanner.domain.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.House
import origin.insuranceplanner.domain.model.OwnershipStatusEnum
import origin.insuranceplanner.domain.model.RiskScore

@SpringBootTest
internal class HomeInsuranceServiceTest {
    
    @Autowired
    private lateinit var homeInsuranceService: HomeInsuranceService

    @Test
    fun `should return ineligible home risk score`() {
        val riskScore = homeInsuranceService.plan(0, null)

        Assertions.assertEquals(RiskScore.INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic home risk score`() {
        val riskScore = homeInsuranceService.plan(0, House(OwnershipStatusEnum.OWNED))

        Assertions.assertEquals(RiskScore.ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular home risk score`() {
        val riskScore = homeInsuranceService.plan(2, House(OwnershipStatusEnum.OWNED))

        Assertions.assertEquals(RiskScore.REGULAR, riskScore)
    }

    @Test
    fun `should return responsible home risk score`() {
        val riskScore = homeInsuranceService.plan(2, House(OwnershipStatusEnum.MORTGAGED))

        Assertions.assertEquals(RiskScore.RESPONSIBLE, riskScore)
    }
}