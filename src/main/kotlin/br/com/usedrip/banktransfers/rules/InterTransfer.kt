package br.com.usedrip.banktransfers.rules

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.error.CustomException
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.service.TransferService
import java.math.BigDecimal

class InterTransfer(  proximo: TransferRule,
    transferServer: TransferService,
    accountTo: Account,
    accountFor: Account,
    transferForm: TransferForm
) : TransferRule(proximo,
                 transferServer,
                 accountTo,
                 accountFor,
                 transferForm) {
    override fun getComission(transferForm: TransferForm): BigDecimal {
        return BigDecimal.valueOf(5.0)
    }

    override fun validateLimit(transferForm: TransferForm) {
        val limiteTransaction : BigDecimal = BigDecimal.valueOf(5000.0)
        val msg : String = "Interbank transfers, between accounts at different banks have a limit of R\$ 5000.00 per transfer"
        if (transferForm.amount > limiteTransaction) {
            throw CustomException(msg)
        }
    }
    override fun getProbabilityFall(transferForm: TransferForm): Boolean {
        val numeroAleatorio = (0..100).random()
        return numeroAleatorio % 2 == 0 
    }

    override fun nextTransfer(accountTo: Account, accountFor: Account): Boolean {
       return (accountTo.bank?.code == accountFor.bank?.code)
    }
}