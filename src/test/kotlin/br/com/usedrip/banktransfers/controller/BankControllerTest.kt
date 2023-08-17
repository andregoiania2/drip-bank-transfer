package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.Mocks
import br.com.usedrip.banktransfers.dto.BankForm
import br.com.usedrip.banktransfers.model.Bank
import br.com.usedrip.banktransfers.repository.BankRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bankRepository: BankRepository

    private val mocks : Mocks = Mocks()

    @Test
    fun `should return list of banks`() {
        val banks = mocks.getListBank()

        `when`(bankRepository.findAll()).thenReturn(banks)

        val andExpect = mockMvc.perform(get("/bank"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].code").value(1))
            .andExpect(jsonPath("$[0].name").value("Bank 1"))
            .andExpect(jsonPath("$[1].code").value(2))
            .andExpect(jsonPath("$[1].name").value("Bank 2"))
    }

    @Test
    fun `should add a new bank`() {
        val bankForm = BankForm(1, "Bank 1")
        val bank = Bank(1, "Bank 1")

        `when`(bankRepository.save(any())).thenReturn(bank)

        mockMvc.perform(post("/bank")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper().writeValueAsString(bankForm)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(1))
            .andExpect(jsonPath("$.name").value("Bank 1"))
    }

    @Test
    fun `should find bank by code`() {
        val opt = mocks.getOptBank()

        `when`(bankRepository.findById(1)).thenReturn(opt)

        mockMvc.perform(get("/bank/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(1))
            .andExpect(jsonPath("$.name").value("Bank 1"))
    }

    @Test
    fun `should find bank by code on exception`() {
        val opt : Optional<Bank> = Optional.empty()

        `when`(bankRepository.findById(1)).thenReturn(opt)

        mockMvc.perform(get("/bank/1"))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
            .andExpect(jsonPath("$.message").value("bank code not found!"))
            .andExpect(jsonPath("$.path").value(""))
    }

}
