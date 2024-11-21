package com.studyKotlin.credit.application.system.domain.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Customer(
        @Column(nullable = false)
        var firstName: String = "",
        @Column(nullable = false)
        var lastName: String = "",
        @Column(nullable = false, unique = true)
        val cpf: String = "",
        @Column(nullable = false, unique = true)
        val email: String = "",
        @Column(nullable = false)
        var password: String = "",
        @Column(nullable = false)
        @Embedded
        var address: Address = Address(),
        @Column(nullable = false)
        var inCome: BigDecimal = BigDecimal.ZERO,
        @Column(nullable = false)
        @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE, CascadeType.PERSIST), mappedBy = "customer")
        val credits: List<Credit> = mutableListOf(),
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id:Long? = null
) {

}