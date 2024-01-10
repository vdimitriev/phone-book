package mk.dmt.pb.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import mk.dmt.pb.fonoapi.DeviceEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-h2database")
public class HttpServiceTest {

    @Autowired
    private HttpService httpService;

    public static MockWebServer mockBackEnd;

    @Autowired
    public TestRestTemplate restTemplate;

    @Autowired
    private CircuitBreaker circuitBreaker;

    @Autowired
    private Retry retry;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void prepare() {
        circuitBreaker.reset();
    }

    @Test
    void testFonoApiDeviceInfo() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        final DeviceEntity deviceEntity = new DeviceEntity("Galaxy S9", "Samsung", "Technology", "2G", "3G", "4G");
        final DeviceEntity[] deviceEntities = { deviceEntity };
        String jsonArray = objectMapper.writeValueAsString(deviceEntities);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(jsonArray)
                .addHeader("Content-Type", "application/json"));
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        DeviceEntity resultDevice = httpService.fonoApiCall(baseUrl, "", "Samsung", "Galaxy S9");
        Assertions.assertNotNull(resultDevice);
        Assertions.assertEquals(deviceEntity, resultDevice);
    }

    @Test
    void testRetry() {
        Assertions.assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(404));
        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(404));
        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(404));
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        DeviceEntity resultDevice = httpService.fonoApiCall(baseUrl, "", "Samsung", "Galaxy S9");
        Assertions.assertNull(resultDevice);
        Assertions.assertEquals(3, mockBackEnd.getRequestCount());
    }

    @Test
    void testCircuitBreaker() {
        Assertions.assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());

        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(404));
        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(404));
        mockBackEnd.enqueue(new MockResponse().setBody("ERROR").setResponseCode(404));

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        DeviceEntity resultDevice = httpService.fonoApiCall(baseUrl, "", "Samsung", "Galaxy S9");
        Assertions.assertNull(resultDevice);

        Assertions.assertEquals(3, mockBackEnd.getRequestCount());
        Assertions.assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
    }
}
