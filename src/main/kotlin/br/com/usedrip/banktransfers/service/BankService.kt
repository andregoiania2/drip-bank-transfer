package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.error.CustomExceptionNotFound
import br.com.usedrip.banktransfers.model.Bank
import br.com.usedrip.banktransfers.repository.BankRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BankService(private val bankRepository: BankRepository,
                  private val notFoundMessage: String = "bank code not found!") {
    fun add(bank: Bank) : Bank {
        return bankRepository.save(bank)
    }

    fun findAll() : List<Bank> {
        return bankRepository.findAll()
    }

    fun findById(code: Int) : Bank {
       return bankRepository.findById(code)
            .orElseThrow{CustomExceptionNotFound(notFoundMessage)}
    }

    fun findByIdOptional(code: Int) : Optional<Bank> {
        return bankRepository.findById(code)
    }
}