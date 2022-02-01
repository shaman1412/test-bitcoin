package com.example.testbitcoin.repositories

import com.example.testbitcoin.dto.GetBitcoinTransaction
import com.example.testbitcoin.entities.BtcEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime


interface BtcRepository : JpaRepository<BtcEntity, Long> {

  @Query("SELECT DATE(dateTime) as date, HOUR(dateTime) as hour, SUM(amount) as totalAmount FROM btc WHERE dateTime >= ?1 and dateTime <= ?2 GROUP BY DATE(dateTime), HOUR(dateTime)", nativeQuery = true)
  fun getBtcWithTimeRangeInHour(startDate: LocalDateTime, endDate: LocalDateTime) : List<GetBitcoinTransaction>?

}