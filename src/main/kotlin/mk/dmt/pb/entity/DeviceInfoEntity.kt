package mk.dmt.pb.entity

import jakarta.persistence.*

@Entity
@Table(name = "device_info")
data class DeviceInfoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "device_info_id")
    var deviceInfoId: String? = null,

    @Column(name = "device_name")
    val deviceName: String? = null,

    @Column(name = "technology")
    val technology: String? = null,

    @Column(name = "bands_2g")
    val bands2g: String? = null,

    @Column(name = "bands_3g")
    val bands3g: String? = null,

    @Column(name = "bands_4g")
    val bands4g: String? = null

)