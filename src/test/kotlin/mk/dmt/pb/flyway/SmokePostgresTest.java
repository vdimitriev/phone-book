package mk.dmt.pb.flyway;

import mk.dmt.pb.controller.PhoneController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test-container-flyway")
@Testcontainers
public class SmokePostgresTest {

    @Autowired
    private PhoneController phoneController;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("phonebook")
            .withUsername("phonebook")
            .withPassword("phonebook");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("flyway.url", postgres::getJdbcUrl);
    }

    @Test
    @Order(value = 3)
    public void contextLoads() {
        Assertions.assertThat(phoneController).isNotNull();
    }
}
