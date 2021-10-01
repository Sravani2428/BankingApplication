package com.bank.resource;

import com.bank.dto.PrioritizeCustomerResponseDto;
import com.bank.exception.PrioritizeCustomersNotFoundException;
import com.bank.service.PrioritizeCustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PrioritizeCustomerResourceTest {

    @MockBean
    PrioritizeCustomerServiceImpl prioritizeCustomerServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPrioritizeCustomersOk() throws Exception {
        PrioritizeCustomerResponseDto prioritizeCustomerResponseDto = new PrioritizeCustomerResponseDto();
        Mockito.when(prioritizeCustomerServiceImpl.findPrioritizeCustomers()).thenReturn(prioritizeCustomerResponseDto);
        mockMvc.perform(get("/prioritizeCustomers")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testPrioritizeCustomersNotFound() throws Exception {
        Mockito.when(prioritizeCustomerServiceImpl.findPrioritizeCustomers()).thenThrow(PrioritizeCustomersNotFoundException.class);
        mockMvc.perform(get("/prioritizeCustomers")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
