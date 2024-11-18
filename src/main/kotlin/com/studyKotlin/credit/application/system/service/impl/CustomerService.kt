package com.studyKotlin.credit.application.system.service.impl

import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CustomerRepository
import com.studyKotlin.credit.application.system.service.ICustomerInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class CustomerService( private  val  customerRepository : CustomerRepository) : ICustomerInterface {
    @Autowired

    override fun save(customer: Customer): Customer {
       return customerRepository.save(customer)
    }

    override fun findById(id: Long): Customer {
       return  customerRepository.findById(id).orElseThrow{
            throw  RuntimeException("Id $id not foud")
        }
    }

    override fun delete(id: Long) {
       customerRepository.deleteById(id)
    }
}