package com.kakaoix.store.product;

import com.kakaoix.store.product.domain.Product;
import com.kakaoix.store.product.domain.ProductDto;
import com.kakaoix.store.product.exception.ProductDuplicateException;
import com.kakaoix.store.product.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 등록
    @Transactional
    public void register(ProductDto.Register register) {
        Optional<Product> productOptional = productRepository.findByProductName(register.getProductName());
        if (productOptional.isPresent()) {
            throw new ProductDuplicateException(register.getProductName());
        }
        productRepository.save(register.toEntity());
    }

    // 상품정보 업데이트
    @Transactional
    public void update(ProductDto.Update update) {
//        productRepository.save()
    }

    // 상품 제거.
    @Transactional
    public void remove(Long id) {
        findById(id);
        productRepository.deleteById(id);
    }

    // 전 상품 리스트.
    public List<ProductDto.Response> list() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto.Response::new)
                .collect(Collectors.toList());
    }

    // id 상품정보 조회
    public ProductDto.Response findProductResponseById(Long id) {
        return new ProductDto.Response(findById(id));
    }

    // id 상품Entity 조회
    public Product getProductById(Long id) {
        return findById(id);
    }

    private Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ProductNotFoundException(id);
        }
    }


}
