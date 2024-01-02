package mk.dmt.pb.service


import kotlinx.coroutines.cancel
import mk.dmt.pb.entity.BookerEntity
import mk.dmt.pb.entity.EventEntity
import mk.dmt.pb.entity.EventType
import mk.dmt.pb.entity.PhoneEntity
import mk.dmt.pb.exception.AvailablePhoneNotFoundException
import mk.dmt.pb.exception.BookerNotFoundException
import mk.dmt.pb.exception.PhoneNotFoundException
import mk.dmt.pb.fonoapi.*
import mk.dmt.pb.http.HttpService
import mk.dmt.pb.model.BookerModel
import mk.dmt.pb.model.HistoryModel
import mk.dmt.pb.model.OutputMessage
import mk.dmt.pb.model.PhoneModel
import mk.dmt.pb.repository.BookerRepository
import mk.dmt.pb.repository.EventRepository
import mk.dmt.pb.repository.PhoneRepository
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import okhttp3.logging.HttpLoggingInterceptor.Level
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
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
            phoneOpt.isEmpty -> throw AvailablePhoneNotFoundException(phoneId)
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

    fun findPhone(phoneId: String): OutputMessage {
        val phones = phoneRepository.findByPhoneId(phoneId)
        when {
            phones.isEmpty() -> throw PhoneNotFoundException(phoneId)
            else -> {
                val outputMessage = OutputMessage(phoneId = phoneId)
                phones.forEach { phoneEntity: PhoneEntity ->
                    val phoneModel = PhoneModel()
                    phoneModel.phoneId = phoneEntity.phoneId!!
                    phoneModel.name = phoneEntity.name!!
                    phoneModel.available = phoneEntity.available
                    phoneModel.booker = null
                    val history:List<HistoryModel> = eventRepository.findBookingEventsByPhoneId(phoneId)
                        .map {
                            val booker = it.booker!!
                            HistoryModel(
                                BookerModel(booker.bookerId, booker.name),
                                it.created
                            ) }
                    phoneModel.history = history
                    outputMessage.phones.add(phoneModel)
                }
                return outputMessage
            }
        }
    }
}