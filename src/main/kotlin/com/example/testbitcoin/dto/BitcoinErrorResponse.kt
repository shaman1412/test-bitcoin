package com.example.testbitcoin.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL


@JsonInclude(NON_NULL)
data class BitcoinErrorResponse(
  var code : String?,
  var tiltle: String?,
  var message: String?,
  var developerMessage: String?
)