package origin.insuranceplanner.domain.service

import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.House
import origin.insuranceplanner.domain.model.OwnershipStatusEnum
import origin.insuranceplanner.domain.model.RiskScore

@Service
class HomeInsuranceService(val scoreService: ScoreService) {
    fun plan(baseScore: Int, house: House?): RiskScore {
        var score = baseScore

        house?.let {
            if (it.ownershipStatus == OwnershipStatusEnum.MORTGAGED) {
                score += 1
            }
        } ?: return RiskScore.INELIGIBLE

        return scoreService.processScore(score)
    }
}