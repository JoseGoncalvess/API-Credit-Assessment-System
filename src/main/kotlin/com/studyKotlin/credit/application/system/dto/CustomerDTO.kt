package com.studyKotlin.credit.application.system.dto

import com.studyKotlin.credit.application.system.domain.model.Address
import com.studyKotlin.credit.application.system.domain.model.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDTO (
    @field:NotEmpty(message = "The firstName are't Empty or  Null")
    val firstName: String,
    @field:NotEmpty(message = "The lastName are't Empty or  Null")
    val lastName: String,
    @field:NotEmpty(message = "The cpf are't Empty or  Null")
    @field:NotNull(message = "The cpf are't Empty or  Null")
    @field:CPF(message =  "The cpf invalid, verify input")
    val cpf: String,
    @field:NotEmpty(message = "The email are't Empty or  Null")
    @field:Email(message = "E-mail Invalid, verify to input")
    val email: String ,
    @field:NotEmpty(message = "The password are't Empty or  Null")
    @field:NotNull(message = "The password are't Null")
    var password: String,
    @field:NotNull(message = "The inCome are't Null")
    val inCome: BigDecimal,
    @field:NotEmpty(message = "The zipCode are't Empty or  Null")
    @field:NotNull(message = "The zipCod are Null")
    var zipCode: String,
    @field:NotEmpty(message = "The street are't Empty or  Null")
    var street: String,
){

    fun  toEntity(): Customer = Customer(this.firstName,
        this.lastName,this.cpf,this.email,
        this.password, Address(this.zipCode,this.street)
    )
}
