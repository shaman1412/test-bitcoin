package com.example.testbitcoin.dto

import java.math.BigDecimal


data class AllBtcDetailResponse(
  var startDatetime : String,
  var amount : BigDecimal
)