package mk.dmt.pb.smoke

import mk.dmt.pb.controller.PhoneController
import org.springframework.boot.test.context.SpringBootTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


@SpringBootTest
class SmokeTest() {

    @Autowired
    lateinit var phoneController: PhoneController

    @Test
    fun contextLoads() {
        assertThat(phoneController).isNotNull()
    }

}