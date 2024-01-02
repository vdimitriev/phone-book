package mk.dmt.pb.exception

class BookerNotFoundException(
    val bookerId: String,
    override val message: String = "Unfortunately no booker with id: [$bookerId] was found in the database",
):RuntimeException(
    message
)