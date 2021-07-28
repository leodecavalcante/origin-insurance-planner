package origin.insuranceplanner.application.port

import org.springframework.stereotype.Component
import origin.insuranceplanner.application.dto.PlanInsuranceRequest
import origin.insuranceplanner.application.dto.PlanInsuranceResponse
import origin.insuranceplanner.domain.usecase.PlanInsuranceUseCase

@Component
class InsurancePort(val planInsuranceUseCase: PlanInsuranceUseCase) {

    fun plan(request: PlanInsuranceRequest): PlanInsuranceResponse {
        return PlanInsuranceResponse.fromRiskProfile(planInsuranceUseCase.provideInsurancePlan(request.toPersonalInformation()))
    }
}