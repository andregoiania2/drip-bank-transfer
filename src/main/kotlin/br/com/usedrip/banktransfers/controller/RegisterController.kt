package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.dto.AccountDTO
import br.com.usedrip.banktransfers.dto.RegisterForm
import br.com.usedrip.banktransfers.service.RegisterService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/register")
class RegisterController(private val service: RegisterService) {
    @PostMapping
    fun add(@RequestBody @Valid form: RegisterForm) : AccountDTO {
        return service.add(form)
    }
}