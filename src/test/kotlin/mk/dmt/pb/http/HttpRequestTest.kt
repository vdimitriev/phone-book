package mk.dmt.pb.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import mk.dmt.pb.model.OutputMessage
import mk.dmt.pb.repository.EventRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-h2database")
class HttpRequestTest {

    @LocalServerPort
    private val port = 0

    @Autowired
    val eventRepository: EventRepository? = null

    @Autowired
    val restTemplate: TestRestTemplate? = null

    @Autowired
    val webClient: WebTestClient? = null

//    @BeforeEach
//    fun setUp() {
//        restTemplate!!
//            .restTemplate.messageConverters = Collections.singletonList(MappingJackson2HttpMessageConverter()) as List<HttpMessageConverter<*>>
//    }

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

//    @Test
//    @Order(3)
//    fun testWebClient() {
//        val tms = "{\"phoneId\":\"samsung-galaxy-s9\",\"phones\":[{\"phone\":{\"id\":\"samsung-galaxy-s9\",\"name\":\"Samsung Galaxy S9\",\"available\":true,\"booker\":null,\"history\":[]}}]}"
//        webClient!!
//            .get()
//            .uri(String.format("http://localhost:%d/api/v1/phone/{phoneId}", port), "samsung-galaxy-s9")
//            .exchange()
//            .expectStatus().isOk()
//            .expectBody().json(tms)
//    }

    @Test
    @Order(3)
    fun testPhoneInfo() {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)

        restTemplate!!.exchange(
            String.format("http://localhost:%d/api/v1/phone/book/samsung-galaxy-s9/f631aebd-9f76-4e83-9d54-6a902acd8769", port),
            HttpMethod.PUT,
            null,
            Any::class.java)

        val response: ResponseEntity<OutputMessage> = restTemplate!!.exchange(
            String.format("http://localhost:%d/api/v1/phone/samsung-galaxy-s9", port),
            HttpMethod.GET,
            HttpEntity<Any>(headers),
            OutputMessage::class.java
        )

        Assertions.assertNotNull(response)

        println(response)
        println(response.body)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotEquals(null, response.body)

        eventRepository!!.findAll().forEach { println(it.eventId) }
    }

//    @Test
//    fun shouldReturn204ResponseAndBookPhone() {
//        val updated = PhoneModel( "samsung-galaxy-s9","Samsung Galaxy S9", true)
//        val response: ResponseEntity<PhoneModel> = restTemplate!!.exchange(
//            String.format("http://localhost:%d/api/v1/phone/book", port),
//            HttpMethod.PUT,
//            HttpEntity<PhoneModel>(updated),
//            PhoneModel::class.java
//        )
//
//        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
//        Assertions.assertThat(response.body).isNull()
//    }
//
//    @Test
//    fun shouldReturn404WhenTryingToBookNonExistingPhone() {
//        val updated = PhoneModel( "non-existing-phone","Non existing phone", true)
//        val response: ResponseEntity<PhoneModel> = restTemplate!!.exchange(
//            String.format("http://localhost:%d/api/v1/phone/book", port),
//            HttpMethod.PUT,
//            HttpEntity<PhoneModel>(updated),
//            PhoneModel::class.java
//        )
//        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
//    }
//
//    @Test
//    fun shouldReturn404WhenTryingToBookNotAvailablePhone() {
//        val phoneModel = PhoneModel( "1","Samsung Galaxy S9", true)
//        restTemplate!!.exchange(
//            String.format("http://localhost:%d/api/v1/phone/book", port),
//            HttpMethod.PUT,
//            HttpEntity<PhoneModel>(phoneModel),
//            PhoneModel::class.java
//        )
//
//        val updated = PhoneModel( "1", "Samsung Galaxy S9", false)
//        val response: ResponseEntity<PhoneModel> = restTemplate!!.exchange(
//            String.format("http://localhost:%d/api/v1/phone/book", port),
//            HttpMethod.PUT,
//            HttpEntity<PhoneModel>(updated),
//            PhoneModel::class.java
//        )
//
//        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
//        Assertions.assertThat(response.body).isNull()
//    }

}