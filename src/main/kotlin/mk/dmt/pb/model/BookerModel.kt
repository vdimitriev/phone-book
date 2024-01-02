package mk.dmt.pb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown=true)
data class BookerModel(

    @JsonProperty("bookerId")
    val bookerId: String? = "",

    @JsonProperty("bookerName")
    val bookerName: String? = "",
)
