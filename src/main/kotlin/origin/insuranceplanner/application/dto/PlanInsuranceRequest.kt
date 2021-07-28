package origin.insuranceplanner.application.dto

import origin.insuranceplanner.domain.model.*

data class PlanInsuranceRequest(
        val age: Int,
        val dependents: Int,
        val income: Int,
        val marital_status: String,
        val risk_questions: Array<Int>,
        val house: HousePlanInsuranceRequest?,
        val vehicle: VehiclePlanInsuranceRequest?
) {
    fun toPersonalInformation() = PersonalInformation(
            age,
            dependents,
            income,
            MaritalStatusEnum.valueOf(marital_status.uppercase()),
            risk_questions.toList(),
            house?.toHouse(),
            vehicle?.toVehicle()
    )
}

data class HousePlanInsuranceRequest(
        val ownership_status: String
) {
    fun toHouse() = House(OwnershipStatusEnum.valueOf(ownership_status.uppercase()))
}

data class VehiclePlanInsuranceRequest(
        val year: Int
) {
    fun toVehicle() = Vehicle(year)
}
