package origin.insuranceplanner.domain.model

data class PersonalInformation(
        val age: Int,
        val dependents: Int,
        val income: Int,
        val maritalStatus: MaritalStatusEnum,
        val riskQuestions: List<Int>,
        val house: House?,
        val vehicle: Vehicle?
)

enum class MaritalStatusEnum {
    SINGLE, MARRIED
}

data class House(
        val ownershipStatus: OwnershipStatusEnum
)

enum class OwnershipStatusEnum {
    OWNED, MORTGAGED
}

data class Vehicle(
        val year: Int
)