package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.model.Account
import org.springframework.stereotype.Service

@Service
class AgentService(
    private val interService: InterTransferService,
    private val intraService: IntraTransferService,
    private val accountService: AccountService
) {
    fun execute(transferForm: TransferForm) : TransfersDTO {
        val accountTo : Account = accountService.getAccountTo(transferForm.accountTo)
        val accountFor : Account = accountService.getAccountFor(transferForm.accountFor)
        if (accountTo.bank?.code != accountFor.bank?.code)
            return interService.convert(transferForm,accountTo,accountFor)
        else return intraService.convert(transferForm,accountTo,accountFor)
    }
}