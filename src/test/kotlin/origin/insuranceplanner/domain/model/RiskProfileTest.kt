package origin.insuranceplanner.domain.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RiskProfileTest{

    @Test
    fun `should return risk score economic`() {
        val riskScore = RiskScore.processScore(0)

        Assertions.assertEquals(RiskScore.ECONOMIC, riskScore)
    }

    @Test
    fun `should return risk score regular`() {
        val riskScore = RiskScore.processScore(1)

        Assertions.assertEquals(RiskScore.REGULAR, riskScore)
    }

    @Test
    fun `should return risk score responsible`() {
        val riskScore = RiskScore.processScore(3)

        Assertions.assertEquals(RiskScore.RESPONSIBLE, riskScore)
    }
}