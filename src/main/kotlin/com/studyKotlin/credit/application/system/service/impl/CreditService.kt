package com.studyKotlin.credit.application.system.service.impl

import com.studyKotlin.API_Rest_Kotlin.domain.model.Credit
import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CreditRepository
import com.studyKotlin.credit.application.system.service.ICreditInterface
import java.util.*

class CreditService(private val creditRepository: CreditRepository,
                    private  val customerService: CustomerService) : ICreditInterface {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
       return creditRepository.save(credit)
    }

    override fun findByCustomer(customerId: Long): List<Credit> {
    return creditRepository.finsByAllCustomerId(customerId)

    }

    override fun findByCreditCode(creditCode: UUID, idCustomer: Long): Credit {
       val credit: Credit? = creditRepository.findByCreditCode(creditCode) ?: throw RuntimeException("CreditCode $creditCode not found")
       return if (credit?.customer!!.id!!.equals(idCustomer)) credit else throw RuntimeException("Contact admin")
    }

    override fun delete(id: Long) {
       creditRepository.deleteById(id)
    }
}