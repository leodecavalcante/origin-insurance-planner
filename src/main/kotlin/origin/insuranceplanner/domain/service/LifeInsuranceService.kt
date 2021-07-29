package origin.insuranceplanner.domain.service

import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.MaritalStatusEnum
import origin.insuranceplanner.domain.model.RiskScore

@Service
class LifeInsuranceService(val scoreService: ScoreService) {
    fun plan(
        baseScore: Int,
        age: Int,
        maritalStatus: MaritalStatusEnum,
        dependents: Int
    ): RiskScore {
        var score = baseScore

        if (age > 60) {
            return RiskScore.INELIGIBLE
        }

        if (maritalStatus == MaritalStatusEnum.MARRIED) {
            score += 1
        }

        if (dependents > 0) {
            score += 1
        }

        return scoreService.processScore(score)
    }
}