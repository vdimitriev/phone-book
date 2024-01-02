package mk.dmt.pb.repository

import mk.dmt.pb.entity.DeviceInfoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceInfoRepository: JpaRepository<DeviceInfoEntity, Long> {
}