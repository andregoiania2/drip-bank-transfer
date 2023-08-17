package br.com.usedrip.banktransfers.dto

data class AccountDTO(
    var code: Int?,
    var name: String,
    var bank: String,
    var client: String,
    var transfers: List<TransfersDTO> = ArrayList()
)