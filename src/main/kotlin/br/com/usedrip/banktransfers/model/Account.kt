package br.com.usedrip.banktransfers.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
data class Account(
    @Id
    var code: Int? = null,
    var name: String="",
    @JsonIgnore
    @ManyToOne
    var bank: Bank?=null,
    @JsonIgnore
    @ManyToOne
    var client: Client?=null,
    @OneToMany(mappedBy = "accountTo")
    var transfersSend: List<Transfer> = ArrayList(),
    @OneToMany(mappedBy = "accountFor")
    var transfersReceived: List<Transfer> = ArrayList()
)
