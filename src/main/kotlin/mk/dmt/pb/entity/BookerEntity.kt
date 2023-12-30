package mk.dmt.pb.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "booker")
data class BookerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long = -1,

    @Column(name = "booker_id")
    var bookerId: String = "",

    @Column(name = "name")
    var name: String = "",
)
