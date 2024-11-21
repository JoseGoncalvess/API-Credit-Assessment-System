package com.studyKotlin.credit.application.system.domain.repository;


import com.studyKotlin.credit.application.system.domain.model.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditRepository : JpaRepository<Credit, Long> {

    fun  findByCreditCode(creditCode: UUID) : Credit?

    @Query(value = "SELECT * FROM CREDIT WHERE CUSTOMER_ID = ?1 ", nativeQuery = true)
    fun finsByAllCustomerId(customerId : Long) : List<Credit>

}