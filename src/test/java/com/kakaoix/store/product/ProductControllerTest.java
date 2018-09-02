package com.kakaoix.store.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void 상품리스트_조회() throws Exception {
        mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("[" +
                        "{\"productName\":\"리틀프랜즈 아스크림1\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBAPIRK0001_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"리틀프랜즈 아스크림2\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBRYIRK0001_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"리틀프랜즈 아스크림3\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBJGIRK0001_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"리틀프랜즈 아스크림4\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBMZIRK0001_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"플라잉전신키링 라이언\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBRYFBK0001_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"플라잉전신키링 어퍼치\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBAPFBK0002_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"플라잉전신키링 무지\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBMZFBK0003_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"플라잉전신키링 튜브\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBTBFBK0004_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"플라잉전신키링 네오\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBNOFBK0005_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"플라잉전신키링 프로도\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBFDFBK0006_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}," +
                        "{\"productName\":\"카드키체인인형 네오\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRBPNOCKR0001_AW_00.jpg\",\"productPrice\":12000,\"productStock\":10}," +
                        "{\"productName\":\"카드키체인인형 튜브\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRBPTBCKR0001_AW_00.jpg\",\"productPrice\":12000,\"productStock\":10}" +
                        "]"));
    }

    @Test
    public void 삼품번호_조회() throws Exception {
        mockMvc.perform(get("/products/3").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("{\"productName\":\"리틀프랜즈 아스크림3\",\"productImage\":\"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBJGIRK0001_AW_00.jpg\",\"productPrice\":8000,\"productStock\":10}"));
    }

    @Test
    public void 상품_등록() throws Exception {
        mockMvc.perform(post("/products").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"productName\": \"리틀프랜즈 테스트1\", \"productImage\": \"https://img1.daumcdn.net/thumb/R200x200/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ffriends%2Fprod%2Fproduct%2FFRPBAPIRK0001_AW_00.jpg\", \"productPrice\": 1000, \"productStock\": 10}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void 상품_삭제() throws Exception {
        mockMvc.perform(delete("/products/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}