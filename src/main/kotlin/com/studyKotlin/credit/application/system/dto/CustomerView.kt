package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.credit.application.system.domain.model.Customer
import java.math.BigDecimal

data class CustomerView(
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val email: String,
    var password: String,
    val inCome: BigDecimal,
    var zipCode: String,
    var street: String,
    var id : Long
) {

    constructor(customer: Customer): this (
        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        email = customer.email,
        password = customer.password,
        inCome = customer.inCome,
        zipCode = customer.address.zipCode,
        street = customer.address.street,
        id = customer.id!!
    )

}
