package mk.dmt.pb.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.validation.constraints.NotBlank
import mk.dmt.pb.model.OutputMessage
import mk.dmt.pb.model.PhoneModel
import mk.dmt.pb.service.PhoneService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/phone")
class PhoneController(val phoneService: PhoneService) {

    @PutMapping(value = ["/book/{phoneId}/{bookerId}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun book(@PathVariable @NotBlank phoneId: String,
             @PathVariable @NotBlank bookerId: String):ResponseEntity<Any> {
        phoneService.bookPhone(phoneId, bookerId)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

    @PutMapping(value = ["/return/{phoneId}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnBack(@PathVariable @NotBlank phoneId: String):ResponseEntity<Any> {
        phoneService.returnPhone(phoneId)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

    @GetMapping(value = ["{phoneId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun phoneInfo(@PathVariable @NotBlank phoneId: String): ResponseEntity<OutputMessage> {
        val phone:OutputMessage = phoneService.findPhone(phoneId)
        return ResponseEntity<OutputMessage>(phone, HttpStatus.OK)
    }


}