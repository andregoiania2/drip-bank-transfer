package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.service.AgentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transfer")
class TransferController(private val agentService: AgentService) {
    @PostMapping
    fun transfer(@RequestBody transferForm: TransferForm) : TransfersDTO {
        return agentService.execute(transferForm)
    }
}