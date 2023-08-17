package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.dto.BankForm
import br.com.usedrip.banktransfers.model.Bank
import br.com.usedrip.banktransfers.service.BankService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bank")
class BankController(private val bankService: BankService) {

    @GetMapping
    fun list() : List<Bank> {
        return bankService.findAll()
    }

    @PostMapping
    fun add(@RequestBody @Valid form: BankForm) : Bank {
        val bank : Bank = form.convert()
        return bankService.add(bank)
    }

    @GetMapping("/{code}")
    fun findById(@PathVariable code : Int) : Bank {
        return bankService.findById(code)
    }
}