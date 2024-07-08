package soomin.park.sajamart.api.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soomin.park.sajamart.config.error.exception.ItemNotFoundException;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록 메서드
    public Product save(ProductRequest request) {
        return productRepository.save(request.toEntity());
    }

    // 전체 상품 조회
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // 특정 상품 조회
    public Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    // 상품 삭제
    public void delete(long id) {
        var item = productRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        productRepository.delete(item);
    }

    // 상품 수정
    @Transactional
    public Product update(long id, ProductRequest request) {
        var product = productRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        product.update(request);
        return product;
    }
}
