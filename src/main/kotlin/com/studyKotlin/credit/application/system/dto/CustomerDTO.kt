package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.API_Rest_Kotlin.domain.model.Address
import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import java.math.BigDecimal

data class CustomerDTO (
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val email: String ,
    var password: String,
    val inCome: BigDecimal,
    var zipCode: String,
    var street: String,
){

    fun  toEntity():Customer = Customer(this.firstName,
        this.lastName,this.cpf,this.email,
        this.password, Address(this.zipCode,this.street))
}
