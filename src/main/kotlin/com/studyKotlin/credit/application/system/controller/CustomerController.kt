package com.studyKotlin.credit.application.system.controller

import com.studyKotlin.credit.application.system.domain.model.Customer
import com.studyKotlin.credit.application.system.dto.CustomerDTO
import com.studyKotlin.credit.application.system.dto.CustomerUpdateDto
import com.studyKotlin.credit.application.system.dto.CustomerView
import com.studyKotlin.credit.application.system.service.impl.CustomerService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController @Autowired constructor( private val customerService: CustomerService) {

    @PostMapping
    fun saveCustomer(@RequestBody @Valid customer: CustomerDTO): ResponseEntity<CustomerView> {
       val customerSaved: Customer  =  customerService.save(customer.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerView(customerSaved))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerService.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer = customer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomerById(@PathVariable id: Long): ResponseEntity<String> {
        this.customerService.delete(id)
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer with id ->  $id Deleted ")
    }

    @PatchMapping
    fun updateCostumer(
        @RequestParam(value = "customerid") id: Long,
        @RequestBody @Valid customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerView> {
        var customer: Customer = this.customerService.findById(id)
        var customerUpdate: Customer = customerUpdateDto.toEntity(customer)
        this.customerService.save(customerUpdate)

        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerView(customerUpdate))

    }

}
