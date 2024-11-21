package com.studyKotlin.credit.application.system.service

import com.studyKotlin.credit.application.system.domain.model.Credit
import java.util.*

interface ICreditInterface {
    fun  save(credit: Credit) : Credit
    fun  findByCustomer(customerId: Long) : List<Credit>
    fun findByCreditCode(creditCode: UUID,idCustomer: Long) : Credit

    fun delete(id: Long)
}