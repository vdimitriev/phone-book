package mk.dmt.pb.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ApiNotFoundException(
    private var status: HttpStatus? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private var timestamp: LocalDateTime? = null,
    private val message: String? = null,
    private val errors: Map<String, String>? = null
) {
    fun ApiException() {
        timestamp = LocalDateTime.now()
        this.status = HttpStatus.NOT_FOUND
    }
}