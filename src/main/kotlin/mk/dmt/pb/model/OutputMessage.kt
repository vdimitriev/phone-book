package mk.dmt.pb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import mk.dmt.pb.model.PhoneModel

@JsonIgnoreProperties(ignoreUnknown=true)
data class OutputMessage(

    @JsonProperty("phoneId")
    val phoneId: String = "",

    @JsonProperty("phones")
    val phones: MutableCollection<PhoneModel> = mutableListOf()

)