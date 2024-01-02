package mk.dmt.pb.h2

import mk.dmt.pb.controller.PhoneController
import org.springframework.boot.test.context.SpringBootTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("test-h2database")
class SmokeH2Test() {

    @Autowired
    lateinit var phoneController: PhoneController

    @Test
    fun contextLoads() {
        assertThat(phoneController).isNotNull()
    }

}