package mk.dmt.pb.service


import mk.dmt.pb.entity.*
import mk.dmt.pb.exception.BookerNotFoundException
import mk.dmt.pb.exception.PhoneNotFoundException
import mk.dmt.pb.fonoapi.*
import mk.dmt.pb.http.HttpService
import mk.dmt.pb.model.*
import mk.dmt.pb.repository.BookerRepository
import mk.dmt.pb.repository.EventRepository
import mk.dmt.pb.repository.PhoneRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*


@Component
class PhoneService(
    val phoneRepository: PhoneRepository,
    val bookerRepository: BookerRepository,
    val eventRepository: EventRepository,
    val httpService: HttpService
    ) {

    @Transactional
    fun bookPhone(phoneId: String, bookerId: String) {
        val phoneOpt = phoneRepository.findAvailablePhoneByPhoneId(phoneId)
        val bookerOpt = bookerRepository.findByBookerId(bookerId)
        when {
            phoneOpt.isEmpty -> throw PhoneNotFoundException(phoneId)
            bookerOpt.isEmpty -> throw BookerNotFoundException(bookerId)
            else -> makeBooking(phoneOpt.get(), bookerOpt.get())
        }
    }

    @Transactional
    fun returnPhone(phoneId: String) {
        val phoneOpt = phoneRepository.findBookedPhoneByPhoneId(phoneId)
        when {
            phoneOpt.isEmpty -> throw PhoneNotFoundException(phoneId)
            else -> makeReturn(phoneOpt.get())
        }
    }

    private fun makeBooking(phoneEntity: PhoneEntity, bookerEntity: BookerEntity) {
        phoneEntity.available = false
        eventRepository.save(EventEntity(null, UUID.randomUUID().toString(), phoneEntity, bookerEntity, EventType.BOOK, OffsetDateTime.now()))
        phoneRepository.save(phoneEntity)
    }

    private fun makeReturn(phoneEntity: PhoneEntity) {
        phoneEntity.available = true
        eventRepository.save(EventEntity(null, UUID.randomUUID().toString(), phoneEntity, null, EventType.RETURN, OffsetDateTime.now()))
        phoneRepository.save(phoneEntity)
    }

    @Transactional
    fun findPhone(phoneId: String): OutputMessage {
        val phones = phoneRepository.findByPhoneId(phoneId)
        when {
            phones.isEmpty() -> throw PhoneNotFoundException(phoneId)
            else -> {
                val outputMessage = OutputMessage(phoneId = phoneId)
                phones.forEach { phoneEntity: PhoneEntity ->
                    val dim: DeviceInfoModel? = findAndStoreDeviceInfo(phoneEntity)
                    val phoneModel = PhoneModel()
                    phoneModel.phoneId = phoneEntity.phoneId!!
                    phoneModel.name = phoneEntity.name!!
                    phoneModel.available = phoneEntity.available
                    phoneModel.deviceInfo = dim
                    val history:List<HistoryModel> = eventRepository.findBookingEventsByPhoneId(phoneId)
                        .map {
                            val booker = it.booker!!
                            HistoryModel(
                                BookerModel(booker.bookerId, booker.name),
                                it.created
                            ) }
                    phoneModel.history = history
                    if(!phoneModel.available && history.isNotEmpty()) {
                        phoneModel.booker = history[0].booker
                        phoneModel.bookingDate = history[0].date
                    }
                    outputMessage.phones.add(phoneModel)
                }
                return outputMessage
            }
        }
    }

    private fun findAndStoreDeviceInfo(phoneEntity: PhoneEntity): DeviceInfoModel? {
        return when {
            phoneEntity.deviceInfo != null -> {
                val die:DeviceInfoEntity = phoneEntity.deviceInfo!!
                DeviceInfoModel(die.deviceName!!, die.technology!!, die.bands2g!!, die.bands3g!!, die.bands4g!!)
            }
            else -> {
                val deviceEntity: DeviceEntity? = httpService.fonoApiCall(URL_BASE, TOKEN, phoneEntity.brand!!, phoneEntity.model!!)
                if(deviceEntity != null) {
                    val die = DeviceInfoEntity(id = null,
                        deviceInfoId = UUID.randomUUID().toString(),
                        deviceName = deviceEntity.deviceName,
                        technology = deviceEntity.technology,
                        bands2g = deviceEntity._2g_bands,
                        bands3g = deviceEntity._3g_bands,
                        bands4g = deviceEntity._4g_bands);
                    phoneEntity.deviceInfo = die
                    phoneRepository.save(phoneEntity)
                    DeviceInfoModel(die.deviceName!!, die.technology!!, die.bands2g!!, die.bands3g!!, die.bands4g!!)
                } else {
                    null
                }
            }
        }

    }
}