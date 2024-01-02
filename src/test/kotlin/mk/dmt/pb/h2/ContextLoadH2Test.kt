package mk.dmt.pb.h2

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test-h2database")
class ContextLoadH2Test {
    @Test
    fun contextLoads() {
    }

}