package origin.insuranceplanner.domain.service

import org.springframework.stereotype.Service
import origin.insuranceplanner.domain.model.RiskScore
import origin.insuranceplanner.domain.model.Vehicle
import java.time.LocalDate

@Service
class AutoInsuranceService(val scoreService: ScoreService) {
    fun plan(baseScore: Int, vehicle: Vehicle?): RiskScore {
        var score = baseScore

        vehicle?.let {
            if (it.year > LocalDate.now().year - 5) {
                score += 1
            }
        } ?: return RiskScore.INELIGIBLE

        return scoreService.processScore(score)
    }
}