package br.com.usedrip.banktransfers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankTransfersApplication

fun main(args: Array<String>) {
	runApplication<BankTransfersApplication>(*args)
}
