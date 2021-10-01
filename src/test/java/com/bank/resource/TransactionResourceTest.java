package com.bank.resource;

import com.bank.dto.TransactionRequestDto;
import com.bank.dto.TransactionResponseDto;
import com.bank.dto.TransactionType;
import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAccountNumberException;
import com.bank.exception.InvalidAmountException;
import com.bank.exception.TransactionFailedException;
import com.bank.service.TransactionServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionResourceTest {

    @MockBean
    TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDepositOk() throws Exception {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setMessage("Amount Deposited Successfully");
        transactionResponseDto.setStatusCode(HttpStatus.OK.value());
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.DEPOSIT))).thenReturn(transactionResponseDto);

        mockMvc.perform(post("/deposit")
                        .content(convertObjectJson(new TransactionRequestDto(1000.00, "11223344")))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());
    }

    @Test
    void testDepositAccountNotFoundException() throws Exception {
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.DEPOSIT))).thenThrow(InvalidAccountNumberException.class);
        mockMvc.perform(post("/deposit")
                .content(convertObjectJson(new TransactionRequestDto(1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testDepositTransactionFailed() throws Exception {
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.DEPOSIT))).thenThrow(TransactionFailedException.class);
        mockMvc.perform(post("/deposit")
                .content(convertObjectJson(new TransactionRequestDto(1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testDepositInvalidAmount() throws Exception {
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.DEPOSIT))).thenThrow(InvalidAmountException.class);
        mockMvc.perform(post("/deposit")
                .content(convertObjectJson(new TransactionRequestDto(-1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testWithDrawOk() throws Exception {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setMessage("Amount Deposited Successfully");
        transactionResponseDto.setStatusCode(HttpStatus.OK.value());
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.WITHDRAW))).thenReturn(transactionResponseDto);

        mockMvc.perform(post("/withDraw")
                .content(convertObjectJson(new TransactionRequestDto(1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testWithDrawAccountNotFoundException() throws Exception {
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.WITHDRAW))).thenThrow(InvalidAccountNumberException.class);
        mockMvc.perform(post("/withDraw")
                .content(convertObjectJson(new TransactionRequestDto(1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testWithDrawTransactionFailed() throws Exception {
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.WITHDRAW))).thenThrow(TransactionFailedException.class);
        mockMvc.perform(post("/withDraw")
                .content(convertObjectJson(new TransactionRequestDto(1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testWithdrawInvalidAmount() throws Exception {
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.WITHDRAW))).thenThrow(InvalidAmountException.class);
        mockMvc.perform(post("/withDraw")
                .content(convertObjectJson(new TransactionRequestDto(-1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testWithdrawInsufficientFund() throws Exception {
        Mockito.when(transactionServiceImpl.depositorWithdrawAmount(Mockito.any(), eq(TransactionType.WITHDRAW))).thenThrow(InsufficientFundsException.class);
        mockMvc.perform(post("/withDraw")
                .content(convertObjectJson(new TransactionRequestDto(-1000.00, "11223344")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotAcceptable());
    }


    public static String convertObjectJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
