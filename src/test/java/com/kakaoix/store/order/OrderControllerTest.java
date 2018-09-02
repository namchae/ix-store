package com.kakaoix.store.order;


import com.kakaoix.store.member.MemberService;
import com.kakaoix.store.member.domain.Member;
import com.kakaoix.store.security.JwtGenerator;
import com.kakaoix.store.security.TokenDto;
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

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtGenerator jwtGenerator;

    private String authToken;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        //  토큰 넣어놓기.
        Optional<Member> member = memberService.findMemberById(1L);
        TokenDto.Response tokenDto = jwtGenerator.generate(member.get());
        this.authToken = tokenDto.getValue();
    }

    @Test
    public void 주문요청() throws Exception {
        mockMvc.perform(post("/orders").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("token", authToken)
                .content("{\"buyer\": \"남채희\", \"orderItems\": [{\"orderQuantity\": 3, \"productId\": 1}], \"address\": {\"address1\" : \"서울특별시 강남구 테헤란로 521\", \"address2\": \"27층 (삼성동, 파르나스타워)\", \"zip\": 12345}}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void 주문요청_재고초과() throws Exception {
        mockMvc.perform(post("/orders").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("token", authToken)
                .content("{\"buyer\": \"남채희\", \"orderItems\": [{\"orderQuantity\": 100, \"productId\": 1}], \"address\": {\"address1\" : \"서울특별시 강남구 테헤란로 521\", \"address2\": \"27층 (삼성동, 파르나스타워)\", \"zip\": 12345}}"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"code\":\"PRODUCT_STOCK_OVERFLOW\",\"message\":\"요청한 상품번호[1]의 재고수량이 부족합니다.\",\"fieldErrors\":null}"));
    }

    @Test
    public void 주문번호_조회요청() throws Exception {
        mockMvc.perform(get("/orders/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("{\"id\":1,\"memberId\":1,\"totalPrice\":48000,\"orderStatus\":\"주문취소\",\"orderDate\":\"2018-09-01 10:00:00\",\"cancelDate\":\"2018-09-01 11:00:00\",\"buyer\":\"남채희\",\"address\":{\"address1\":\"서울특별시 강남구 테헤란로 521\",\"address2\":\"27층 (삼성동, 파르나스타워)\",\"zip\":\"12345\"},\"orderLineResponses\":[{\"productName\":\"리틀프랜즈 아스크림1\",\"productPrice\":8000,\"quantity\":3},{\"productName\":\"플라잉전신키링 무지\",\"productPrice\":8000,\"quantity\":3}]}"));
    }

    @Test
    public void 잘못된_주문번호_조회요청() throws Exception {
        mockMvc.perform(get("/orders/10").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("{\"code\":\"ORDER_NOT_FOUND\",\"message\":\"요청한 주문번호[10]를 찾을 수 없습니다.\",\"fieldErrors\":null}"));
    }

    @Test
    public void 회원번호기준_주문조회() throws Exception {
        mockMvc.perform(get("/orders/member").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .header("token", authToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("[{\"id\":1,\"memberId\":1,\"totalPrice\":48000,\"orderStatus\":\"주문취소\",\"orderDate\":\"2018-09-01 10:00:00\",\"cancelDate\":\"2018-09-01 11:00:00\",\"buyer\":\"남채희\",\"address\":{\"address1\":\"서울특별시 강남구 테헤란로 521\",\"address2\":\"27층 (삼성동, 파르나스타워)\",\"zip\":\"12345\"},\"orderLineResponses\":[{\"productName\":\"리틀프랜즈 아스크림1\",\"productPrice\":8000,\"quantity\":3},{\"productName\":\"플라잉전신키링 무지\",\"productPrice\":8000,\"quantity\":3}]},{\"id\":3,\"memberId\":1,\"totalPrice\":24000,\"orderStatus\":\"주문완료\",\"orderDate\":\"2018-09-01 10:00:00\",\"cancelDate\":null,\"buyer\":\"남채희\",\"address\":{\"address1\":\"서울특별시 강남구 테헤란로 427\",\"address2\":\"야놀자\",\"zip\":\"12345\"},\"orderLineResponses\":[{\"productName\":\"리틀프랜즈 아스크림2\",\"productPrice\":8000,\"quantity\":3}]},{\"id\":4,\"memberId\":1,\"totalPrice\":24000,\"orderStatus\":\"주문완료\",\"orderDate\":\"2018-09-01 10:00:00\",\"cancelDate\":null,\"buyer\":\"남채희\",\"address\":{\"address1\":\"서울특별시 강남구 테헤란로 521\",\"address2\":\"27층 (삼성동, 파르나스타워)\",\"zip\":\"12345\"},\"orderLineResponses\":[{\"productName\":\"플라잉전신키링 무지\",\"productPrice\":8000,\"quantity\":3}]}]"));
    }

    @Test
    public void 취소요청() throws Exception {
        mockMvc.perform(patch("/orders/2").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void 취소된_주문건_취소요청() throws Exception {
        mockMvc.perform(patch("/orders/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

}