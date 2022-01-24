package com.example.testbitcoin.filter

import com.example.testbitcoin.constant.StatusCode
import com.example.testbitcoin.dto.BitcoinErrorResponse
import com.example.testbitcoin.exception.BTCCompositeException
import com.example.testbitcoin.service.BitcoinService
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.transaction.CannotCreateTransactionException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.UnexpectedTypeException

@RestControllerAdvice
class GlobalControllerAdvice(
  val messageSource : MessageSource
) {

  private val log = LoggerFactory.getLogger(BitcoinService::class.java)

  @ExceptionHandler(BTCCompositeException::class)
  fun handleBtcException(exception : BTCCompositeException) : HttpEntity<BitcoinErrorResponse> {
    log.error("BTCCompositeException: $exception, ${exception.printStackTrace()} ")
    val locale = LocaleContextHolder.getLocale()
    val title = messageSource.getMessage("TTL.${exception.code}", null, locale)
    val msg = messageSource.getMessage("MSG.${exception.code}", null, locale)
    val result = BitcoinErrorResponse(exception.code, title, msg, exception.developerMessage)
    return ResponseEntity(result, exception.httpStatus)
  }

  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun handleBtcException(exception : MethodArgumentNotValidException) : HttpEntity<BitcoinErrorResponse> {
    log.error("MethodArgumentNotValidException: $exception, ${exception.printStackTrace()} ")
    val locale = LocaleContextHolder.getLocale()
    val title = messageSource.getMessage("TTL.${StatusCode.STATUS400.value}", null, locale)
    val msg = messageSource.getMessage("MSG.${StatusCode.STATUS400.value}", null, locale)
    val result = BitcoinErrorResponse(StatusCode.STATUS400.value, title, msg, exception.bindingResult.fieldError?.defaultMessage)
    return ResponseEntity(result, BAD_REQUEST)
  }

  @ExceptionHandler(UnexpectedTypeException::class)
  fun handleBtcException(exception : UnexpectedTypeException) : HttpEntity<BitcoinErrorResponse> {
    log.error("UnexpectedTypeException: $exception, ${exception.printStackTrace()} ")
    val locale = LocaleContextHolder.getLocale()
    val title = messageSource.getMessage("TTL.${StatusCode.STATUS400.value}", null, locale)
    val msg = messageSource.getMessage("MSG.${StatusCode.STATUS400.value}", null, locale)
    val result = BitcoinErrorResponse(StatusCode.STATUS400.value, title, msg, exception.message)
    return ResponseEntity(result, BAD_REQUEST)
  }

  @ExceptionHandler(CannotCreateTransactionException::class)
  fun handleDefaultException(exception : CannotCreateTransactionException) : HttpEntity<BitcoinErrorResponse> {
    log.error("Can't connect DB with exception: $exception, ${exception.printStackTrace()} ")
    val locale = LocaleContextHolder.getLocale()
    val title = messageSource.getMessage("TTL.${StatusCode.STATUS500.value}", null, locale)
    val msg = messageSource.getMessage("MSG.${StatusCode.STATUS500.value}", null, locale)
    val result = BitcoinErrorResponse(StatusCode.STATUS500.value, title, msg, exception.message)
    return ResponseEntity(result, INTERNAL_SERVER_ERROR)
  }

  @ExceptionHandler(Exception::class)
  fun handleDefaultException(exception : Exception) : HttpEntity<BitcoinErrorResponse> {
    log.error("Exception occurred: $exception, ${exception.printStackTrace()} ")
    val locale = LocaleContextHolder.getLocale()
    val title = messageSource.getMessage("TTL.${StatusCode.STATUS500.value}", null, locale)
    val msg = messageSource.getMessage("MSG.${StatusCode.STATUS500.value}", null, locale)
    val result = BitcoinErrorResponse(StatusCode.STATUS500.value, title, msg, exception.message)
    return ResponseEntity(result, INTERNAL_SERVER_ERROR)
  }


}