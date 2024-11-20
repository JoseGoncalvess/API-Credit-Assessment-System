package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.API_Rest_Kotlin.domain.model.Credit
import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDTO(
    val creditValue: BigDecimal,
    val dayFirstInstallment: LocalDate,
    val customerId: Long,
    val numberOfInstallment: Int ,
) {

   fun toEntity(): Credit  = Credit(
       creditValue = this.creditValue,
       dayFirstInstallment = this.dayFirstInstallment,
       numberOfInstallment = this.numberOfInstallment,
       customer =  Customer(id = this.customerId)

   )
}
