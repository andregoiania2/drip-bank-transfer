package br.com.usedrip.banktransfers.rules

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.service.TransferService
import java.math.BigDecimal

abstract class TransferRule(private var proximo: TransferRule?,
                            private var transferServer: TransferService,
                            private var accountTo: Account,
                            private var accountFor: Account,
                            private var transferForm: TransferForm
                            ) {
    fun execute() : TransfersDTO {
        if (!nextTransfer(this.accountTo, this.accountFor)) {
            validateLimit(this.transferForm)
            return this.transferServer.transfer(
                this.transferForm,
                getComission(this.transferForm),
                this.accountTo,
                this.accountFor,
                getProbabilityFall(this.transferForm)
            )
        } else
            return this.proximo!!.execute()
    }

    abstract fun getComission(transferForm: TransferForm) : BigDecimal
    abstract fun validateLimit(transferForm: TransferForm)
    abstract fun getProbabilityFall(transferForm: TransferForm) : Boolean
    abstract fun nextTransfer(accountTo : Account, accountFor : Account) : Boolean

}