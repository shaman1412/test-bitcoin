package com.example.testbitcoin.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonInclude(NON_NULL)
@JsonNaming(SnakeCaseStrategy::class)
data class BitcoinCompositeResponse(
  var code: String?,
  var message: String?
)