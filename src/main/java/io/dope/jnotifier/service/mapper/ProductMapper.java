package io.dope.jnotifier.service.mapper;


import io.dope.jnotifier.domain.*;
import io.dope.jnotifier.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {


    @Mapping(target = "uuids", ignore = true)
    @Mapping(target = "removeUuid", ignore = true)
    Product toEntity(ProductDTO productDTO);

    default Product fromId(String id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
