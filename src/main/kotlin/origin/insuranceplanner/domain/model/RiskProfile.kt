package origin.insuranceplanner.domain.model

data class RiskProfile(
    val auto: RiskScore,
    val disability: RiskScore,
    val home: RiskScore,
    val life: RiskScore
)

enum class RiskScore {
    ECONOMIC, REGULAR, RESPONSIBLE, INELIGIBLE;

    companion object {
        fun processScore(score: Int): RiskScore {
            return when (score) {
                in Int.MIN_VALUE..0 -> ECONOMIC
                in 1..2 -> REGULAR
                else -> RESPONSIBLE
            }
        }
    }
}