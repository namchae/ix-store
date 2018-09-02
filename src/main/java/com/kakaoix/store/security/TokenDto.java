package com.kakaoix.store.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class TokenDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "TokenDto.Response", description = "토큰 정보")
    public static class Response {

        @ApiModelProperty(notes = "회원ID", required = true, example = "1")
        private Long memberId;

        @ApiModelProperty(notes = "이메일", required = true, example = "namchaeya@gmail.com")
        private String email;

        @ApiModelProperty(notes = "토큰", required = true, example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYWJyaWVsODcwMjE0QGdtYWlsLmNvbSIsIm1lbWJlcklkIjoxfQ.-naYGZFBk2jfy3Eg5h5LBGf8VejWsDkZraMb31mwzTezBsr_mB7_Oque17cMIxPkLSrOqg0kdVg7Fepcggcf4g")
        private String value;

        @Builder
        public Response(Long memberId, String email, String value) {
            this.memberId = memberId;
            this.email = email;
            this.value = value;
        }

    }
}
