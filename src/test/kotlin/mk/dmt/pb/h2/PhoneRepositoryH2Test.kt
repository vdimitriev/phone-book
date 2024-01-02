package mk.dmt.pb.h2

import mk.dmt.pb.repository.PhoneRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test-h2database")
class PhoneRepositoryH2Test {

    @Autowired
    var phoneRepository: PhoneRepository? = null


    @Test
    @Order(value = 1)
    fun testConnectionToDatabase() {
        Assertions.assertNotNull(phoneRepository)
    }

    @Test
    @Order(value = 2)
    fun testFlywayInsertData() {
        val phones = phoneRepository!!.findAll()
        Assertions.assertNotNull(phones)
        Assertions.assertFalse(phones.isEmpty())
        Assertions.assertEquals(10, phones.size)
    }

    @Test
    @Order(value = 3)
    fun testFindAvailablePhoneByPhoneId() {
        val phoneOpt = phoneRepository!!.findAvailablePhoneByPhoneId("samsung-galaxy-s8")
        Assertions.assertFalse(phoneOpt.isEmpty)
        val phoneEntity = phoneOpt.get()
        Assertions.assertEquals("samsung-galaxy-s8", phoneEntity.phoneId)
        Assertions.assertEquals(1, phoneEntity.unitId)
    }

    @Test
    @Order(value = 4)
    fun testFindExistingPhonesByPhoneId() {
        val phones = phoneRepository!!.findByPhoneId("samsung-galaxy-s8")
        Assertions.assertFalse(phones.isEmpty())
        Assertions.assertEquals(2, phones.size)
    }

    @Test
    @Order(value = 5)
    fun testFindOnePhoneByPhoneId() {
        val phones = phoneRepository!!.findByPhoneId("samsung-galaxy-s9")
        Assertions.assertFalse(phones.isEmpty())
        Assertions.assertEquals(1, phones.size)
    }

    @Test
    @Order(value = 6)
    fun testFindNonExistingPhoneByPhoneId() {
        val phones = phoneRepository!!.findByPhoneId("samsung-galaxy-s920")
        Assertions.assertTrue(phones.isEmpty())
    }

}