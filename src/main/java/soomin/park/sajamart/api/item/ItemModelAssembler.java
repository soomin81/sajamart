package soomin.park.sajamart.api.item;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {

    @Override
    public EntityModel<Item> toModel(Item item) {
        return EntityModel.of(item, //
                linkTo(methodOn(ItemController.class).findItem(item.getId())).withSelfRel(),
                linkTo(ItemController.class).withRel("itemList"));
    }
}
