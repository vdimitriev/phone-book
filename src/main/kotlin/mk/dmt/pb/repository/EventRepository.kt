package mk.dmt.pb.repository

import mk.dmt.pb.entity.EventEntity
import mk.dmt.pb.entity.EventType
import mk.dmt.pb.entity.PhoneEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EventRepository:JpaRepository<EventEntity, Long> {

    @Query("SELECT event FROM EventEntity event WHERE event.type = 'BOOK' AND event.phone.phoneId = :phoneId ORDER BY event.created desc")
    fun findBookingEventsByPhoneId(@Param("phoneId") phoneId: String): Collection<EventEntity>
}