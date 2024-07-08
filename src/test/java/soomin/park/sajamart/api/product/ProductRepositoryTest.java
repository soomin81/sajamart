package soomin.park.sajamart.api.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @DisplayName("save: 상품 등록")
    @Test
    void save() {
        // given
        final Product product = Product.builder()
                .name("젤다의 전설 티어스 오브 더 킹덤")
                .price(50000)
                .stockQuantity(9999)
                .description("3인칭 오픈 에어 액션 어드벤처")
                .productStatus(ProductStatus.SELL)
                .build();

        // when
        Product saveProduct = productRepository.save(product);

        // then
        Assertions.assertThat(productRepository.findById(saveProduct.getId())).isPresent();
    }
}