//package mk.dmt.pb.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//public class PhoneServiceIT {
//
//    public static MockWebServer mockBackEnd;
//
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
//
//    @BeforeEach
//    void initialize() {
//        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
//        //employeeService = new EmployeeService(baseUrl);
//    }
//
//    @Test
//    void test() {
//
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//
//        mockBackEnd.enqueue(new MockResponse()
//                .setBody(objectMapper.writeValueAsString(mockEmployee))
//                .addHeader("Content-Type", "application/json"));
//    }
//}
