package com.example.testbitcoin.service

import com.example.testbitcoin.constant.StatusCode.STATUS200
import com.example.testbitcoin.dto.AddBtcRequest
import com.example.testbitcoin.dto.AllBtcDetailResponse
import com.example.testbitcoin.dto.BitcoinCompositeResponse
import com.example.testbitcoin.dto.SearchBtcRequest
import com.example.testbitcoin.entities.BtcEntity
import com.example.testbitcoin.repositories.BtcRepository
import com.example.testbitcoin.utils.DateUtils
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BitcoinService(
  val messageSource : MessageSource,
  val btcRepository : BtcRepository
) {
  private val log = LoggerFactory.getLogger(BitcoinService::class.java)
  private val ADD_BTC_COMPLETE = "add_btc_complete"

  @Transactional
  fun addBTC(addBtcRequest: AddBtcRequest) : BitcoinCompositeResponse{
    log.info("Get message $addBtcRequest")
    val dateTime = DateUtils.resolveDateDefault(addBtcRequest.datetime!!)
    btcRepository.save(BtcEntity(null, dateTime, addBtcRequest.amount!!))
    return BitcoinCompositeResponse(STATUS200.value, messageSource.getMessage(ADD_BTC_COMPLETE, null, LocaleContextHolder.getLocale()))
  }

  @Transactional(readOnly = true)
  fun getTransactionHistoryDetail(searchBtcRequest: SearchBtcRequest) : List<AllBtcDetailResponse>{
    log.info("Get message $searchBtcRequest")
    val startDateTime = DateUtils.resolveDateDefault(searchBtcRequest.startDatetime!!)
    val endDateTime = DateUtils.resolveDateDefault(searchBtcRequest.endDatetime!!)
    return getBTCDetailDependOnDateTimeRange(startDateTime, endDateTime)
  }

  private fun getBTCDetailDependOnDateTimeRange(startDateTime : LocalDateTime, endDateTime : LocalDateTime ) : List<AllBtcDetailResponse>{
    val allBtcDetail = btcRepository.getBtcWithTimeRangeInHour(startDateTime, endDateTime)
    return allBtcDetail.map { AllBtcDetailResponse(DateUtils.formatDateTimeWithHour(it.getDate(), it.getHour()), it.getTotalAmount())}
  }

}