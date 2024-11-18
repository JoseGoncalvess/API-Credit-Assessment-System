package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import java.math.BigDecimal

class CustomerUpdateDto(
    val firstName: String,
    val lastName: String,
    val inCome: BigDecimal,
    var zipCode: String,
    var street: String,
) {
fun  toEntity(customer: Customer) : Customer{
    customer.firstName = this.firstName;
    customer.lastName = this.lastName;
    customer.inCome = this.inCome;
    customer.address.street = this.street;
    customer.address.zipCode = this.zipCode

    return  customer
}
}
