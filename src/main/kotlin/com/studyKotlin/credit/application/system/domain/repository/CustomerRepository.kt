package com.studyKotlin.credit.application.system.domain.repository;

import com.studyKotlin.credit.application.system.domain.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
}