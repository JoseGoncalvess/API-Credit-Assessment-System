package com.studyKotlin.credit.application.system.service

import com.studyKotlin.credit.application.system.domain.model.Customer

interface ICustomerInterface {
    fun  save(customer: Customer) : Customer
    fun  findById(id: Long) : Customer
    fun delete(id: Long)

}