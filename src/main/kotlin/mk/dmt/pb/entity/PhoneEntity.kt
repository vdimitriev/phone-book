package mk.dmt.pb.entity

import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType

@Entity
@Table(name = "phone")
data class PhoneEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "phone_id")
    var phoneId: String? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "device_info_id", nullable = false)
    @Cascade(CascadeType.ALL)
    var deviceInfo: DeviceInfoEntity? = null,

    @Column(name = "unit_id")
    var unitId: Int? = null,

    @Column(name = "brand")
    var brand: String? = null,

    @Column(name = "model")
    var model: String? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "available")
    var available: Boolean = true

)
