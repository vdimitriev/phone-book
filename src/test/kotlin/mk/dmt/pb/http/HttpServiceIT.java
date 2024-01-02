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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-h2database")
public class HttpServiceIT {

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

//    @Test
//    void test() throws JsonProcessingException {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//        final DeviceEntity[] deviceEntities = {
//                new DeviceEntity("Galaxy S9", "Samsung", "Technology", "2G", "3G", "4G")
//        };
//        String jsonArray = objectMapper.writeValueAsString(deviceEntities);
//        mockBackEnd.enqueue(new MockResponse()
//                .setBody(jsonArray)
//                .addHeader("Content-Type", "application/json"));
//
//        final String deviceUrl = "getdevice?token=$token&brand=$brand&device=$device";
//        final String token = "";
//        final String brand = "";
//        final String device = "";
//
//        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
//        final String url = "https://fonoapi.freshpixl.com/v1/getdevice?token=token&brand=samsung&device=s9";
//        DeviceEntity deviceEntity = httpService.restCall(baseUrl, token, brand, device);
//
//        Assertions.assertNotNull(deviceEntity);
//        System.out.println(deviceEntity);
//    }

    @Test
    void bestCall() throws Exception {
        for(int i = 0; i < 12; i++) {
            mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        }

        final String token = "token";
        final String brand = "samsung";
        final String device = "s9";

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        final String url = "https://fonoapi.freshpixl.com/v1/getdevice";

        httpService.bestCall(baseUrl, token, brand, device).block();
//        for(int i = 0; i < 12; i++) {
//            System.out.println("i = " + i);
//            httpService.bestCall(baseUrl, token, brand, device).block();
//        }

    }

    @Test
    void retryTest() throws JsonProcessingException {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        //        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

//        final String deviceUrl = "getdevice?token=$token&brand=$brand&device=$device";
        final String token = "token";
        final String brand = "samsung";
        final String device = "s9";

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        final String url = "https://fonoapi.freshpixl.com/v1/getdevice";

        for(int i = 0; i < 3; i++) {
            httpService.testCall(baseUrl, token, brand, device);
        }

        for(int i = 0; i < 3; i++) {
            httpService.testCall(baseUrl, token, brand, device);
        }
    }

    @Test
    void bestTest() throws JsonProcessingException {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(500));


//        mockBackEnd.enqueue(new MockResponse().setResponseCode(503));
//        mockBackEnd.enqueue(new MockResponse().setResponseCode(503));
//        mockBackEnd.enqueue(new MockResponse().setResponseCode(503));
//        mockBackEnd.enqueue(new MockResponse().setResponseCode(503));
//        mockBackEnd.enqueue(new MockResponse().setResponseCode(503));
//        mockBackEnd.enqueue(new MockResponse().setResponseCode(503));

        final String deviceUrl = "getdevice?token=$token&brand=$brand&device=$device";
        final String token = "";
        final String brand = "";
        final String device = "";

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        final String url = "https://fonoapi.freshpixl.com/v1/getdevice?token=token&brand=samsung&device=s9";

//        httpService.restCall(baseUrl, token, brand, device);
//        httpService.restCall(baseUrl, token, brand, device);
//        httpService.restCall(baseUrl, token, brand, device);
//        httpService.restCall(baseUrl, token, brand, device);
//        httpService.restCall(baseUrl, token, brand, device);



        for(int i = 1; i < 5; i++) {
            try {
                httpService.bestCall(baseUrl, token, brand, device).block();
            } catch (WebClientResponseException.InternalServerError e) {
                e.printStackTrace();
            }
        }

        for(int i = 1; i < 5; i++) {
            try {
                httpService.bestCall(baseUrl, token, brand, device).block();
            } catch (WebClientResponseException.ServiceUnavailable e) {
                e.printStackTrace();
            }
        }

        httpService.bestCall(baseUrl, token, brand, device);
    }
}
