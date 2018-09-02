package com.kakaoix.store.security;

import com.kakaoix.store.member.domain.Member;
import com.kakaoix.store.security.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.ehcache.search.expression.IsNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class JwtGenerator {

    @Autowired
    private EhCacheCacheManager ehCacheManager;

    public static final String SAMPLE_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYWJyaWVsODcwMjE0QGdtYWlsLmNvbSIsIm1lbWJlcklkIjoxfQ.-naYGZFBk2jfy3Eg5h5LBGf8VejWsDkZraMb31mwzTezBsr_mB7_Oque17cMIxPkLSrOqg0kdVg7Fepcggcf4g";

    private final String cacheName= "tokenCache";

    private final String secretKey = "kakaoix";

    // 토큰 생성.
    @Cacheable(value = cacheName, key = "#member.getEmail()")
    public TokenDto.Response generate(Member member) {
        String token = generateToken(member);
        return TokenDto.Response.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .value(token)
                .build();
    }

    // memberService에서 테스트로 가입된 회원에 대한 Token을 생성한다.
    public void putToken(Member member) {
        Cache tokenCache = ehCacheManager.getCache(cacheName);
        tokenCache.put(member.getEmail(), generate(member));
    }

    // token String 생성.
    private String generateToken(Member member) {
        Claims claims = Jwts.claims()
                .setSubject(member.getEmail());
        claims.put("memberId", member.getId());

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    // 로그아웃시 캐시 제거.
    @CacheEvict(cacheNames = {cacheName}, key = "#email")
    public void clearCache(String email) {

    }

    // 토큰 검증.
    public TokenDto.Response validate(String token) {
        Cache tokenCache = ehCacheManager.getCache(cacheName);
        Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        TokenDto.Response tokenDto = tokenCache.get(body.getSubject(), TokenDto.Response.class);

        if (!isNull(tokenDto)) {
            return tokenDto;
        } else {
            throw new UnauthorizedException();
        }
    }

}
