package mk.dmt.pb.fonoapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown=true)
data class DeviceEntity(
    @JsonProperty("DeviceName") val deviceName: String,
    @JsonProperty("Brand") val brand: String,
    @JsonProperty("technology") val technology: String,
    @JsonProperty("_2g_bands") val _2g_bands: String,
    @JsonProperty("_3g_bands") val _3g_bands: String,
    @JsonProperty("_4g_bands") val _4g_bands: String
)