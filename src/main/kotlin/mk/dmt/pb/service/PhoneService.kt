package mk.dmt.pb.service

import mk.dmt.pb.entity.PhoneEntity
import mk.dmt.pb.model.PhoneModel
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class PhoneService {

    fun bookPhone(phone: PhoneModel):Boolean {
        val phoneEntity:PhoneEntity? = phoneList.firstOrNull() { phoneEntity: PhoneEntity -> phoneEntity.name == phone.name && phoneEntity.available }
        return if(phoneEntity != null) {
            phoneEntity.available = false
            phoneEntity.dateOfBooking = LocalDate.now().toString()
            true
        } else {
            false
        }
    }

    fun returnPhone(phone: PhoneModel): Boolean {
        val phoneEntity:PhoneEntity? = phoneList.firstOrNull() { phoneEntity: PhoneEntity -> phoneEntity.name == phone.name && phoneEntity.available }
        return if(phoneEntity != null) {
            if(phoneEntity.available) false
            else {
                phoneEntity.available = true
                phoneEntity.booker = null
                phoneEntity.dateOfBooking = null
                true
            }
        } else {
            false
        }
    }

    fun findPhoneModel(phoneId: String): PhoneModel? {
        val phoneEntity:PhoneEntity? = phoneList.firstOrNull() {
            phoneEntity: PhoneEntity -> phoneEntity.phoneId == phoneId
        }
        return if(phoneEntity == null) null
        else {
            PhoneModel(phoneEntity.phoneId, phoneEntity.name!!, phoneEntity.booker!!.name)
        }
    }

    fun findAllPhoneModels(): List<PhoneModel> {
        val phoneModelList = phoneList.map { it -> PhoneModel(it.phoneId, it.name!!, it.booker!!.name) }
        return phoneModelList
    }

    val phoneList:List<PhoneEntity> = listOf()
//    val phoneList = listOf(
//        PhoneEntity(1, "samsung-galaxy-s9", "Samsung Galaxy S9", PhoneStatus.RETURNED),
//        PhoneEntity(2, "samsung-galaxy-s8", "Samsung Galaxy S8", PhoneStatus.RETURNED),
//        PhoneEntity(3, "samsung-galaxy-s8", "Samsung Galaxy S8", PhoneStatus.RETURNED),
//        PhoneEntity(4, "motorola-nexus-6", "Motorola Nexus 6", PhoneStatus.RETURNED),
//        PhoneEntity(5, "oneplus-9", "Oneplus 9", PhoneStatus.RETURNED),
//        PhoneEntity(6, "apple-iphone-13","Apple iPhone 13", PhoneStatus.RETURNED),
//        PhoneEntity(7, "apple-iphone-12", "Apple iPhone 12", PhoneStatus.RETURNED),
//        PhoneEntity(8, "apple-iphone-11", "Apple iPhone 11", PhoneStatus.RETURNED),
//        PhoneEntity(9, "iphone-x", "iPhone X", PhoneStatus.RETURNED),
//        PhoneEntity(10, "nokia-3310", "Nokia 3310", PhoneStatus.RETURNED),
//    )
}