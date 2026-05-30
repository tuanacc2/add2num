package com.example.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class Add2NumIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test xem khi vào trang chủ "/" hệ thống có hiển thị đúng file HTML không
     */
    @Test
    void testShowAdd2NumPage_Available() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("add2num"))
                .andExpect(model().attributeExists("form"));
    }

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

    /**
     * Test xem khi điền 2 số và bấm nút gửi (POST /calculate) kết quả trả về có
     * đúng không
     */
    @Test
    void testProcessAddition_SuccessfullyCalculated() throws Exception {
        String jsonRequest = "{\"num1\":\"500\",\"num2\":\"600\"}";

        mockMvc.perform(post("/api/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.num1").value("500"))
                .andExpect(jsonPath("$.num2").value("600"))
                .andExpect(jsonPath("$.result").value("1100"));
    }
}