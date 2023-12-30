package mk.dmt.pb.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "event")
data class EventEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long = -1,

    @Column(name = "event_id")
    var eventId: String = "",

    @Column(name = "created")
    var created: String = "",

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "phone_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var phone: PhoneEntity? = null,

    @Column(name = "type")
    var type: EventType? = null,

)

enum class EventType {
    BOOK,
    RETURN
}
