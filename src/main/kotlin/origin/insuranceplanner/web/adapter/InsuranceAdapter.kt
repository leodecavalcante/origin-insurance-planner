package origin.insuranceplanner.web.adapter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import origin.insuranceplanner.web.dto.PlanInsuranceRequest
import origin.insuranceplanner.web.dto.PlanInsuranceResponse
import origin.insuranceplanner.web.port.InsurancePort

@RestController
@RequestMapping("/insurance")
class InsuranceAdapter(val insurancePort: InsurancePort) {

    val logger: Logger = LoggerFactory.getLogger(InsuranceAdapter::class.java)

    @GetMapping("/plan")
    fun plan(@RequestBody request: PlanInsuranceRequest): PlanInsuranceResponse {
        logger.info("Receiving request: $request")
        return insurancePort.plan(request)
    }
}