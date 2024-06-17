package soomin.park.sajamart.api.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @DisplayName("상품 등록")
    @Test
    void save() {
        // given
        final Item item = Item.builder()
                .title("젤다의 전설 티어스 오브 더 킹덤")
                .price(50000)
                .availableStock(9999)
                .detail("3인칭 오픈 에어 액션 어드벤처")
                .itemStatus(ItemStatus.SELL)
                .build();

        // when
        Item saveItem = itemRepository.save(item);

        // then
        Assertions.assertThat(itemRepository.findById(saveItem.getId())).isPresent();
    }
}