package mk.dmt.pb.model

import com.fasterxml.jackson.annotation.*
import java.time.OffsetDateTime

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @JsonProperty("bookingDate")
    var bookingDate: OffsetDateTime? = null,

    @JsonProperty("history")
    var history: List<HistoryModel> = mutableListOf()
)
