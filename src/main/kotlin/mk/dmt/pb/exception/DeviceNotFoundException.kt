package mk.dmt.pb.exception

class DeviceNotFoundException(
    val device: String,
    val brand: String,
    override val message: String = "No device $device was found for brand $brand.",
) :RuntimeException(
    message,
)