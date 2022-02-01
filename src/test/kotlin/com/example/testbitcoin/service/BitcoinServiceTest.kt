package com.example.testbitcoin.service

import com.example.testbitcoin.dto.AddBtcRequest
import com.example.testbitcoin.dto.SearchBtcRequest
import com.example.testbitcoin.entities.BtcEntity
import com.example.testbitcoin.exception.BTCCompositeException
import com.example.testbitcoin.repositories.BtcRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(MockitoExtension::class)
class BitcoinServiceTest {

  @InjectMocks
  lateinit var bitcoinService : BitcoinService

  @Mock
  lateinit var messageSource : MessageSource

  @Mock
  lateinit var btcRepository : BtcRepository

  @Test
  fun `verifyAddBTCIsSuccessful`(){
    val dateTimeCapture = argumentCaptor<BtcEntity>()
    Mockito.`when`(messageSource.getMessage(eq("add_btc_complete"), anyOrNull(), any())).thenReturn("add btc successful")
    val response = bitcoinService.addBTC(AddBtcRequest("2011-10-05T10:48:01+00:00", BigDecimal("120")))
    Mockito.verify(btcRepository).save(dateTimeCapture.capture())
    assertEquals("2011-10-05T10:48:01",dateTimeCapture.firstValue.dataTime.toString())
    assertEquals(BigDecimal("120"),dateTimeCapture.firstValue.amount)
    assertNull(dateTimeCapture.firstValue.id)
    assertEquals("bc-200",response.code)
    assertEquals("add btc successful",response.message)
  }

  @Test
  fun `verifyAddBTCIsSuccessfulWithDifferTimeZoneUTC+0700`(){
    val dateTimeCapture = argumentCaptor<BtcEntity>()
    Mockito.`when`(messageSource.getMessage(eq("add_btc_complete"), anyOrNull(), any())).thenReturn("add btc successful")
    val response = bitcoinService.addBTC(AddBtcRequest("2011-10-05T10:48:01+07:00", BigDecimal("120")))
    Mockito.verify(btcRepository).save(dateTimeCapture.capture())
    assertEquals("2011-10-05T03:48:01",dateTimeCapture.firstValue.dataTime.toString())
    assertEquals(BigDecimal("120"),dateTimeCapture.firstValue.amount)
    assertNull(dateTimeCapture.firstValue.id)
    assertEquals("bc-200",response.code)
    assertEquals("add btc successful",response.message)
  }

  @Test
  fun `verifyAddBTCIsSuccessfulWithDifferTimeZoneUTC-0700`(){
    val dateTimeCapture = argumentCaptor<BtcEntity>()
    Mockito.`when`(messageSource.getMessage(eq("add_btc_complete"), anyOrNull(), any())).thenReturn("add btc successful")
    val response = bitcoinService.addBTC(AddBtcRequest("2011-10-05T10:48:01-07:00", BigDecimal("120")))
    Mockito.verify(btcRepository).save(dateTimeCapture.capture())
    assertEquals("2011-10-05T17:48:01",dateTimeCapture.firstValue.dataTime.toString())
    assertEquals(BigDecimal("120"),dateTimeCapture.firstValue.amount)
    assertNull(dateTimeCapture.firstValue.id)
    assertEquals("bc-200",response.code)
    assertEquals("add btc successful",response.message)
  }

  @Test
  fun `verifyAddBTCIsSuccessfulWithoutTimezone`(){
    val btcException = assertThrows<BTCCompositeException> { bitcoinService.addBTC(AddBtcRequest("2011-10-05 10:48:01", BigDecimal("120")))  }
    Mockito.verify(btcRepository, times(0)).save(any())
    assertEquals("bc-400",btcException.code)
    assertEquals(HttpStatus.BAD_REQUEST,btcException.httpStatus)
    assertEquals("Cant pares date time format : 2011-10-05 10:48:01, The format should be yyyy-MM-dd'T'HH:mm:ssZ"
      ,btcException.developerMessage)
  }

  @Test
  fun `verifyGetBtcHistoryDetailIsSuccessful`(){
    val startDateTimeCapture = argumentCaptor<LocalDateTime>()
    val endDateTimeCapture = argumentCaptor<LocalDateTime>()
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val btcEntity1 = BtcEntity(null, LocalDateTime.parse("2011-10-05 10:48:01", dateTimeFormatter), BigDecimal(20))
    val btcEntity2 = BtcEntity(null, LocalDateTime.parse("2011-10-05 10:45:01", dateTimeFormatter), BigDecimal(20))
    val btcEntity3 = BtcEntity(null, LocalDateTime.parse("2011-10-05 12:48:01", dateTimeFormatter), BigDecimal(20))
    val btcEntity4 = BtcEntity(null, LocalDateTime.parse("2011-10-05 09:48:01", dateTimeFormatter), BigDecimal(20))
    val btcEntity5 = BtcEntity(null, LocalDateTime.parse("2011-10-05 11:48:01", dateTimeFormatter), BigDecimal(20))
    val btcGroup = listOf(btcEntity1, btcEntity2, btcEntity3, btcEntity4, btcEntity5)
    Mockito.`when`(btcRepository.getBtcWithTimeRangeInHour(startDateTimeCapture.capture(), endDateTimeCapture.capture()))
      .thenReturn(btcGroup)
    val response = bitcoinService.getTransactionHistoryDetail(SearchBtcRequest("2011-10-05T08:48:01+00:00", "2011-10-05T11:48:01+00:00"))
    assertEquals("2011-10-05T08:48:01",startDateTimeCapture.firstValue.toString())
    assertEquals("2011-10-05T11:48:01",endDateTimeCapture.firstValue.toString())
    assertEquals(BigDecimal("20"),response[0].amount)
    assertEquals("2011-10-05T12:00:00+00:00",response[0].startDatetime)
    assertEquals(BigDecimal("20"),response[1].amount)
    assertEquals("2011-10-05T09:00:00+00:00",response[1].startDatetime)
    assertEquals(BigDecimal("20"),response[2].amount)
    assertEquals("2011-10-05T11:00:00+00:00",response[2].startDatetime)
    assertEquals(BigDecimal("40"),response[3].amount)
    assertEquals("2011-10-05T10:00:00+00:00",response[3].startDatetime)
  }
}