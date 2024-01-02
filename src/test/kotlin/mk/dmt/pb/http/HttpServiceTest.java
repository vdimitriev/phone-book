package mk.dmt.pb.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.dmt.pb.fonoapi.DeviceEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static mk.dmt.pb.fonoapi.ConstantsKt.URL_BASE;

public class HttpServiceTest {

    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

//    @BeforeEach
//    void initialize() {
//        //String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
//        //employeeService = new EmployeeService(baseUrl);
//    }

//    @Test
//    void test() throws JsonProcessingException {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//        final DeviceEntity[] deviceEntities = {
//            new DeviceEntity("Galaxy S9", "Samsung", "Technology", "2G", "3G", "4G")
//        };
//        String jsonArray = objectMapper.writeValueAsString(deviceEntities);
//        mockBackEnd.enqueue(new MockResponse()
//                .setBody(jsonArray)
//                .addHeader("Content-Type", "application/json"));
//        final HttpService httpService = new HttpService();
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
//
////        Mono<Employee> employeeMono = employeeService.getEmployeeById(100);
////        StepVerifier.create(employeeMono)
////                .expectNextMatches(employee -> employee.getRole()
////                        .equals(Role.LEAD_ENGINEER))
////                .verifyComplete();
//    }

}
