//package mk.dmt.pb.config
//
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
//import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer
//import mk.dmt.pb.fonoapi.*
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import java.time.Duration
//import java.util.Map
//
//
//@Configuration
//class WebClientConfig {
//
////    @Bean
////    fun webClient(): WebClient {
////        return WebClient.builder().baseUrl(URL_BASE).build()
////    }
//
//    @Bean
//    fun externalServiceFonoApiCircuitBreakerConfig(): CircuitBreakerConfigCustomizer {
//        return CircuitBreakerConfigCustomizer.of("fonoapi") { builder: CircuitBreakerConfig.Builder ->
//                builder.slidingWindowSize(6)
//                    .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
//                    .waitDurationInOpenState(Duration.ofSeconds(1))
//                    .minimumNumberOfCalls(3)
//                    .failureRateThreshold(50.0f)
//            }
//    }
//
////    @Bean
////    fun circuitBreakerRegistry(): CircuitBreakerRegistry {
////        val externalServiceFooConfig = CircuitBreakerConfig.custom()
////            .slidingWindowSize(10)
////            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
////            .waitDurationInOpenState(Duration.ofSeconds(5))
////            .minimumNumberOfCalls(5)
////            .failureRateThreshold(50.0f)
////            .build()
////        return CircuitBreakerRegistry.of(
////            Map.of("fonoapi", externalServiceFooConfig)
////        )
////    }
//}