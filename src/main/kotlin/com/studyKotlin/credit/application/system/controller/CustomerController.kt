package com.studyKotlin.credit.application.system.controller

import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import com.studyKotlin.credit.application.system.dto.CustomerDTO
import com.studyKotlin.credit.application.system.dto.CustomerUpdateDto
import com.studyKotlin.credit.application.system.dto.CustomerView
import com.studyKotlin.credit.application.system.service.impl.CustomerService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ipi/customers")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    fun  saveCustomer(@RequestBody customer: CustomerDTO): String{
        customerService.save(customer.toEntity())
        return  "Customer ${customer.email} saved"
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Long) : CustomerView{
        val customer : Customer = this.customerService.findById(id)
        return  CustomerView(customer = customer)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomerById(@PathVariable id : Long) : String{
      this.customerService.delete(id)
        return  "Customer with id ->  $id Deleted "
    }

    @PatchMapping
    fun updateCostumer(@RequestParam(value = "customerid") id : Long, @RequestBody customerUpdateDto : CustomerUpdateDto) : CustomerView{
       var customer:Customer = this.customerService.findById(id)
        var customerUpdate : Customer = customerUpdateDto.toEntity(customer)
         this.customerService.save(customerUpdate)

        return CustomerView(customerUpdate)

    }

}
