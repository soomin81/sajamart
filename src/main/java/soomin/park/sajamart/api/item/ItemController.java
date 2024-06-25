package soomin.park.sajamart.api.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping(value = "/api/items", produces = MediaTypes.HAL_JSON_VALUE)
public class ItemController {

    private final ItemService service;
    private final ItemModelAssembler assembler;

    // 상품 등록
    @PostMapping
    public ResponseEntity<EntityModel<Item>> addItem(@RequestBody @Validated ItemRequest request) {

        var item = service.save(request);
        var entityModel = assembler.toModel(item);
        entityModel.add(Link.of("/docs/index.html#_상품_등록").withRel("profile"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // 특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Item>> findItem(@PathVariable long id) {
        var item = service.findById(id);

        var entityModel  = assembler.toModel(item);
        entityModel.add(Link.of("/docs/index.html").withRel("profile"));

        return ResponseEntity.ok(entityModel);
    }

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Item>>> findAllItems(Pageable pageable,
                                                                      PagedResourcesAssembler<Item> assembler) {
        var page = service.findAll(pageable);
        var pagedResources = assembler.toModel(page);
        pagedResources.add(Link.of("/docs/index.html").withRel("profile"));

        return ResponseEntity.ok(pagedResources);
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Item>> updateItem(@PathVariable long id, @RequestBody ItemRequest request) {
        var item = service.update(id, request);
        var entityModel = assembler.toModel(item);
        entityModel.add(Link.of("/docs/index.html").withRel("profile"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
