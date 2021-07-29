package origin.insuranceplanner.web.dto

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
        age = age,
        dependents = dependents,
        income = income,
        maritalStatus = MaritalStatusEnum.valueOf(marital_status.uppercase()),
        riskQuestions = risk_questions.toList(),
        house = house?.toHouse(),
        vehicle = vehicle?.toVehicle()
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
