package com.studyKotlin.credit.application.system.controller

import com.studyKotlin.API_Rest_Kotlin.domain.model.Credit
import com.studyKotlin.API_Rest_Kotlin.domain.model.Customer
import com.studyKotlin.credit.application.system.dto.*
import com.studyKotlin.credit.application.system.service.impl.CreditService
import com.studyKotlin.credit.application.system.service.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import java.util.stream.Collectors


@RestController
@RequestMapping("/api/credits")
class CreditController(
    private val creditService: CreditService,
    private val customerService: CustomerService
) {
    @PostMapping
    fun saveCredit(@RequestBody creditDTO: CreditDTO): ResponseEntity<String> {
        val credit: Credit = creditService.save(creditDTO.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body("Credit ${credit.customer?.email} saved Sucessive")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<List<CreditViewList>> {

        val credList: List<CreditViewList> = creditService.findByCustomer(id).stream().map { credit: Credit ->
            CreditViewList(credit)
        }.collect(Collectors.toList<CreditViewList>())
        return ResponseEntity.status(HttpStatus.OK).body(credList)

    }

    @GetMapping
    fun findByCredCod(
        @RequestParam(value = "customerid") customerId: Long,
        @RequestParam(value = "credCod") credCod: UUID
    ): ResponseEntity<CreditView> {
        val credit: Credit = creditService.findByCreditCode(credCod, customerId)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))

    }

    @DeleteMapping("/{id}")
    fun deleteCredit(@PathVariable id: Long): ResponseEntity<String> {
        creditService.delete(id)
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer with id ->  $id Deleted ")
    }
//
//    @PatchMapping
//    fun updateCostumer(@RequestParam(value = "customerid") id : Long,
//                       @RequestBody customerUpdateDto : CustomerUpdateDto) : CreditView {
//        var customer: Customer = this.creditService.findById(id)
//        var customerUpdate : Customer = customerUpdateDto.toEntity(customer)
//        this.creditService.save(customerUpdate)
//
//        return CustomerView(customerUpdate)
//
//    }
}