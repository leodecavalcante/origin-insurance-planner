package origin.insuranceplanner.domain.model

data class RiskProfile(
    val auto: RiskScore,
    val disability: RiskScore,
    val home: RiskScore,
    val life: RiskScore
)

enum class RiskScore {
    ECONOMIC, REGULAR, RESPONSIBLE, INELIGIBLE
}