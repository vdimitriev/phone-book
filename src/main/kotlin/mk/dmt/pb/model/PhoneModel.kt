package mk.dmt.pb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("phone")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
data class PhoneModel (

    @JsonProperty("id")
    var phoneId: String = "",

    @JsonProperty("name")
    var name: String = "",

    @JsonProperty("available")
    var available: Boolean = true,

    @JsonProperty("deviceInfo")
    var deviceInfo: DeviceInfoModel? = null,

    @JsonProperty("booker")
    var booker: BookerModel? = null,

    @JsonProperty("history")
    var history: List<HistoryModel> = mutableListOf()
)
