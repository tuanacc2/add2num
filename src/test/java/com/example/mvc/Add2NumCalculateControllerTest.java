package com.example.mvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class Add2NumCalculateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test quy trình tính toán qua API /calculate thành công
     * Sử dụng @RequestParam (Form URL Encoded) và mong đợi trả về JSON chứa kết quả
     * cùng các bước log
     */
    @Test
    void testCalculate_Success() throws Exception {
        mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("num1", "100")
                .param("num2", "900"))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.result", is("1000")))

                .andExpect(jsonPath("$.steps", is(instanceOf(java.util.List.class))))
                .andExpect(jsonPath("$.steps", hasSize(greaterThanOrEqualTo(0))));
    }
}