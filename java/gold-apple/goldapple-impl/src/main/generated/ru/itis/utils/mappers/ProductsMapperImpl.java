package ru.itis.utils.mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.itis.dto.ProductDto;
import ru.itis.dto.ProductDto.ProductDtoBuilder;
import ru.itis.models.ProductEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T12:38:56+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (Amazon.com Inc.)"
)
@Component
public class ProductsMapperImpl implements ProductsMapper {

    @Override
    public ProductDto toResponse(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductDtoBuilder productDto = ProductDto.builder();

        if ( productEntity.getName() != null ) {
            productDto.name( productEntity.getName() );
        }
        if ( productEntity.getCategory() != null ) {
            productDto.category( productEntity.getCategory() );
        }
        if ( productEntity.getMaker() != null ) {
            productDto.maker( productEntity.getMaker() );
        }
        if ( productEntity.getVendorCode() != null ) {
            productDto.vendorCode( productEntity.getVendorCode() );
        }
        if ( productEntity.getPrice() != null ) {
            productDto.price( productEntity.getPrice() );
        }
        if ( productEntity.getDescription() != null ) {
            productDto.description( productEntity.getDescription() );
        }
        if ( productEntity.getDiscount() != null ) {
            productDto.discount( productEntity.getDiscount() );
        }
        if ( productEntity.getPicture() != null ) {
            productDto.picture( productEntity.getPicture() );
        }

        return productDto.build();
    }

    @Override
    public Set<ProductDto> toResponse(Set<ProductEntity> productEntitySet) {
        if ( productEntitySet == null ) {
            return null;
        }

        Set<ProductDto> set = new HashSet<ProductDto>( Math.max( (int) ( productEntitySet.size() / .75f ) + 1, 16 ) );
        for ( ProductEntity productEntity : productEntitySet ) {
            set.add( toResponse( productEntity ) );
        }

        return set;
    }

    @Override
    public List<ProductDto> toResponse(List<ProductEntity> productEntityList) {
        if ( productEntityList == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( productEntityList.size() );
        for ( ProductEntity productEntity : productEntityList ) {
            list.add( toResponse( productEntity ) );
        }

        return list;
    }
}
