package origin.insuranceplanner.web.port

import org.springframework.stereotype.Component
import origin.insuranceplanner.web.dto.PlanInsuranceRequest
import origin.insuranceplanner.web.dto.PlanInsuranceResponse
import origin.insuranceplanner.domain.usecase.PlanInsuranceUseCase

@Component
class InsurancePort(val planInsuranceUseCase: PlanInsuranceUseCase) {

    fun plan(request: PlanInsuranceRequest): PlanInsuranceResponse {
        return PlanInsuranceResponse.fromRiskProfile(planInsuranceUseCase.provideInsurancePlan(request.toPersonalInformation()))
    }
}