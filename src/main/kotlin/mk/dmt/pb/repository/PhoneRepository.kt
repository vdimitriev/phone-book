package mk.dmt.pb.repository

import mk.dmt.pb.entity.PhoneEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneRepository:JpaRepository<PhoneEntity, Long> {
}