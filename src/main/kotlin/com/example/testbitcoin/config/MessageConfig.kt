package com.example.testbitcoin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource

@Configuration
class MessageConfig {

  @Bean
  fun messageSource(): ResourceBundleMessageSource{
    val messageSourceResourceBundle = ResourceBundleMessageSource()
    messageSourceResourceBundle.setBasenames("i18n/messages")
    messageSourceResourceBundle.setDefaultEncoding("UTF-8")
    messageSourceResourceBundle.setUseCodeAsDefaultMessage(true)
    return messageSourceResourceBundle;
  }
}
