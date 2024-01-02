package mk.dmt.pb.entity

import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.OffsetDateTime

@Entity
@Table(name = "event")
data class EventEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "event_id")
    var eventId: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "phone_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var phone: PhoneEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "booker_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var booker: BookerEntity? = null,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: EventType? = null,

    @Column(name = "created")
    var created: OffsetDateTime? = null,

)

enum class EventType {
    BOOK,
    RETURN
}
