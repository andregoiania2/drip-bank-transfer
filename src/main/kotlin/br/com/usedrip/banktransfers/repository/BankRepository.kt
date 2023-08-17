package br.com.usedrip.banktransfers.repository

import br.com.usedrip.banktransfers.model.Bank
import org.springframework.data.jpa.repository.JpaRepository

interface BankRepository: JpaRepository<Bank, Int> {
}