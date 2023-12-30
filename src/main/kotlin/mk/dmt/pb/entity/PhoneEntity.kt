package mk.dmt.pb.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "phone")
data class PhoneEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long = -1,

    @Column(name = "phone_id")
    var phoneId: String = "",

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "booker_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var booker: BookerEntity? = null,

    @OneToMany(mappedBy="phone")
    var history: Set<EventEntity> = mutableSetOf(),

    @Column(name = "make")
    var make: String? = null,

    @Column(name = "model")
    var model: String? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "date_of_booking")
    var dateOfBooking: String? = null,

    @Column(name = "available")
    var available: Boolean = true,

    @Column(name = "quantity")
    var quantity: Int = -1,

    )
