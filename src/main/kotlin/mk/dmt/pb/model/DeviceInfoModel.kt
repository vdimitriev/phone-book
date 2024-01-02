package mk.dmt.pb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown=true)
class DeviceInfoModel(

    @JsonProperty("deviceName")
    var deviceName: String = "N/A",

    @JsonProperty("technology")
    var technology: String = "N/A",

    @JsonProperty("bands2g")
    var bands2g: String = "N/A",

    @JsonProperty("bands3g")
    var bands3g: String = "N/A",

    @JsonProperty("bands4g")
    var bands4g: String = "N/A",
)