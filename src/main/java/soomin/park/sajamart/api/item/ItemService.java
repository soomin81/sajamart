package soomin.park.sajamart.api.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품 등록 메서드
    public Item save(ItemRequest request) {
        return itemRepository.save(request.toEntity());
    }

    // 전체 상품 조회
    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Item findById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 상품 삭제
    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    // 상품 수정
    @Transactional
    public Item update(long id, ItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        item.update(request);
        return item;
    }
}