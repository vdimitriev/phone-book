package mk.dmt.pb.mock

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import mk.dmt.pb.model.PhoneModel
import mk.dmt.pb.service.PhoneService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PhoneBookMockTest {

    @Autowired
    val mockMvc: MockMvc? = null

    @MockBean
    val service: PhoneService? = null

    @Test
    fun shouldReturnDefaultMessage() {
        var objectMapper: ObjectMapper = ObjectMapper()
        objectMapper.registerKotlinModule()
        objectMapper.findAndRegisterModules();
        val phoneModel: PhoneModel = PhoneModel("1", "Samsung", true);
        Mockito.`when`(service?.bookPhone("1", "1"))
        mockMvc?.perform(
            put("/api/v1/phone/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(phoneModel)))
            ?.andExpect(status().isNoContent())
    }

}