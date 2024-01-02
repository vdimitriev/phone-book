package mk.dmt.pb.exception

class PhoneNotFoundException(
    val phoneId: String,
    override val message: String = "Unfortunately no phone with id: [$phoneId] was found in the database",
):RuntimeException(
    message,
)