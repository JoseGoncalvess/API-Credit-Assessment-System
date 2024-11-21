package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.credit.application.system.domain.model.Customer
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

class CustomerUpdateDto(
    @field:NotEmpty(message = "The firstName are't Empty or  Null")
    val firstName: String,
    @field:NotEmpty(message = "The lastName are't Empty or  Null")
    val lastName: String,
    @field:NotNull(message = "The inCome are't Null")
    val inCome: BigDecimal,
    @field:NotEmpty(message = "The zipCode are't Empty or  Null")
    var zipCode: String,
    @field:NotEmpty(message = "The street are't Empty or  Null")
    var street: String,
) {
fun  toEntity(customer: Customer) : Customer {
    customer.firstName = this.firstName;
    customer.lastName = this.lastName;
    customer.inCome = this.inCome;
    customer.address.street = this.street;
    customer.address.zipCode = this.zipCode

    return  customer
}
}
