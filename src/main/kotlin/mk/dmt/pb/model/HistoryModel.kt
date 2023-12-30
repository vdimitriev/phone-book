package mk.dmt.pb.model

data class HistoryModel(
    val historyId: String? = "",
    val timestamp: String? = "",
    val booker: BookerModel? = null
)
