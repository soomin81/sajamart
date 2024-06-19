package soomin.park.sajamart.api.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping(value = "/api/items", produces = MediaTypes.HAL_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<EntityModel<Item>> addItem(@RequestBody @Validated ItemRequest request) {

        Item item = itemService.save(request);

        EntityModel<Item> entityModel = EntityModel.of(item,
                linkTo(ItemController.class).slash(item.getId()).withSelfRel(),
                linkTo(ItemController.class).withRel("item-list")
        );

        var selfLinkBuilder = linkTo(ItemController.class).slash(item.getId());
        URI createdUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createdUri).body(entityModel);
    }

    @GetMapping
    public CollectionModel<EntityModel<Item>> findAllItems(Pageable pageable) {


        List<EntityModel<Item>> items = itemService.findAll(pageable)
                .stream()
                .map(item -> EntityModel.of(item,
                        linkTo(ItemController.class).slash(item.getId()).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(ItemController.class).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Item> findItem(@PathVariable long id) {
        Item item = itemService.findById(id);

        return EntityModel.of(item,
                linkTo(ItemController.class).slash(item.getId()).withSelfRel(),
                linkTo(ItemController.class).slash(item.getId()).withRel("update"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        itemService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id, @RequestBody ItemRequest request) {
        Item updatedItem = itemService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedItem);
    }
}
