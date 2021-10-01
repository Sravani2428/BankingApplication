package com.bank.resource;

import com.bank.dto.TransactionsResponseDto;
import com.bank.exception.TransactionsNotFoundException;
import com.bank.service.TransactionStatementServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TransactionStatementResourceTest {

    @MockBean
    TransactionStatementServiceImpl transactionStatementServiceImpl;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testGetStatementOk() throws Exception {
        TransactionsResponseDto transactionsResponseDto = new TransactionsResponseDto();
        Mockito.when(transactionStatementServiceImpl.getStatement(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(transactionsResponseDto);
        mockMvc.perform(get("/transactions/{customerId}", 1)
                .param("fromDate", "2021-09-14")
                .param("toDate", "2021-09-15")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testTransactionsNotFoundException() throws Exception {
        Mockito.when(transactionStatementServiceImpl.getStatement(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenThrow(TransactionsNotFoundException.class);
        mockMvc.perform(get("/transactions/{customerId}", 1)
                .param("fromDate", "2021-09-14")
                .param("toDate", "2021-09-15")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
