package mk.dmt.pb.exception

class DeviceNotFoundException(
    override val message: String = "No device was found. when calling the api",
) :RuntimeException(
    message,
)