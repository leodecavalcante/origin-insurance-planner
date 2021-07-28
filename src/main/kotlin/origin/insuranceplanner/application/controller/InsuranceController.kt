package origin.insuranceplanner.application.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import origin.insuranceplanner.application.dto.PlanInsuranceRequest
import origin.insuranceplanner.application.dto.PlanInsuranceResponse
import origin.insuranceplanner.application.port.InsurancePort

@RestController
@RequestMapping("/insurance")
class InsuranceController(val insurancePort: InsurancePort) {

    @GetMapping("/plan")
    fun plan(@RequestBody request: PlanInsuranceRequest): PlanInsuranceResponse {
//        log.println("Receiving request: $request")
        return insurancePort.plan(request)
    }
}