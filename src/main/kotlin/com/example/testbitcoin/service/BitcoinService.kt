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
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
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
    val mapCountToHour = HashMap<String, BigDecimal>()
    for(btcDetails in allBtcDetail){
      val translatedDateTimeKey = translateTimeToHour(btcDetails.dataTime)
      val amount = btcDetails.amount
      if (mapCountToHour.containsKey(translatedDateTimeKey)){
        mapCountToHour[translatedDateTimeKey] = mapCountToHour[translatedDateTimeKey]!!.plus(amount)
      }else{
        mapCountToHour[translatedDateTimeKey] = amount
      }
    }
    return mapCountToHour.toList().map { AllBtcDetailResponse(it.first, it.second) }
  }

  private fun translateTimeToHour(dateTime : LocalDateTime) : String{
    val newDateTime = LocalDateTime.from(dateTime).withHour(dateTime.hour).withMinute(0).withSecond(0)
    return DateUtils.formatDateTimeForResponse(newDateTime)
  }


}