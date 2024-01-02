package mk.dmt.pb.repository

import mk.dmt.pb.entity.PhoneEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PhoneRepository:JpaRepository<PhoneEntity, Long> {
    @Query("SELECT phone FROM PhoneEntity phone WHERE phone.phoneId = :phoneId")
    fun findByPhoneId(@Param("phoneId") phoneId: String): Collection<PhoneEntity>

    @Query("SELECT phone FROM PhoneEntity phone WHERE phone.phoneId = :phoneId AND phone.available = true ORDER BY phone.unitId LIMIT 1")
    fun findAvailablePhoneByPhoneId(@Param("phoneId") phoneId: String): Optional<PhoneEntity>

    @Query("SELECT phone FROM PhoneEntity phone WHERE phone.phoneId = :phoneId AND phone.available = false ORDER BY phone.unitId LIMIT 1")
    fun findBookedPhoneByPhoneId(@Param("phoneId") phoneId: String): Optional<PhoneEntity>

}