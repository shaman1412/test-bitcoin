package com.example.testbitcoin.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class AddBtcRequest(
  @field:NotBlank(message = "datetime is missing")
  var datetime : String?,

  @field:NotNull(message = "amount must not be null")
  var amount: BigDecimal?
)