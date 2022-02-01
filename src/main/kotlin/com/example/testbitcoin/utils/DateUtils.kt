package com.example.testbitcoin.utils

import com.example.testbitcoin.constant.StatusCode.STATUS400
import com.example.testbitcoin.exception.BTCCompositeException
import com.example.testbitcoin.service.BitcoinService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.BAD_REQUEST
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateUtils {
  companion object{
    private val log = LoggerFactory.getLogger(BitcoinService::class.java)

    fun resolveDateDefault(localDate: String): LocalDateTime {
      try {
        val zoneDateTime = ZonedDateTime.parse(localDate)
        return zoneDateTime.withZoneSameInstant(ZoneId.of("+00:00")).toLocalDateTime()
      }catch (ex : DateTimeParseException){
        log.error("Cant pares date time format : $localDate")
        throw BTCCompositeException(BAD_REQUEST, STATUS400.value, "Cant pares date time format : $localDate, " +
            "The format should be yyyy-MM-dd'T'HH:mm:ssZ")
      }
    }

    fun formatDateTimeForResponse(localDateTime: LocalDateTime): String{
      try {
        val dateWithZone = localDateTime.atZone(ZoneId.of("+00:00"))
        val responseDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return dateWithZone.format(responseDateFormat) + "+00:00"
      }catch (ex : DateTimeParseException){
      log.error("Cant pares date time format : $localDateTime")
      throw BTCCompositeException(BAD_REQUEST, STATUS400.value, "Cant pares date time format : $localDateTime")
      }
    }

    fun formatDateTimeWithHour(dateTime : Date, hour: Int) : String{
      val newDateTime = dateTime.toLocalDate().atStartOfDay().withHour(hour)
      return formatDateTimeForResponse(newDateTime)
    }
  }

}