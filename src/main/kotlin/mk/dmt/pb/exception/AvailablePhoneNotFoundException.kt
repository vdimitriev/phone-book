package mk.dmt.pb.exception

class AvailablePhoneNotFoundException(
    val phoneName: String,
    override val message: String = "Unfortunately no available phone of type: [$phoneName] was found.",
):RuntimeException(
    message,
)