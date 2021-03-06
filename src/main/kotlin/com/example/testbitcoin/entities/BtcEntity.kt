package com.example.testbitcoin.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "btc")
@JsonInclude(NON_NULL)
class BtcEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id : Long?,

  @Column(name = "datetime")
  var dataTime: LocalDateTime,

  var amount: BigDecimal
)