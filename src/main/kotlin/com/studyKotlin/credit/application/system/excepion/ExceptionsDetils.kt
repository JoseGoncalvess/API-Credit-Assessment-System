package com.studyKotlin.credit.application.system.excepion

import java.time.LocalDate
import java.time.LocalDateTime

data class ExceptionsDetils(
    val title: String,
    val timestamp: LocalDateTime,
    val exception: String,
    val details: MutableMap<String, String?>
)
