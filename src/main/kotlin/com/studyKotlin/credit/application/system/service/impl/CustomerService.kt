package com.studyKotlin.credit.application.system.service.impl

import com.studyKotlin.credit.application.system.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CustomerRepository
import com.studyKotlin.credit.application.system.excepion.BusinessException
import com.studyKotlin.credit.application.system.service.ICustomerInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class CustomerService @Autowired constructor(private val customerRepository: CustomerRepository) : ICustomerInterface {
    override fun save(customer: Customer): Customer {
        return customerRepository.save(customer)
    }

    override fun findById(id: Long): Customer {
        return customerRepository.findById(id).orElseThrow {
            throw BusinessException("Id $id not foud")
        }
    }

    override fun delete(id: Long) {
        val customer: Optional<Customer> = this.customerRepository.findById(id)
        customerRepository.delete(customer.get())
    }
}