package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.dto.AccountDTO
import br.com.usedrip.banktransfers.dto.AccountForm
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AccountController(private val accountService: AccountService) {

    @GetMapping
    @Operation(summary = "Devolve várias opções de nomes")
    fun list() : List<AccountDTO> {
        return accountService.list()
    }

    @PostMapping
    fun add(@RequestBody @Valid form : AccountForm): Account {
        return accountService.add(form)
    }

    @GetMapping("/{code}")
    fun findById(@PathVariable code : Int) : AccountDTO? {
        return accountService.findById(code)
    }

}