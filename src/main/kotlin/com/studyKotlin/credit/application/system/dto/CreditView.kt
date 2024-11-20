package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.API_Rest_Kotlin.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.enumeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class CreditView(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val stattus : Status,
    val numberOfInstallment: Int,
    val emailCustomer : String?,
    val incodeCustomer : BigDecimal?
) {
    constructor(credit: Credit): this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallment= credit.numberOfInstallment,
        stattus = credit.status,
        emailCustomer =credit.customer?.email,
        incodeCustomer = credit.customer?.inCome
    )
}
