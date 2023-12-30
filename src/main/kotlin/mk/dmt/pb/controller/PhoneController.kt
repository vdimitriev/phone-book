package mk.dmt.pb.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import mk.dmt.pb.model.PhoneModel
import mk.dmt.pb.service.PhoneService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/phone")
class PhoneController(val phoneService: PhoneService) {

    @PutMapping(value = ["/book"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun book(@Valid @RequestBody phone: PhoneModel):ResponseEntity<Any> {
        val booked = phoneService.bookPhone(phone)
        return if(booked) {
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping(value = ["/return"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun returnBack(@Valid @RequestBody phone: PhoneModel):ResponseEntity<Any> {
        val booked = phoneService.returnPhone(phone)
        return if(booked) {
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping(value = ["{phoneId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPhoneInfo(@PathVariable @NotBlank phoneId: String):ResponseEntity<PhoneModel> {
        val phoneModel:PhoneModel? = phoneService.findPhoneModel(phoneId)
        return if(phoneModel == null) {
            ResponseEntity<PhoneModel>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity<PhoneModel>(phoneModel, HttpStatus.OK)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPhonesInfo():ResponseEntity<List<PhoneModel>> {
        val phoneModelList:List<PhoneModel> = phoneService.findAllPhoneModels()
        return if(phoneModelList.isEmpty()) {
            ResponseEntity<List<PhoneModel>>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity<List<PhoneModel>>(phoneModelList, HttpStatus.OK)
        }
    }

}