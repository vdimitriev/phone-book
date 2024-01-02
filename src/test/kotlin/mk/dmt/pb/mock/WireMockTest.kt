//package mk.dmt.pb.mock
//
//import com.github.tomakehurst.wiremock.WireMockServer
//import com.github.tomakehurst.wiremock.*
//import com.github.tomakehurst.wiremock.core.WireMockConfiguration
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.springframework.web.reactive.function.client.WebClient
//
//
//class WireMockTest(
//
//) {
//
//    lateinit var wireMockServer: WireMockServer
//    lateinit var webClient: WebClient
//
//    @BeforeEach
//    @Throws(Exception::class)
//    fun setUp() {
//        wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort())
//        wireMockServer.start()
//        webClient = WebClient.builder().baseUrl(wireMockServer.baseUrl()).build()
//    }
//
//    @Test
//    fun testWireMock() {
//        wireMockServer.stubFor(get("/test").willReturn(ok("hello")))
//        val body = webClient.get()
//            .uri("/test")
//            .retrieve()
//            .bodyToMono(String::class.java)
//            .block()
//        Assertions.assertEquals("hello", body)
//    }
//
//    @AfterEach
//    @Throws(java.lang.Exception::class)
//    fun tearDown() {
//        wireMockServer.stop()
//    }
//}