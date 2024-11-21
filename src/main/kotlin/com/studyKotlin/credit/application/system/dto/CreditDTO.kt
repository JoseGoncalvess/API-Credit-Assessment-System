package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.API_Rest_Kotlin.domain.model.Credit
import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDTO(
    @field:NotNull(message = "The creditValue are't Null")
    val creditValue: BigDecimal,
    @field:Future(message = "creation date must be future or present.")
    val dayFirstInstallment: LocalDate,
    @field:NotNull(message = "The creditValue are't Null")
    val customerId: Long,
    @field:NotNull(message = "The numberOfInstallment are't Null")
    @field:Max(value = 5)
    @field:Min(value = 1)
    val numberOfInstallment: Int ,
) {

   fun toEntity(): Credit  = Credit(
       creditValue = this.creditValue,
       dayFirstInstallment = this.dayFirstInstallment,
       numberOfInstallment = this.numberOfInstallment,
       customer =  Customer(id = this.customerId)

   )
}
