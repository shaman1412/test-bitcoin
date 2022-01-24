package com.example.testbitcoin.filter

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Locale
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE )
class LocaleFilter : OncePerRequestFilter() {

  private val DEFAULT_LOCALE = Locale("en")
  private val LOCALE_LIST = listOf(Locale("th"), Locale("en"))

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    LocaleContextHolder.setLocale(resolveLocale(request))
    filterChain.doFilter(request, response)
  }
  private fun resolveLocale(request: HttpServletRequest): Locale {
    return try {
      if (request.getHeader(HttpHeaders.ACCEPT_LANGUAGE) == null) {
        DEFAULT_LOCALE
      } else {
        val list = Locale.LanguageRange.parse(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE))
        Locale.lookup(list, LOCALE_LIST)
      }
    }catch (exception : Exception){
      DEFAULT_LOCALE
    }

  }
}