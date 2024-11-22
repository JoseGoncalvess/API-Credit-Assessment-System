package com.studyKotlin.credit.application.system.service.impl

import com.studyKotlin.credit.application.system.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.repository.CreditRepository
import com.studyKotlin.credit.application.system.excepion.BusinessException
import com.studyKotlin.credit.application.system.service.ICreditInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.*

@Service
class CreditService @Autowired  constructor (private val creditRepository: CreditRepository,
                                             private  val customerService: CustomerService
) : ICreditInterface {
    override fun save(credit: Credit): Credit {
        val today : LocalDate = LocalDate.now()
        if (credit.dayFirstInstallment <= today.plusDays(90) && credit.dayFirstInstallment >= today){
            credit.apply {
                customer = customerService.findById(credit.customer?.id!!)
            }
            return creditRepository.save(credit)
        }else{
            throw BusinessException("The first installment must be paid within 90 days of applying for the loan.")
        }

    }

    override fun findByCustomer(customerId: Long): List<Credit> {
    return creditRepository.finsByAllCustomerId(customerId)

    }

    override fun findByCreditCode(creditCode: UUID, idCustomer: Long): Credit {
       val credit: Credit? = creditRepository.findByCreditCode(creditCode) ?: throw RuntimeException("CreditCode $creditCode not found")
       return if (credit?.customer!!.id!!.equals(idCustomer)) credit else throw BusinessException("Contact admin")
    }

    override fun delete(id: Long) {
       creditRepository.deleteById(id)
    }
}