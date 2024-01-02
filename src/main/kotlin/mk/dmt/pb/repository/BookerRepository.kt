package mk.dmt.pb.repository

import mk.dmt.pb.entity.BookerEntity
import mk.dmt.pb.entity.PhoneEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookerRepository: JpaRepository<BookerEntity, Long> {
    @Query("SELECT booker FROM BookerEntity booker WHERE booker.bookerId = :bookerId ")
    fun findByBookerId(@Param("bookerId") bookerId: String): Optional<BookerEntity>

}