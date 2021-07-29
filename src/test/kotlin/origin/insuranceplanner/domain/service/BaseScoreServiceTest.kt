package origin.insuranceplanner.domain.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.insuranceplanner.domain.model.*

@SpringBootTest
internal class BaseScoreServiceTest {

    @Autowired
    private lateinit var baseScoreService: BaseScoreService

    @Test
    fun `should return base score zero`() {
        val personalInformation = PersonalInformation(35, 2, 0, MaritalStatusEnum.MARRIED, listOf(0, 1, 0), House(
            OwnershipStatusEnum.OWNED
        ), Vehicle(2018)
        )
        val baseScore = baseScoreService.calculate(personalInformation)

        Assertions.assertEquals(0, baseScore)
    }
}