package com.example.testbitcoin.repositories

import com.example.testbitcoin.entities.BtcEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime


interface BtcRepository : JpaRepository<BtcEntity, Long> {

  @Query("SELECT * FROM btc t1 WHERE dateTime >= ?1 and dateTime <= ?2", nativeQuery = true)
  fun getBtcWithTimeRangeInHour(startDate: LocalDateTime, endDate: LocalDateTime) : List<BtcEntity>
}