package mk.dmt.pb.http

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import io.github.resilience4j.reactor.retry.RetryOperator
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.timelimiter.TimeLimiter
import mk.dmt.pb.exception.DeviceNotFoundException
import mk.dmt.pb.exception.PhoneNotFoundException
import mk.dmt.pb.fonoapi.*
import mu.KLogger
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException.InternalServerError
import reactor.core.publisher.Mono

@Component
class HttpService(
    private val apiCircuitBreaker: CircuitBreaker,
    private val apiRetry: Retry,
    private val apiTimeLimiter: TimeLimiter
) {

    private val logger: KLogger = KotlinLogging.logger {}
    private val webClient: WebClient = WebClient.builder().baseUrl(URL_BASE).build()

    private fun webClientCall(baseUrl: String, token: String, brand: String, device: String): Mono<Array<DeviceEntity>> {
        val deviceUrl = "$URL_GET_DEVICE?$QUERY_TOKEN=$token&$QUERY_BRAND=$brand&$QUERY_DEVICE=$device"
        return webClient
                .get()
                .uri("$baseUrl/$deviceUrl")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Array<DeviceEntity>::class.java)
                .transformDeferred(CircuitBreakerOperator.of(apiCircuitBreaker))
                .transformDeferred(RetryOperator.of(apiRetry))
                .transformDeferred(TimeLimiterOperator.of(apiTimeLimiter))
                .doOnError { throw DeviceNotFoundException(device, brand) }
    }

    fun fonoApiCall(baseUrl: String, token: String, brand: String, device: String): DeviceEntity? {
        var devices: Array<DeviceEntity>? = null
        return try {
            devices = webClientCall(baseUrl, token, brand, device).block()
            devices?.get(0)
        } catch (e: DeviceNotFoundException) {
            logger.error { e }
            null
        }
    }
}