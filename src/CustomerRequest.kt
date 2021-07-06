package com.example

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.NotNull

data class Customer(val firstName: String, val lastName: String)

@Serializable
data class EnterpriseCustomer(
    @field:NotNull
    @field:NotEmpty
    val name: String? = null,

    @field:NotNull
    @field:Min(10)
    val noOfEmployees: Long? = null
)