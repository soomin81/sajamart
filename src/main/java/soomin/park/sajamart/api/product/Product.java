package soomin.park.sajamart.api.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Product {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    private Long id;

    @Column(nullable = false) // name 이라는 not null 컬럼과 매핑
    private String name; // 상품 이름

    @Column(nullable = false)
    private int price; // 상품 가격

    @Column(nullable = false)
    private int stockQuantity; // 상품 재고 수량

    @Column(nullable = false)
    private String description; // 상품 설명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus; // 상품 상태

    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    private LocalDateTime createAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    private LocalDateTime updateAt;

    @Builder // 빌더 패턴으로 객체 생성
    public Product(Long id, String name, int price, int stockQuantity, String description, ProductStatus productStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.productStatus = productStatus;
    }

    public void update(ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.price = productRequest.getPrice();
        this.stockQuantity = productRequest.getStockQuantity();
        this.description = productRequest.getDescription();
        this.productStatus = productRequest.getProductStatus();
    }
}
