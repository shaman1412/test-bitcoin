package com.example.testbitcoin.controller

import com.example.testbitcoin.dto.AddBtcRequest
import com.example.testbitcoin.dto.AllBtcDetailResponse
import com.example.testbitcoin.dto.BitcoinCompositeResponse
import com.example.testbitcoin.dto.SearchBtcRequest
import com.example.testbitcoin.service.BitcoinService
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bitcoin-composite")
class BitcoinController(
  var bitcoinService: BitcoinService
) {

  @PostMapping("/v1/detail")
  fun getBitcoinDetail(@Validated @RequestBody searchBtcRequest: SearchBtcRequest) : HttpEntity<List<AllBtcDetailResponse>>{
    return ResponseEntity(bitcoinService.getTransactionHistoryDetail(searchBtcRequest), HttpStatus.OK)
  }

  @PostMapping("/v1/addBtc")
  fun addBitcoin(@Validated @RequestBody addBtcRequest: AddBtcRequest) : HttpEntity<BitcoinCompositeResponse>{
    return ResponseEntity(bitcoinService.addBTC(addBtcRequest), CREATED)
  }

}