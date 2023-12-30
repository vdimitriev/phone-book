package mk.dmt.pb.repository

import mk.dmt.pb.entity.BookerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookerRepository: JpaRepository<BookerEntity, Long> {
}