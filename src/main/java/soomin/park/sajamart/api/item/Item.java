package soomin.park.sajamart.api.item;

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
public class Item {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    private Long id;

    @Column(nullable = false) // title 이라는 not null 컬럼과 매핑
    private String title; // 상품명

    @Column(nullable = false)
    private int price; // 금액

    @Column(nullable = false)
    private int availableStock; // 재고

    @Column(nullable = false)
    private String detail; // 상품 상세 설명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    private LocalDateTime createAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    private LocalDateTime updateAt;

    @Builder // 빌더 패턴으로 객체 생성
    public Item(Long id, String title, int price, int availableStock, String detail, ItemStatus itemStatus) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.availableStock = availableStock;
        this.detail = detail;
        this.itemStatus = itemStatus;
    }

    public void update(ItemRequest itemUpdateRequest) {
        this.title = itemUpdateRequest.getTitle();
        this.price = itemUpdateRequest.getPrice();
        this.availableStock = itemUpdateRequest.getAvailableStock();
        this.detail = itemUpdateRequest.getDetail();
        this.itemStatus = itemUpdateRequest.getItemStatus();
    }
}
