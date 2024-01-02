//package mk.dmt.pb.http;
//
//import mk.dmt.pb.fonoapi.DeviceEntity;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//
//import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
//import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.get;
//import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@ActiveProfiles("test-h2database")
//public class HttpServiceBestTest {
//
//    @RegisterExtension
//    static WireMockExtension EXTERNAL_SERVICE = WireMockExtension.newInstance()
//            .options(WireMockConfiguration.wireMockConfig().port(9090))
//            .build();
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private HttpService httpService;
//
//    @Test
//    public void testFoo() throws Exception {
//        EXTERNAL_SERVICE.stubFor(get("/external-foo").willReturn(serverError()));
//
//        for (int i = 0; i < 5; i++) {
//            ResponseEntity<String> response = restTemplate.getForEntity("/foo", String.class);
//            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        for (int i = 0; i < 5; i++) {
//            ResponseEntity<String> response = restTemplate.getForEntity("/foo", String.class);
//            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
//
//    @Test
//    public void testBest() {
//        //final String url = "https://fonoapi.freshpixl.com/v1/getdevice?token=token&brand=samsung&device=s9";
//        EXTERNAL_SERVICE.stubFor(get("/v1/getdevice?token=token&brand=samsung&device=s9").willReturn(serverError()));
//
//        final String url = "https://fonoapi.freshpixl.com/v1/getdevice?token=token&brand=samsung&device=s9";
//        DeviceEntity deviceEntity = httpService.restCall(baseUrl, token, brand, device);
//    }
//}
