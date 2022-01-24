package com.example.testbitcoin.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchBtcRequest(
  @field:NotBlank(message = "startDatetime is missing")
  var startDatetime: String?,

  @field:NotBlank(message = "endDatetime is missing")
  var endDatetime: String?
)