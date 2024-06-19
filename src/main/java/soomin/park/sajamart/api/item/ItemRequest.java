package soomin.park.sajamart.api.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자 추가
@Builder
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class ItemRequest {

    @NotEmpty
    @Size(min = 1, max = 100, message = "상품명은 100자 보다 작아야 합니다.")
    private String title; // 상품명
    @Min(value = 100, message = "금액은 0보다 큰 값을 입력해야 합니다.")
    private int price; // 금액
    @Min(value = 0, message = "재고는 0 보다 큰 값을 입력해야 합니다.")
    private int availableStock; // 재고
    @NotEmpty(message = "상품 상세 설명은 필수 값입니다.")
    private String detail; // 상품 상세 설명
    @NotNull(message = "상품 상태 값은 필수 값입니다.")
    private ItemStatus itemStatus;

    public Item toEntity() { // 생성자를 사용해 객체 생성
        return Item.builder()
                .title(title)
                .price(price)
                .availableStock(availableStock)
                .detail(detail)
                .itemStatus(itemStatus)
                .build();
    }

}
