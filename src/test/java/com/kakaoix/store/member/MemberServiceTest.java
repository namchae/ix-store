package com.kakaoix.store.member;

import com.kakaoix.store.member.domain.MemberDto;
import com.kakaoix.store.member.exception.InvalidPasswordException;
import com.kakaoix.store.member.exception.MemberDuplicateException;
import com.kakaoix.store.member.exception.PasswordFailedOverException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @Before
    public void setUp() {

    }

    @Test
    public void 회원가입_성공() {
        final String email= "namchae@kakaofriends.com";
        MemberDto.SignUp signUp = MemberDto.SignUp.builder().email(email).nickName("namchae").password("1234").build();
        memberService.signUp(signUp);

        Assert.assertTrue(memberService.isExistByEmail(email));
    }

    @Test(expected = MemberDuplicateException.class)
    public void 회원가입_중복가입_실패() {
        final String email = "gabriel870214@gmail.com";
        Assert.assertTrue(memberService.isExistByEmail(email));

        MemberDto.SignUp signUp = MemberDto.SignUp.builder().email(email).nickName("namchaeya").password("1234").build();
        memberService.signUp(signUp);
    }

    @Test
    public void 로그인_성공() {
        final String email = "gabriel870214@gmail.com";
        MemberDto.Login login = MemberDto.Login.builder().email(email).password("namchae12").build();
        memberService.login(login);
    }

    @Test(expected = InvalidPasswordException.class)
    public void 로그인_비밀번호틀림_실패() {
        final String email = "gabriel870214@gmail.com";
        MemberDto.Login login = MemberDto.Login.builder().email(email).password("1234").build();
        memberService.login(login);
    }

    @Test(expected = PasswordFailedOverException.class)
    public void 로그인_비밀번호초과_실패() {
        final String email = "password@kakao.com";
        MemberDto.Login login = MemberDto.Login.builder().email(email).password("1234").build();
        memberService.login(login);
    }

}