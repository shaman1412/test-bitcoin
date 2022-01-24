package com.example.testbitcoin.exception

import org.springframework.http.HttpStatus

class BTCCompositeException(
  var httpStatus: HttpStatus,
  var code: String,
  var developerMessage: String
) : RuntimeException() {
}