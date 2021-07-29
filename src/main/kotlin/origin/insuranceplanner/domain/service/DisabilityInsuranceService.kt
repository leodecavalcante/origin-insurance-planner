package origin.insuranceplanner.domain.service

import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.House
import origin.insuranceplanner.domain.model.MaritalStatusEnum
import origin.insuranceplanner.domain.model.OwnershipStatusEnum
import origin.insuranceplanner.domain.model.RiskScore

@Service
class DisabilityInsuranceService(val scoreService: ScoreService) {
    fun plan(
        baseScore: Int,
        income: Int,
        age: Int,
        maritalStatus: MaritalStatusEnum,
        dependents: Int,
        house: House?
    ): RiskScore {
        var score = baseScore

        if (income == 0 || age > 60) {
            return RiskScore.INELIGIBLE
        }

        if (maritalStatus == MaritalStatusEnum.MARRIED) {
            score -= 1
        }

        if (dependents > 0) {
            score += 1
        }

        house?.let {
            if (it.ownershipStatus == OwnershipStatusEnum.MORTGAGED) {
                score += 1
            }
        }

        return scoreService.processScore(score)
    }
}