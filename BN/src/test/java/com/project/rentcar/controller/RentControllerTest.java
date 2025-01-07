package com.project.rentcar.controller;

import com.project.rentcar.domain.dto.RentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RentControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void t1() throws Exception {
        //given
        RentDto rentDto = new RentDto();
        rentDto.setMemberId("abcd");
        rentDto.setCarname("sonata");
        //when&then
        mvc.perform(post("/rent/RentCar")
                        .contentType("application/json")
                        .content("{\"memberId\": \"abcd\", \"carname\": \"sonata\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void t2() throws Exception {
        // given
        RentDto rentDto = new RentDto();
        rentDto.setRentId(1L);

        // when & then
        mvc.perform(post("/rent/RentDayDelay")
                        .contentType("application/json")
                        .content("{\"rentId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void t3() throws Exception {
        // given
        RentDto rentDto = new RentDto();
        rentDto.setRentId(1L); // rentId를 설정

        // when & then
        mvc.perform(post("/rent/RentReturn")
                        .contentType("application/json")
                        .content("{\"rentId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }


}