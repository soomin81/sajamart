package soomin.park.sajamart.api.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponse {

    private Long id;
    private String title; // 상품명
    private int price; // 금액
    private int availableStock; // 재고
    private String detail; // 상품 상세 설명
    private ItemStatus itemStatus;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.availableStock = item.getAvailableStock();
        this.detail = item.getDetail();
        this.itemStatus = item.getItemStatus();
    }

}
