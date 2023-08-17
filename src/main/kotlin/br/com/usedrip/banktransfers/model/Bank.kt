package br.com.usedrip.banktransfers.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Bank(
    @Id
    var code: Int? = null,
    var name: String="",
    @OneToMany(mappedBy = "bank")
    val accounts: List<Account> = ArrayList()
)
