package com.studyKotlin.credit.application.system.service

import com.studyKotlin.API_Rest_Kotlin.domain.model.Credit
import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import java.util.UUID

interface ICustomerInterface {
    fun  save(customer: Customer) : Customer
    fun  findById(id: Long) : Customer
    fun delete(id: Long)

}