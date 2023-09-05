package ru.itis.utils.mappers;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ProductDto;
import ru.itis.dto.ProductDto.ProductDtoBuilder;
import ru.itis.dto.ShopAddressDto;
import ru.itis.dto.ShopAddressDto.ShopAddressDtoBuilder;
import ru.itis.dto.UserDto;
import ru.itis.dto.UserDto.UserDtoBuilder;
import ru.itis.models.OrderEntity;
import ru.itis.models.ProductEntity;
import ru.itis.models.ShopAddressEntity;
import ru.itis.models.UserEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T12:38:55+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (Amazon.com Inc.)"
)
@Component
public class OrdersMapperImpl implements OrdersMapper {

    @Override
    public OrderDto toResponse(OrderEntity order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setAmount( order.getAmount() );
        if ( order.getUser() != null ) {
            orderDto.setUser( userEntityToUserDto( order.getUser() ) );
        }
        if ( order.getShopAddress() != null ) {
            orderDto.setShopAddress( shopAddressEntityToShopAddressDto( order.getShopAddress() ) );
        }
        Set<ProductDto> set = productEntitySetToProductDtoSet( order.getProducts() );
        if ( set != null ) {
            orderDto.setProducts( set );
        }

        return orderDto;
    }

    protected UserDto userEntityToUserDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        if ( userEntity.getFirstName() != null ) {
            userDto.firstName( userEntity.getFirstName() );
        }
        if ( userEntity.getLastName() != null ) {
            userDto.lastName( userEntity.getLastName() );
        }
        if ( userEntity.getEmail() != null ) {
            userDto.email( userEntity.getEmail() );
        }
        if ( userEntity.getPhoneNumber() != null ) {
            userDto.phoneNumber( userEntity.getPhoneNumber() );
        }
        if ( userEntity.getHashPassword() != null ) {
            userDto.hashPassword( userEntity.getHashPassword() );
        }
        if ( userEntity.getDiscount() != null ) {
            userDto.discount( userEntity.getDiscount() );
        }
        if ( userEntity.getAvatar() != null ) {
            userDto.avatar( userEntity.getAvatar() );
        }

        return userDto.build();
    }

    protected ShopAddressDto shopAddressEntityToShopAddressDto(ShopAddressEntity shopAddressEntity) {
        if ( shopAddressEntity == null ) {
            return null;
        }

        ShopAddressDtoBuilder shopAddressDto = ShopAddressDto.builder();

        if ( shopAddressEntity.getAddress() != null ) {
            shopAddressDto.address( shopAddressEntity.getAddress() );
        }

        return shopAddressDto.build();
    }

    protected ProductDto productEntityToProductDto(ProductEntity productEntity) {
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

    protected Set<ProductDto> productEntitySetToProductDtoSet(Set<ProductEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<ProductDto> set1 = new HashSet<ProductDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ProductEntity productEntity : set ) {
            set1.add( productEntityToProductDto( productEntity ) );
        }

        return set1;
    }
}
