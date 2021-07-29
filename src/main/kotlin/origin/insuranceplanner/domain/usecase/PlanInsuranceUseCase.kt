package origin.insuranceplanner.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.*
import origin.insuranceplanner.domain.model.MaritalStatusEnum.MARRIED
import origin.insuranceplanner.domain.model.OwnershipStatusEnum.MORTGAGED
import origin.insuranceplanner.domain.model.RiskScore.*
import java.time.LocalDate

@Service
class PlanInsuranceUseCase {

    val logger: Logger = LoggerFactory.getLogger(PlanInsuranceUseCase::class.java)

    fun provideInsurancePlan(personalInformation: PersonalInformation): RiskProfile {
        val baseScore = calculateBaseScore(personalInformation)
        logger.info("Base score is: $baseScore")
        val autoRiskScore = planAutoRiskScore(baseScore, personalInformation.vehicle)
        logger.info("Auto risk score is: $autoRiskScore")
        val disabilityRiskScore = planDisabilityRiskScore(
            baseScore,
            personalInformation.income,
            personalInformation.age,
            personalInformation.maritalStatus,
            personalInformation.dependents,
            personalInformation.house
        )
        logger.info("Disability risk score is: $disabilityRiskScore")
        val homeRiskScore = planHomeRiskScore(baseScore, personalInformation.house)
        logger.info("Home risk score is: $homeRiskScore")
        val lifeRiskScore = planLifeRiskScore(
            baseScore,
            personalInformation.age,
            personalInformation.maritalStatus,
            personalInformation.dependents
        )
        logger.info("Life risk score is: $lifeRiskScore")
        return RiskProfile(autoRiskScore, disabilityRiskScore, homeRiskScore, lifeRiskScore)
    }

    protected fun calculateBaseScore(personalInformation: PersonalInformation): Int {
        var baseScore = 0

        personalInformation.riskQuestions.forEach { baseScore += it }

        when {
            personalInformation.age < 30 -> baseScore -= 2
            personalInformation.age in 30..40 -> baseScore -= 1
        }

        if (personalInformation.income > 200000) {
            baseScore -= 1
        }

        return baseScore
    }

    protected fun planAutoRiskScore(baseScore: Int, vehicle: Vehicle?): RiskScore {
        var score = baseScore

        vehicle?.let {
            if (it.year > LocalDate.now().year - 5) {
                score += 1
            }
        } ?: return INELIGIBLE

        return processScore(score)
    }

    protected fun planDisabilityRiskScore(
        baseScore: Int,
        income: Int,
        age: Int,
        maritalStatus: MaritalStatusEnum,
        dependents: Int,
        house: House?
    ): RiskScore {
        var score = baseScore

        if (income == 0 || age > 60) {
            return INELIGIBLE
        }

        if (maritalStatus == MARRIED) {
            score -= 1
        }

        if (dependents > 0) {
            score += 1
        }

        house?.let {
            if (it.ownershipStatus == MORTGAGED) {
                score += 1
            }
        }

        return processScore(score)
    }

    protected fun planHomeRiskScore(baseScore: Int, house: House?): RiskScore {
        var score = baseScore

        house?.let {
            if (it.ownershipStatus == MORTGAGED) {
                score += 1
            }
        } ?: return INELIGIBLE

        return processScore(score)
    }

    protected fun planLifeRiskScore(
        baseScore: Int,
        age: Int,
        maritalStatus: MaritalStatusEnum,
        dependents: Int
    ): RiskScore {
        var score = baseScore

        if (age > 60) {
            return INELIGIBLE
        }

        if (maritalStatus == MARRIED) {
            score += 1
        }

        if (dependents > 0) {
            score += 1
        }

        return processScore(score)
    }

    protected fun processScore(baseScore: Int): RiskScore {
        return when (baseScore) {
            in Int.MIN_VALUE..0 -> ECONOMIC
            in 1..2 -> REGULAR
            else -> RESPONSIBLE
        }
    }
}