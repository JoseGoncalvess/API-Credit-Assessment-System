package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.credit.application.system.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.model.Customer
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDTO(
    @field:NotNull(message = "The creditValue are't Null")
    val creditValue: BigDecimal,
    @field:Future( message = "creation date must be future or present.")
    val dayFirstInstallment: LocalDate,
    @field:NotNull(message = "The creditValue are't Null")
    val customerId: Long,
    @field:NotNull(message = "The numberOfInstallment are't Null")
    @field:Max(value = 48, message = "The installments cannot be greater than 5")
    @field:Min(value = 1 , message = "The installments cannot be greater smaller  1")
    val numberOfInstallment: Int ,
) {

   fun toEntity(): Credit = Credit(
       creditValue = this.creditValue,
       dayFirstInstallment = this.dayFirstInstallment,
       numberOfInstallment = this.numberOfInstallment,
       customer =  Customer(id = this.customerId)

   )
}
