package origin.insuranceplanner.domain.service

import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.PersonalInformation
import origin.insuranceplanner.domain.model.RiskScore

@Service
class ScoreService {
    fun calculateBaseScore(personalInformation: PersonalInformation): Int {
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

    fun processScore(score: Int): RiskScore {
        return when (score) {
            in Int.MIN_VALUE..0 -> RiskScore.ECONOMIC
            in 1..2 -> RiskScore.REGULAR
            else -> RiskScore.RESPONSIBLE
        }
    }
}