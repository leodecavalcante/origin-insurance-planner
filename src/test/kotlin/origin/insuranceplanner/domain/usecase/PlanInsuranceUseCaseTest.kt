package origin.insuranceplanner.domain.usecase

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.House
import origin.insuranceplanner.domain.model.MaritalStatusEnum.MARRIED
import origin.insuranceplanner.domain.model.OwnershipStatusEnum.OWNED
import origin.insuranceplanner.domain.model.PersonalInformation
import origin.insuranceplanner.domain.model.RiskProfile
import origin.insuranceplanner.domain.model.RiskScore.*
import origin.insuranceplanner.domain.model.Vehicle

@SpringBootTest
internal class PlanInsuranceUseCaseTest{

    @Autowired
    private lateinit var planInsuranceUseCase: PlanInsuranceUseCase

    @Test
    fun `should return risk profile`() {
        val personalInformation = PersonalInformation(35, 2, 0, MARRIED, listOf(0, 1, 0), House(OWNED), Vehicle(2018))
        val expectedRiskProfile = RiskProfile(REGULAR, INELIGIBLE, ECONOMIC, REGULAR)

        val riskProfile = planInsuranceUseCase.provideInsurancePlan(personalInformation)

        Assertions.assertEquals(expectedRiskProfile, riskProfile)
    }
}