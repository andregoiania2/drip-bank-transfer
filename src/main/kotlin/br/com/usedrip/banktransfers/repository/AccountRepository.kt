package br.com.usedrip.banktransfers.repository

import br.com.usedrip.banktransfers.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Int> {
}