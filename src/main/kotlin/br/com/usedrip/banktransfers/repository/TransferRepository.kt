package br.com.usedrip.banktransfers.repository

import br.com.usedrip.banktransfers.model.Transfer
import org.springframework.data.jpa.repository.JpaRepository

interface TransferRepository: JpaRepository<Transfer, Long> {

}