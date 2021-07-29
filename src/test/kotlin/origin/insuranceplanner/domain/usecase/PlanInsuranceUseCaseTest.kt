package origin.insuranceplanner.domain.usecase

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.House
import origin.insuranceplanner.domain.model.MaritalStatusEnum.MARRIED
import origin.insuranceplanner.domain.model.MaritalStatusEnum.SINGLE
import origin.insuranceplanner.domain.model.OwnershipStatusEnum.MORTGAGED
import origin.insuranceplanner.domain.model.OwnershipStatusEnum.OWNED
import origin.insuranceplanner.domain.model.PersonalInformation
import origin.insuranceplanner.domain.model.RiskProfile
import origin.insuranceplanner.domain.model.RiskScore.*
import origin.insuranceplanner.domain.model.Vehicle
import java.time.LocalDate

@SpringBootTest
internal class PlanInsuranceUseCaseTest : PlanInsuranceUseCase() {

    @Test
    fun `should return risk profile`() {
        val personalInformation = PersonalInformation(35, 2, 0, MARRIED, listOf(0, 1, 0), House(OWNED), Vehicle(2018))
        val expectedRiskProfile = RiskProfile(REGULAR, INELIGIBLE, ECONOMIC, REGULAR)

        val riskProfile = provideInsurancePlan(personalInformation)

        Assertions.assertEquals(expectedRiskProfile, riskProfile)
    }

    @Test
    fun `should return base score zero`() {
        val personalInformation = PersonalInformation(35, 2, 0, MARRIED, listOf(0, 1, 0), House(OWNED), Vehicle(2018))
        val baseScore = calculateBaseScore(personalInformation)

        Assertions.assertEquals(0, baseScore)
    }

    @Test
    fun `should return base score minus one when `() {
        val personalInformation =
            PersonalInformation(35, 2, 200001, MARRIED, listOf(0, 1, 0), House(OWNED), Vehicle(2018))
        val baseScore = calculateBaseScore(personalInformation)

        Assertions.assertEquals(-1, baseScore)
    }

    @Test
    fun `should return ineligible auto risk score`() {
        val riskScore = planAutoRiskScore(0, null)

        Assertions.assertEquals(INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic auto risk score`() {
        val riskScore = planAutoRiskScore(-1, Vehicle(LocalDate.now().year - 1))

        Assertions.assertEquals(ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular auto risk score`() {
        val riskScore = planAutoRiskScore(1, Vehicle(LocalDate.now().year - 1))

        Assertions.assertEquals(REGULAR, riskScore)
    }

    @Test
    fun `should return responsible auto risk score`() {
        val riskScore = planAutoRiskScore(2, Vehicle(LocalDate.now().year - 1))

        Assertions.assertEquals(RESPONSIBLE, riskScore)
    }

    @Test
    fun `should return ineligible disability risk score when income is zero`() {
        val riskScore = planDisabilityRiskScore(0, 0, 35, SINGLE, 0, null)

        Assertions.assertEquals(INELIGIBLE, riskScore)
    }

    @Test
    fun `should return ineligible disability risk score for income zero`() {
        val riskScore = planDisabilityRiskScore(0, 10000, 61, SINGLE, 0, null)

        Assertions.assertEquals(INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic disability risk score`() {
        val riskScore = planDisabilityRiskScore(0, 10000, 35, SINGLE, 0, null)

        Assertions.assertEquals(ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular disability risk score`() {
        val riskScore = planDisabilityRiskScore(1, 10000, 35, MARRIED, 1, null)

        Assertions.assertEquals(REGULAR, riskScore)
    }

    @Test
    fun `should return responsible disability risk score`() {
        val riskScore = planDisabilityRiskScore(2, 10000, 35, MARRIED, 1, House(MORTGAGED))

        Assertions.assertEquals(RESPONSIBLE, riskScore)
    }

    @Test
    fun `should return ineligible home risk score`() {
        val riskScore = planHomeRiskScore(0, null)

        Assertions.assertEquals(INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic home risk score`() {
        val riskScore = planHomeRiskScore(0, House(OWNED))

        Assertions.assertEquals(ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular home risk score`() {
        val riskScore = planHomeRiskScore(2, House(OWNED))

        Assertions.assertEquals(REGULAR, riskScore)
    }

    @Test
    fun `should return responsible home risk score`() {
        val riskScore = planHomeRiskScore(2, House(MORTGAGED))

        Assertions.assertEquals(RESPONSIBLE, riskScore)
    }

    @Test
    fun `should return ineligible life risk score`() {
        val riskScore = planLifeRiskScore(0, 61, MARRIED, 1)

        Assertions.assertEquals(INELIGIBLE, riskScore)
    }

    @Test
    fun `should return economic life risk score`() {
        val riskScore = planLifeRiskScore(-1, 42, MARRIED, 0)

        Assertions.assertEquals(ECONOMIC, riskScore)
    }

    @Test
    fun `should return regular life risk score`() {
        val riskScore = planLifeRiskScore(0, 42, MARRIED, 1)

        Assertions.assertEquals(REGULAR, riskScore)
    }

    @Test
    fun `should return risk score economic`() {
        val riskScore = processScore(0)

        Assertions.assertEquals(ECONOMIC, riskScore)
    }

    @Test
    fun `should return risk score regular`() {
        val riskScore = processScore(1)

        Assertions.assertEquals(REGULAR, riskScore)
    }

    @Test
    fun `should return risk score responsible`() {
        val riskScore = processScore(3)

        Assertions.assertEquals(RESPONSIBLE, riskScore)
    }
}