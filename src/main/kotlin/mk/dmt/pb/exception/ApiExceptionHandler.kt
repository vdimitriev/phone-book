package mk.dmt.pb.exception

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import mu.KLogger
import mu.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ApiExceptionHandler(
    val logger: KLogger = KotlinLogging.logger {}
    ) {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AvailablePhoneNotFoundException::class)
    fun handlePhoneNotFoundException(exception: AvailablePhoneNotFoundException): ResponseEntity<Any> {
        logger.warn(exception) { exception.message }
        return returnResponseEntity(HttpStatus.NOT_FOUND, exception.message)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookerNotFoundException::class)
    fun handleBookerNotFoundException(exception: BookerNotFoundException): ResponseEntity<Any> {
        logger.warn(exception) { exception.message }
        return returnResponseEntity(HttpStatus.NOT_FOUND, exception.message)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<Any> {
        val message = "Unexpected exception caught with message: ${exception.message}"
        logger.error(exception) { message }
        return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }

    @ExceptionHandler(CallNotPermittedException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    fun handle(exception: CallNotPermittedException) {
        logger.error(exception) { "Circuit breaker error" }
    }

    private fun returnResponseEntity(httpStatus: HttpStatus, message: String?): ResponseEntity<Any> =
        ResponseEntity.status(httpStatus)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
            .body(message)

}