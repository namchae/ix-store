package com.kakaoix.store.member;

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

import static com.kakaoix.store.interceptor.JwtInterceptor.HEADER_TOKEN_KEY;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void 회원가입_성공() throws Exception {
        mockMvc.perform(post("/members/signUp").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"namchae@gmail.com\", \"nickName\": \"namchae\", \"password\": \"namchae12\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HEADER_TOKEN_KEY));
    }

    @Test
    public void 회원가입_아이디베이스_실패() throws Exception {
        mockMvc.perform(post("/members/signUp").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"namchae\", \"nickName\": \"namchae\", \"password\": \"namchae12\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"code\":\"REQUEST_PARAMETER_NOT_VALID\",\"message\":\"Request parameter is not valid.\",\"fieldErrors\":[{\"field\":\"email\",\"value\":\"namchae\",\"reason\":\"must be a well-formed email address\"}]}"));
    }

    @Test
    public void 회원가입_짧은비밀번호_실패() throws Exception {
        mockMvc.perform(post("/members/signUp").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"namchae@gmail.com\", \"nickName\": \"namchae\", \"password\": \"nam2\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_중복가입_실패() throws Exception {
        mockMvc.perform(post("/members/signUp").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"gabriel870214@gmail.com\", \"nickName\": \"namchae\", \"password\": \"namchae12\"}"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void 로그인_성공() throws Exception {
        mockMvc.perform(post("/members/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"gabriel870214@gmail.com\", \"password\": \"namchae12\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HEADER_TOKEN_KEY));
    }

    @Test
    public void 로그인_찾을수_없는_회원_실패() throws Exception {
        mockMvc.perform(post("/members/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"gabriel870@gmail.com\", \"password\": \"namchae\"}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인_비밀번호틀림_실패() throws Exception {
        mockMvc.perform(post("/members/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"gabriel870214@gmail.com\", \"password\": \"namchae\"}"))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}