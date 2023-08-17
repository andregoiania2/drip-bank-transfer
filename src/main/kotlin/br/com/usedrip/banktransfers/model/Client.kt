package br.com.usedrip.banktransfers.model

import jakarta.persistence.*

@Entity
data class Client(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String="",
    val documentCPF: String="",
    @OneToMany(mappedBy = "client")
    val accounts: List<Account> = ArrayList()
)
