package com.kakaoix.store.order.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @NotEmpty
    @ApiModelProperty(notes = "주소1", required = true, example = "서울특별시 강남구 테헤란로 521")
    @Column(name = "address1", nullable = false)
    private String address1;

    @NotEmpty
    @ApiModelProperty(notes = "주소2", required = true, example = "27층 (삼성동, 파르나스타워)")
    @Column(name = "address2", nullable = false)
    private String address2;

    @NotEmpty
    @ApiModelProperty(notes = "우편번호", required = true, example = "12345")
    @Column(name = "zip", nullable = false)
    private String zip;

    @Builder
    public Address(String address1, String address2, String zip) {
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
    }
}
