package mk.dmt.pb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhoneBookApplication

fun main(args: Array<String>) {
	runApplication<PhoneBookApplication>(*args)
}
