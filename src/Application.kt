package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import jakarta.validation.Validation
import jakarta.validation.Validator
import kotlinx.serialization.json.Json


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {


    val validatorFactory = Validation.buildDefaultValidatorFactory()
    val validator: Validator = validatorFactory.validator


    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    routing {
        post("/register-enterprise-customer") {
            val customer = call.receive<EnterpriseCustomer>()
            val validationErrors = validator.validate(customer)
            if (validationErrors.isNotEmpty())
            {
                call.respond(HttpStatusCode.BadRequest)
            }
            call.respondText("Created enterprise customer with name ${customer.name}", contentType = ContentType.Text.Plain)
        }
    }
}
/*
suspend inline fun <reified T : Any> ApplicationCall.bindJson(): T {
    val dto = this.receive<T>()
    val violations = validator.validate(dto);
    if (violations.size > 0) {
        // Throw error messages when found violdations
        val details = violations.map {
            val propertyName = it.propertyPath.toString()
            val errorMessage = it.message
            "${propertyName}: $errorMessage"
        }
        // Your custom Exception in Status Page feature of Ktor application
        throw CustomException(details)
    } else {
        return dto
    }
}*/