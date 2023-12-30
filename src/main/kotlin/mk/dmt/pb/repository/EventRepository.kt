package mk.dmt.pb.repository

import mk.dmt.pb.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository:JpaRepository<EventEntity, Long> {
}