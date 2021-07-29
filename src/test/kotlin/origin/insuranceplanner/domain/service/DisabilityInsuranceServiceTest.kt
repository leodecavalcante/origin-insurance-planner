package origin.insuranceplanner.domain.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.House
import origin.insuranceplanner.domain.model.MaritalStatusEnum
import origin.insuranceplanner.domain.model.OwnershipStatusEnum
import origin.insuranceplanner.domain.model.RiskScore

@SpringBootTest
internal class DisabilityInsuranceServiceTest {

    @Autowired
    private lateinit var disabilityRiskScoreService: DisabilityInsuranceService

    @Test
    fun `should return ineligible disability risk score when income is zero`() {
        val riskScore = disabilityRiskScoreService.plan(0, 0, 35, MaritalStatusEnum.SINGLE, 0, null)

        Assertions.assertEquals(RiskScore.INELIGIBLE, riskScore)
    }

    @Test
    fun `should return ineligible disability risk score for income zero`() {
        val riskScore = disabilityRiskScoreService.plan(0, 10000, 61, MaritalStatusEnum.SINGLE, 0, null)

        Assertions.assertEquals(RiskScore.INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic disability risk score`() {
        val riskScore = disabilityRiskScoreService.plan(0, 10000, 35, MaritalStatusEnum.SINGLE, 0, null)

        Assertions.assertEquals(RiskScore.ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular disability risk score`() {
        val riskScore = disabilityRiskScoreService.plan(1, 10000, 35, MaritalStatusEnum.MARRIED, 1, null)

        Assertions.assertEquals(RiskScore.REGULAR, riskScore)
    }

    @Test
    fun `should return responsible disability risk score`() {
        val riskScore = disabilityRiskScoreService.plan(2, 10000, 35,
            MaritalStatusEnum.MARRIED, 1, House(OwnershipStatusEnum.MORTGAGED)
        )

        Assertions.assertEquals(RiskScore.RESPONSIBLE, riskScore)
    }
}