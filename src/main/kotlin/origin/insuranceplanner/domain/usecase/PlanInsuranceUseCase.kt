package origin.insuranceplanner.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.PersonalInformation
import origin.insuranceplanner.domain.model.RiskProfile
import origin.insuranceplanner.domain.service.*

@Service
class PlanInsuranceUseCase(
    val baseScoreService: BaseScoreService,
    val autoInsuranceService: AutoInsuranceService,
    val disabilityInsuranceService: DisabilityInsuranceService,
    val homeRiskScoreService: HomeInsuranceService,
    val lifeRiskScoreService: LifeInsuranceService
) {
    val logger: Logger = LoggerFactory.getLogger(PlanInsuranceUseCase::class.java)

    fun provideInsurancePlan(personalInformation: PersonalInformation): RiskProfile {
        val baseScore = baseScoreService.calculate(personalInformation)
        logger.info("Base score is: $baseScore")

        val autoRiskScore = autoInsuranceService.plan(baseScore, personalInformation.vehicle)
        logger.info("Auto risk score is: $autoRiskScore")

        val disabilityRiskScore = disabilityInsuranceService.plan(
            baseScore,
            personalInformation.income,
            personalInformation.age,
            personalInformation.maritalStatus,
            personalInformation.dependents,
            personalInformation.house
        )
        logger.info("Disability risk score is: $disabilityRiskScore")

        val homeRiskScore = homeRiskScoreService.plan(baseScore, personalInformation.house)
        logger.info("Home risk score is: $homeRiskScore")

        val lifeRiskScore = lifeRiskScoreService.plan(
            baseScore,
            personalInformation.age,
            personalInformation.maritalStatus,
            personalInformation.dependents
        )
        logger.info("Life risk score is: $lifeRiskScore")

        return RiskProfile(autoRiskScore, disabilityRiskScore, homeRiskScore, lifeRiskScore)
    }
}