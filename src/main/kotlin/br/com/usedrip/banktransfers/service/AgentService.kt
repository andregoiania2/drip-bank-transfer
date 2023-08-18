package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.model.Account
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AgentService(
    private val interService: InterTransferService,
    private val intraService: IntraTransferService,
    private val customTransferService: CustonTransferService,
    private val accountService: AccountService
) {
    fun execute(transferForm: TransferForm) : TransfersDTO {
        val accountTo : Account = accountService.getAccountTo(transferForm.accountTo)
        val accountFor : Account = accountService.getAccountFor(transferForm.accountFor)
        var transfersDTO : TransfersDTO;
        if (accountTo.bank?.code != accountFor.bank?.code)
            transfersDTO =  interService.convert(transferForm,accountTo,accountFor)
        else
            if (transferForm.amount > BigDecimal.valueOf(2000.00))
                transfersDTO = customTransferService.convert(transferForm,accountTo,accountFor)
            else transfersDTO = intraService.convert(transferForm,accountTo,accountFor)

        return transfersDTO;
    }
}