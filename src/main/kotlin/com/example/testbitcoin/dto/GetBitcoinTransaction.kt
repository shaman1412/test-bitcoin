package com.example.testbitcoin.dto

import java.math.BigDecimal
import java.sql.Date


interface GetBitcoinTransaction{

  fun getDate(): Date

  fun getHour(): Int

  fun getTotalAmount(): BigDecimal
}