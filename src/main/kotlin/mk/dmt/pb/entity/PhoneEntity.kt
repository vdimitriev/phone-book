package mk.dmt.pb.entity

import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.OffsetDateTime

@Entity
@Table(name = "phone")
data class PhoneEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "phone_id")
    var phoneId: String? = null,

    @Column(name = "unit_id")
    var unitId: Int? = null,

    @Column(name = "make")
    var make: String? = null,

    @Column(name = "model")
    var model: String? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "available")
    var available: Boolean = true

)
