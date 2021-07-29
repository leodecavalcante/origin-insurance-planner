package origin.insuranceplanner.domain.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.*

@SpringBootTest
internal class BaseScoreServiceTest {

    @Autowired
    private lateinit var scoreService: ScoreService

    @Test
    fun `should return base score zero`() {
        val personalInformation = PersonalInformation(35, 2, 0, MaritalStatusEnum.MARRIED, listOf(0, 1, 0), House(
            OwnershipStatusEnum.OWNED
        ), Vehicle(2018)
        )
        val baseScore = scoreService.calculateBaseScore(personalInformation)

        Assertions.assertEquals(0, baseScore)
    }

    @Test
    fun `should return risk score economic`() {
        val riskScore = scoreService.processScore(0)

        Assertions.assertEquals(RiskScore.ECONOMIC, riskScore)
    }

    @Test
    fun `should return risk score regular`() {
        val riskScore = scoreService.processScore(1)

        Assertions.assertEquals(RiskScore.REGULAR, riskScore)
    }

    @Test
    fun `should return risk score responsible`() {
        val riskScore = scoreService.processScore(3)

        Assertions.assertEquals(RiskScore.RESPONSIBLE, riskScore)
    }
}