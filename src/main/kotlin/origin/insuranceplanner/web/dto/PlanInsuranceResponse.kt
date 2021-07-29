package origin.insuranceplanner.web.dto

import origin.insuranceplanner.domain.model.RiskProfile

data class PlanInsuranceResponse(
    val auto: String,
    val disability: String,
    val home: String,
    val life: String
) {
    companion object {
        fun fromRiskProfile(riskProfile: RiskProfile) = PlanInsuranceResponse(
            riskProfile.auto.name.lowercase(),
            riskProfile.disability.name.lowercase(),
            riskProfile.home.name.lowercase(),
            riskProfile.life.name.lowercase()
        )
    }
}