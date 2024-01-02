package mk.dmt.pb.flyway;

import mk.dmt.pb.entity.PhoneEntity;
import mk.dmt.pb.repository.PhoneRepository;
import org.junit.jupiter.api.Assertions;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test-container-flyway")
@Testcontainers
public class PhoneRepositoryPostgresTest {

    @Autowired
    private PhoneRepository phoneRepository;

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
    @Order(value = 1)
    public void testConnectionToDatabase() {
        Assertions.assertNotNull(phoneRepository);
    }

    @Test
    @Order(value = 2)
    public void testFlywayInsertion() {
        List<PhoneEntity> phones = phoneRepository.findAll();
        Assertions.assertNotNull(phones);
        Assertions.assertFalse(phones.isEmpty());
        Assertions.assertEquals(10, phones.size());
    }

    @Test
    @Order(value = 2)
    public void testFindByPhoneId() {
        Collection<PhoneEntity> phones = phoneRepository.findByPhoneId("samsung-galaxy-s9");
        Assertions.assertNotNull(phones);
        Assertions.assertFalse(phones.isEmpty());
        Assertions.assertEquals(1, phones.size());
    }

    @Test
    @Order(value = 3)
    public void testFindAvailablePhoneByPhoneId() {
        final Optional<PhoneEntity> phoneOpt = phoneRepository.findAvailablePhoneByPhoneId("samsung-galaxy-s8");
        Assertions.assertFalse(phoneOpt.isEmpty());

        final PhoneEntity phoneEntity = phoneOpt.get();
        Assertions.assertEquals("samsung-galaxy-s8", phoneEntity.getPhoneId());
        Assertions.assertEquals(1, phoneEntity.getUnitId());
    }
}
