package mk.dmt.pb.model

import com.fasterxml.jackson.annotation.*
import java.time.OffsetDateTime

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("event")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
data class HistoryModel(

    @JsonProperty("booker")
    val booker: BookerModel? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @JsonProperty("date")
    val date: OffsetDateTime? = null
)
