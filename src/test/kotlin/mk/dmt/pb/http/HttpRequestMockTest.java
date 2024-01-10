package mk.dmt.pb.http;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import mk.dmt.pb.model.OutputMessage;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.time.Duration;

import org.springframework.test.web.reactive.server.WebTestClient;

import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN;
import static mk.dmt.pb.fonoapi.ConstantsKt.URL_BASE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-h2database")
public class HttpRequestMockTest {

    @Autowired
    public TestRestTemplate restTemplate;

    @LocalServerPort
    private Integer port = 0;

    public static MockWebServer mockBackEnd;

//    @Autowired
//    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    private CircuitBreaker circuitBreaker;

    private WebTestClient testClient;

    @Autowired
    private Retry retry;

    @Autowired
    private ApplicationContext context;

//    @BeforeAll
//    static void setUp() throws IOException {
//        mockBackEnd = new MockWebServer();
//        mockBackEnd.start();
//    }
//
//    @AfterAll
//    static void tearDown() throws IOException {
//        mockBackEnd.shutdown();
//    }

    @BeforeEach
    void prepare() throws IOException {
        circuitBreaker.reset();

        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        //circuitBreakerRegistry.getAllCircuitBreakers().forEach(CircuitBreaker::reset);
    }

    @AfterEach
    void finish() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void test() {
//        final CircuitBreaker.State state = circuitBreakerRegistry.circuitBreaker("fonoapi").getState();

        Assertions.assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());

        testClient = WebTestClient
                .bindToApplicationContext(context)
                .configureClient().responseTimeout(Duration.ofSeconds(60))
                .build();

        mockBackEnd.url(URL_BASE);
        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(500));

        //mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(500));

        //String baseUrl = String.format("http://localhost:%s/api/v1/phone/samsung-galaxy-s9", mockBackEnd.getPort());
        testClient.get().uri(String.format("http://localhost:%s/api/v1/phone/samsung-galaxy-s9", port))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        Assertions.assertEquals(2, mockBackEnd.getRequestCount());
        //Assertions.assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());

        //boolean fonoapiState = circuitBreakerRegistry.circuitBreaker("fonoapi").getState() == OPEN;

        //String baseUrl = String.format("http://localhost:%s/api/v1/phone/samsung-galaxy-s9", mockBackEnd.getPort());
        //restTemplate.exchange(baseUrl, HttpMethod.GET, null, OutputMessage.class);
    }

//    fun restTemplateExchange(count:Int, headers: HttpHeaders) {
//        for(c in 0 until count) {
//            restTemplate!!.exchange(
//                    String.format("http://localhost:%d/api/v1/phone/samsung-galaxy-s9", port),
//                    HttpMethod.GET,
//                    HttpEntity<Any>(headers),
//                    OutputMessage::class.java
//            )
//        }
//    }

}
