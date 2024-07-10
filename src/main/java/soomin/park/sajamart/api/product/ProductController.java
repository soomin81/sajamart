package soomin.park.sajamart.api.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping(value = "/api/product", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService service;
    private final ProductModelAssembler assembler;

    // 상품 등록
    @PostMapping
    public ResponseEntity<EntityModel<Product>> addProduct(@RequestBody @Validated ProductRequest request, @AuthenticationPrincipal Jwt jwt) {

      log.info(jwt.getClaim("user_name"));

        var product = service.save(request);
        var entityModel = assembler.toModel(product);
        entityModel.add(Link.of("/docs/index.html#_상품_등록").withRel("profile"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // 특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> findProduct(@PathVariable long id) {
        var product = service.findById(id);

        var entityModel  = assembler.toModel(product);
        entityModel.add(Link.of("/docs/index.html").withRel("profile"));

        return ResponseEntity.ok(entityModel);
    }

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Product>>> findAllProducts(Pageable pageable,
                                                                         PagedResourcesAssembler<Product> assembler) {
        var page = service.findAll(pageable);
        var pagedResources = assembler.toModel(page);
        pagedResources.add(Link.of("/docs/index.html").withRel("profile"));

        return ResponseEntity.ok(pagedResources);
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> updateProduct(@PathVariable long id, @RequestBody ProductRequest request) {
        var product = service.update(id, request);
        var entityModel = assembler.toModel(product);
        entityModel.add(Link.of("/docs/index.html").withRel("profile"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}