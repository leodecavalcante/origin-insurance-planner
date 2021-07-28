package origin.insuranceplanner.domain.usecase

import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.MaritalStatusEnum.MARRIED
import origin.insuranceplanner.domain.model.OwnershipStatusEnum
import origin.insuranceplanner.domain.model.OwnershipStatusEnum.MORTGAGED
import origin.insuranceplanner.domain.model.PersonalInformation
import origin.insuranceplanner.domain.model.RiskProfile
import origin.insuranceplanner.domain.model.RiskScore
import origin.insuranceplanner.domain.model.RiskScore.*
import java.time.LocalDate

@Service
class PlanInsuranceUseCase {

//    private Logger.getLogger("PlanInsuranceUseCase")


    private val maxIncomeWithoutDeduction: Int = 200000

    // age < 30 - 2 all
    // between 30 and 40 - 1 all
    // income > 200k - 1 all
    fun provideInsurancePlan(personalInformation: PersonalInformation): RiskProfile {
        var baseScore = 0

        personalInformation.riskQuestions.forEach { baseScore += it }

        when {
            personalInformation.age < 30 -> baseScore -= 2
            personalInformation.age in 30..40 -> baseScore -= 1
        }

        if (personalInformation.income > maxIncomeWithoutDeduction) {
            baseScore -= 1
        }

//        Config.log.println("Base score is: baseScore")
        val autoRiskScore = planAutoRiskScore(personalInformation, baseScore)
//        Config.log.println("Auto risk score is: $autoRiskScore")
        val disabilityRiskScore = planDisabilityRiskScore(personalInformation, baseScore)
//        Config.log.println("Disability risk score is: $disabilityRiskScore")
        val homeRiskScore = planHomeRiskScore(personalInformation, baseScore)
//        Config.log.println("Home risk score is: $homeRiskScore")
        val lifeRiskScore = planLifeRiskScore(personalInformation, baseScore)
//        Config.log.println("Life risk score is: $lifeRiskScore")

        return RiskProfile(autoRiskScore, disabilityRiskScore, homeRiskScore, lifeRiskScore)
    }

    // 0 vehicles  ineligible for auto
    // vehicle year < LocalDate.year - 5 + 1 auto
    private fun planAutoRiskScore(personalInformation: PersonalInformation, baseScore: Int): RiskScore {
        var score = baseScore

        personalInformation.vehicle?.let {
            if (it.year > LocalDate.now().year - 5) {
                score += 1
            }
        } ?: return INELIGIBLE

        return processScore(score)
    }

    // 0 income ineligible for disability
    // age > 60 ineligible for disability and life
    // maritalstatus married + 1 life and - 1 disability
    // dependents > 0 + 1 disability and life
    // house mortgaged + 1 home and disability
    private fun planDisabilityRiskScore(personalInformation: PersonalInformation, baseScore: Int): RiskScore {
        var score = baseScore

        if (personalInformation.income == 0 || isAgeIneligible(personalInformation.age)) {
            return INELIGIBLE
        }

        if (personalInformation.maritalStatus == MARRIED) {
            score -= 1
        }

        if (personalInformation.dependents > 0) {
            score += 1
        }

        personalInformation.house?.let {
            if (it.ownershipStatus == OwnershipStatusEnum.MORTGAGED) {
                score += 1
            }
        }

        return processScore(score)
    }

    // house mortgaged + 1 home and disability
    // 0 houses ineligible for home
    private fun planHomeRiskScore(personalInformation: PersonalInformation, baseScore: Int): RiskScore {
        var score = baseScore

        personalInformation.house?.let {
            if (personalInformation.house.ownershipStatus == MORTGAGED){
                score += 1
            }
        } ?: return INELIGIBLE

        return processScore(score)
    }

    // age > 60 ineligible for disability and life
    // maritalstatus married + 1 life and - 1 disability
    // dependents > 0 + 1 disability and life
    private fun planLifeRiskScore(personalInformation: PersonalInformation, baseScore: Int): RiskScore {
        var score = baseScore

        if (isAgeIneligible(personalInformation.age)) {
            return INELIGIBLE
        }

        if (personalInformation.maritalStatus == MARRIED) {
            score += 1
        }

        if (personalInformation.dependents > 0) {
            score += 1
        }

        return processScore(score)
    }

    private fun isAgeIneligible(age: Int): Boolean {
        return age > 60
    }

    private fun processScore(baseScore: Int): RiskScore {
        return when (baseScore) {
            in Int.MIN_VALUE..0 -> ECONOMIC
            in 1..2 -> REGULAR
            else -> RESPONSIBLE
        }
    }
}