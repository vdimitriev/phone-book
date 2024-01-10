package mk.dmt.pb.http

import mk.dmt.pb.model.OutputMessage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-h2database")
class HttpRequestTest {

    @LocalServerPort
    private val port = 0

    @Autowired
    val restTemplate: TestRestTemplate? = null

    @Test
    @Order(1)
    fun testBookPhone() {
        val response: ResponseEntity<Any> = restTemplate!!.exchange(
            String.format("http://localhost:%d/api/v1/phone/book/samsung-galaxy-s9/f631aebd-9f76-4e83-9d54-6a902acd8769", port),
            HttpMethod.PUT,
            null,
            Any::class.java)

        Assertions.assertNotNull(response)
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        Assertions.assertEquals(null, response.body)
    }

    @Test
    @Order(2)
    fun testReturnPhone() {
        val response: ResponseEntity<Any> = restTemplate!!.exchange(
            String.format("http://localhost:%d/api/v1/phone/return/samsung-galaxy-s9", port),
            HttpMethod.PUT,
            null,
            Any::class.java)

        Assertions.assertNotNull(response)
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        Assertions.assertEquals(null, response.body)
    }

    @Test
    @Order(3)
    fun testPhoneInfo() {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
        restTemplateExchange(4, headers)
//        Assertions.assertNotNull(response)
//        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
//        Assertions.assertNotEquals(null, response.body)
//        Assertions.assertNotNull(response.body!!.phones)
//        Assertions.assertFalse(response.body!!.phones.isEmpty())
//        Assertions.assertTrue(response.body!!.phones.all { it.history.isNotEmpty() })
    }

    fun restTemplateExchange(count:Int, headers: HttpHeaders) {
        for(c in 0 until count) {
            restTemplate!!.exchange(
                String.format("http://localhost:%d/api/v1/phone/samsung-galaxy-s9", port),
                HttpMethod.GET,
                HttpEntity<Any>(headers),
                OutputMessage::class.java
            )
        }
    }

}