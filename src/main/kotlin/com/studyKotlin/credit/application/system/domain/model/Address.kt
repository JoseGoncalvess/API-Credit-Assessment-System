package com.studyKotlin.credit.application.system.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

@Embeddable
data class Address(
        @Column(nullable = false)
        var zipCode: String = "",
        @Column(nullable = false)
        var street: String = "" )