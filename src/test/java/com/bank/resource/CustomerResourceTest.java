package com.bank.resource;

import com.bank.dto.CustomerResponseDto;
import com.bank.exception.CustomerNotFoundException;
import com.bank.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerResourceTest {

    @MockBean
    CustomerServiceImpl customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCustomerDetailsOk() throws Exception {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        Mockito.when(customerService.getCustomerDetails(anyInt())).thenReturn(customerResponseDto);
        mockMvc.perform(get("/getCustomerDetails")
                .param("customerId", "1")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testCustomerNotFound() throws Exception {
        Mockito.when(customerService.getCustomerDetails(anyInt())).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(get("/getCustomerDetails")
                .param("customerId", "1")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

}
