package mk.dmt.pb.http

//import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import io.github.resilience4j.reactor.retry.RetryOperator
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.timelimiter.TimeLimiter
import mk.dmt.pb.exception.DeviceNotFoundException
import mk.dmt.pb.exception.PhoneNotFoundException
import mk.dmt.pb.exception.RetryException
import mk.dmt.pb.fonoapi.DeviceEntity
import mk.dmt.pb.fonoapi.URL_BASE
import mu.KLogger
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.WebClientResponseException.InternalServerError
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.temporal.ChronoUnit

@Component
class HttpService(
    val logger: KLogger = KotlinLogging.logger {}
) {

    private var webClient: WebClient = WebClient.builder().baseUrl(URL_BASE).build()

//    @Autowired
//    private lateinit var circuitBreakerRegistry: CircuitBreakerRegistry

    @Autowired
    private lateinit var fonoApiCircuitBreaker: CircuitBreaker

    @Autowired
    private lateinit var fonoApiRetry: Retry

    @Autowired
    private lateinit var fonoApiTimeLimiter: TimeLimiter

    fun bestCall(baseUrl: String, token: String, brand: String, device: String): Mono<Array<DeviceEntity>> {
        val deviceUrl = "v1/getdevice?token=$token&brand=$brand&device=$device"
        return webClient
                .get()
                .uri("$baseUrl/$deviceUrl")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Array<DeviceEntity>::class.java)
                .transformDeferred(CircuitBreakerOperator.of(fonoApiCircuitBreaker))
                .transformDeferred(RetryOperator.of(fonoApiRetry))
                .transformDeferred(TimeLimiterOperator.of(fonoApiTimeLimiter))
    }

    fun testCall(baseUrl: String, token: String, brand: String, device: String):DeviceEntity? {
        var devices: Array<DeviceEntity>? = null
        try {
            devices = bestCall(baseUrl, token, brand, device).block()
        } catch (e: CallNotPermittedException) {
            logger.error { e }
            e.printStackTrace()
        } catch (e: InternalServerError) {
            logger.error { e }
            e.printStackTrace()
        } catch (e: RetryException) {
            logger.error { e }
            e.printStackTrace()
        }
        var device: DeviceEntity? = null
        if (!devices.isNullOrEmpty()) {
            device = devices[0]
        }
        return device
    }

//    fun restCall(baseUrl: String, token: String, brand: String, device: String): DeviceEntity? {
//        val deviceUrl = "v1/getdevice?token=$token&brand=$brand&device=$device"
//        val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("fonoapi", "fonoapi")
//        val response: Array<DeviceEntity>? = webClient
//            .get()
//            .uri("$baseUrl/$deviceUrl")
//            .accept(MediaType.APPLICATION_JSON)
//            .retrieve()
//            .bodyToMono(Array<DeviceEntity>::class.java)
//            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
////            .retryWhen(Retry.backoff(2, Duration.of(1, ChronoUnit.SECONDS))
////                .onRetryExhaustedThrow { _, _ -> DeviceNotFoundException("") }
////            )
//            .block()
//        var device: DeviceEntity? = null
//        if (!response.isNullOrEmpty()) {
//            device = response[0]
//        }
//        return device
//    }

//    fun restOfUs() {
//        try {
//            val response: UserDefinedResponse = webClient
//                .post()
//                .body<Map<*, *>, Mono<Map<*, *>>>(
//                    Mono.just<Map<*, *>>(userDefinedRequest),
//                    MutableMap::class.java
//                )
//                .retrieve()
//                .bodyToMono(UserDefinedResponse::class.java)
//                .onErrorResume(Mono::error)
//                .retryWhen(
//                    Retry.backoff(3, Duration.of(2, ChronoUnit.SECONDS))
//                        .onRetryExhaustedThrow(BiFunction<RetryBackoffSpec, RetrySignal, Throwable> { retryBackoffSpec: RetryBackoffSpec?, retrySignal: RetrySignal ->
//                            UserDefinedException(
//                                retrySignal.failure()
//                            )
//                        })
//                )
//                .block()
//        } catch (userDefinedException: UserDefinedException) {
////Error Handling logic
//        }
//    }
}