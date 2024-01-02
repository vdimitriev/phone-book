package mk.dmt.pb.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryRegistry
import io.github.resilience4j.timelimiter.TimeLimiter
import io.github.resilience4j.timelimiter.TimeLimiterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConfiguration {

    @Bean
    fun fonoApiCircuitBreaker(registry: CircuitBreakerRegistry): CircuitBreaker {
        return registry.circuitBreaker("fonoapi")
    }

    @Bean
    fun fonoApiRetry(registry: RetryRegistry): Retry {
        return registry.retry("fonoapi")
    }

    @Bean
    fun fonoApiTimeLimiter(registry: TimeLimiterRegistry): TimeLimiter {
        return registry.timeLimiter("fonoapi")
    }

}