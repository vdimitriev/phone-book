package mk.dmt.pb.mock

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import mk.dmt.pb.controller.PhoneController
import mk.dmt.pb.model.PhoneModel
import mk.dmt.pb.service.PhoneService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.IOException

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [PhoneController::class])
@AutoConfigureMockMvc
class PhoneControllerIT {

    @MockBean
    lateinit var phoneService: PhoneService

    @Autowired
    private val mvc: MockMvc? = null

    @Test
    fun phoneTest() {
        Mockito.`when`(phoneService.bookPhone("1", "1"))
        Mockito.`when`(phoneService.bookPhone("2", "1"))

        val phoneModel = PhoneModel("2", "Samsung Galaxy S9", true)
        mvc?.perform(put("/api/v1/phone/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(phoneModel)))
            ?.andExpect(status().isNoContent())
    }

    @Throws(IOException::class)
    fun toJson(obj: PhoneModel): ByteArray {
        var objectMapper: ObjectMapper = ObjectMapper()
        objectMapper.registerKotlinModule()
        objectMapper.findAndRegisterModules();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return objectMapper.writeValueAsBytes(obj)
    }
}