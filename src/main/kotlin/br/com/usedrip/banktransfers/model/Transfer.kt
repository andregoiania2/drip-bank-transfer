package br.com.usedrip.banktransfers.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Transfer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val comission: BigDecimal=BigDecimal.ZERO,
    val amount: BigDecimal=BigDecimal.ZERO,
    val success: Boolean=true,
    @JsonIgnore
    @ManyToOne
    val accountTo: Account?=null,
    @JsonIgnore
    @ManyToOne
    val accountFor: Account?=null
)
