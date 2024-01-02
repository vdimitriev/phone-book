package mk.dmt.pb.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.dmt.pb.fonoapi.DeviceEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-h2database")
public class HttpServiceTest {

    @Autowired
    private HttpService httpService;

    public static MockWebServer mockBackEnd;

    @Autowired
    public TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
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
}
