package com.kakaoix.store.product;

import com.kakaoix.store.product.domain.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api("상품 API")
@RequestMapping("products")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "상품 리스트", notes = "등록된 모든 상품 목록 가져오기")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto.Response> list() {
        return productService.list();
    }

    @ApiOperation(value = "상품 정보", notes = "요청된 ID 기준의 상품정보 가져오기")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto.Response findById(
            @ApiParam(value = "상품 ID", required = true, example = "1") @PathVariable Long id) {
        return productService.findProductResponseById(id);
    }

    @ApiOperation(value = "상품 등록", notes = "요청된 상품 등록하기")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @ApiParam(value = "상품 등록 정보", required = true) @Valid @RequestBody final ProductDto.Register register) {
        productService.register(register);
    }

//    @ApiOperation(value = "상품 수정", notes = "요청된 상품 수정하기(미구현)")
//    @PatchMapping
//    public void update(@RequestBody final ProductDto.Update update) {
//        productService.update(update);
//    }

    @ApiOperation(value = "상품 삭제", notes = "요청된 ID 기준의 상품정보 삭제")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(
            @ApiParam(value = "상품 ID", required = true, example = "1") @PathVariable Long id) {
        productService.remove(id);
    }
}
