package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.Mocks
import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.repository.AccountRepository
import br.com.usedrip.banktransfers.repository.TransferRepository
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var repository: AccountRepository

    @MockBean
    private lateinit var tranferRepository: TransferRepository

    private val mocks: Mocks = Mocks()

    @Test
    fun `should Transfer interbancárias `() {
        val transferForm = TransferForm(BigDecimal.valueOf(3000.0), 1, 2)
        `when`(tranferRepository.save(any())).thenReturn(mocks.getTransf(1))
        `when`(tranferRepository.count()).thenReturn(2)
        `when`(repository.findById(1)).thenReturn(mocks.getOptAccount())
        `when`(repository.findById(2)).thenReturn(mocks.getOptAccount2())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(transferForm))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.comission").value(BigDecimal.valueOf(5.0)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.account").value("2 - Account 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operation").value("payment"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(BigDecimal.valueOf(4500.0)))
    }

    @Test
    fun `should Transfer intrabancárias `() {
        val transferForm = TransferForm(BigDecimal.valueOf(3000.0), 1, 2)
        `when`(tranferRepository.save(any())).thenReturn(mocks.getTransfIntra(1))
        `when`(tranferRepository.count()).thenReturn(2)
        `when`(repository.findById(1)).thenReturn(mocks.getOptAccount())
        `when`(repository.findById(2)).thenReturn(mocks.getOptAccount())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(transferForm))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.comission").value(BigDecimal.ZERO))
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.account").value("2 - Account 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operation").value("payment"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(BigDecimal.valueOf(4500.0)))
    }

    @Test
    fun `should Transfer interbancárias Superior ao limite `() {
        val transferForm = TransferForm(BigDecimal.valueOf(5001.0), 1, 2)
        `when`(tranferRepository.save(any())).thenReturn(mocks.getTransf(1))
        `when`(tranferRepository.count()).thenReturn(2)
        `when`(repository.findById(1)).thenReturn(mocks.getOptAccount())
        `when`(repository.findById(2)).thenReturn(mocks.getOptAccount2())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(transferForm))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("BAD_REQUEST"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.message")
                    .value("Interbank transfers, between accounts at different banks have a limit of R\$ 5000.00 per transfer")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(""))
    }


    @Test
    fun `should Transfer interbancárias accountTo not exist  `() {
        val transferForm = TransferForm(BigDecimal.valueOf(5001.0), 1, 2)
        `when`(tranferRepository.save(any())).thenReturn(mocks.getTransf(1))
        `when`(tranferRepository.count()).thenReturn(2)
        `when`(repository.findById(1)).thenReturn(Optional.empty())
        `when`(repository.findById(2)).thenReturn(mocks.getOptAccount2())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(transferForm))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Destination account code not found!"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(""))
    }



    @Test
    fun `should Transfer interbancárias accountFor not exist  `() {
        val transferForm = TransferForm(BigDecimal.valueOf(5001.0), 1, 2)
        `when`(tranferRepository.save(any())).thenReturn(mocks.getTransf(1))
        `when`(tranferRepository.count()).thenReturn(2)
        `when`(repository.findById(1)).thenReturn(mocks.getOptAccount2())
        `when`(repository.findById(2)).thenReturn(Optional.empty())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(transferForm)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Source account code not found!"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(""))
    }

}

