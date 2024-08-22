package pl.wasik.damian.project.spring.warehouse.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.wasik.damian.project.spring.warehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.CartDto;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;
import pl.wasik.damian.project.spring.warehouse.web.model.UserDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DisplayName("CartMapper Unit Test")
public class CartMapperUnitTest {

    private static final Long CART_ID = 1L;
    private static final Long USER_ID = 123L;
    private static final Long PRODUCT_ID_1 = 456L;
    private static final Long PRODUCT_ID_2 = 789L;

    @Test
    @DisplayName("given CartEntity when map to CartDto then return CartDto")
    void givenCartEntity_whenMapToDto_thenReturnCartDto() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);

        Set<ProductEntity> productEntities = new HashSet<>();
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(PRODUCT_ID_1);
        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(PRODUCT_ID_2);
        productEntities.add(productEntity1);
        productEntities.add(productEntity2);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        cartEntity.setUserEntity(userEntity);
        cartEntity.setProductEntities(productEntities);

        // When
        CartDto cartDto = cartMapper.toDto(cartEntity);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(cartDto, "CartDto should not be null"),
                () -> Assertions.assertEquals(CART_ID, cartDto.getId(), "ID should match"),
                () -> Assertions.assertNotNull(cartDto.getUser(), "UserDto should not be null"),
                () -> Assertions.assertEquals(USER_ID, cartDto.getUser().getId(), "User ID should match in UserDto"),
                () -> Assertions.assertNotNull(cartDto.getProducts(), "Products should not be null"),
                () -> Assertions.assertEquals(2, cartDto.getProducts().size(), "ProductDto set size should match"),
                () -> Assertions.assertTrue(
                        cartDto.getProducts().stream().anyMatch(product -> PRODUCT_ID_1.equals(product.getId())),
                        "Product ID 1 should be in the ProductDto set"
                ),
                () -> Assertions.assertTrue(
                        cartDto.getProducts().stream().anyMatch(product -> PRODUCT_ID_2.equals(product.getId())),
                        "Product ID 2 should be in the ProductDto set"
                )
        );
    }

    @Test
    @DisplayName("given CartDto when map to CartEntity then return CartEntity")
    void givenCartDto_whenMapToEntity_thenReturnCartEntity() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);

        Set<ProductDto> productDtos = new HashSet<>();
        ProductDto productDto1 = new ProductDto();
        productDto1.setId(PRODUCT_ID_1);
        ProductDto productDto2 = new ProductDto();
        productDto2.setId(PRODUCT_ID_2);
        productDtos.add(productDto1);
        productDtos.add(productDto2);

        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setUser(userDto);
        cartDto.setProducts(productDtos);

        // When
        CartEntity cartEntity = cartMapper.toEntity(cartDto);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(cartEntity, "CartEntity should not be null"),
                () -> Assertions.assertEquals(CART_ID, cartEntity.getId(), "ID should match"),
                () -> Assertions.assertNotNull(cartEntity.getUserEntity(), "UserEntity should not be null"),
                () -> Assertions.assertEquals(USER_ID, cartEntity.getUserEntity().getId(), "User ID should match in UserEntity"),
                () -> Assertions.assertNotNull(cartEntity.getProductEntities(), "Products should not be null"),
                () -> Assertions.assertEquals(2, cartEntity.getProductEntities().size(), "ProductEntity set size should match"),
                () -> Assertions.assertTrue(
                        cartEntity.getProductEntities().stream().anyMatch(product -> PRODUCT_ID_1.equals(product.getId())),
                        "Product ID 1 should be in the ProductEntity set"
                ),
                () -> Assertions.assertTrue(
                        cartEntity.getProductEntities().stream().anyMatch(product -> PRODUCT_ID_2.equals(product.getId())),
                        "Product ID 2 should be in the ProductEntity set"
                )
        );
    }

    @Test
    @DisplayName("given CartEntity with null products when map to CartDto then return CartDto with empty products")
    void givenCartEntityWithNullProducts_whenMapToDto_thenReturnCartDtoWithEmptyProducts() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        cartEntity.setUserEntity(userEntity);
        cartEntity.setProductEntities(null);

        // When
        CartDto cartDto = cartMapper.toDto(cartEntity);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(cartDto, "CartDto should not be null"),
                () -> Assertions.assertEquals(CART_ID, cartDto.getId(), "ID should match"),
                () -> Assertions.assertNotNull(cartDto.getUser(), "UserDto should not be null"),
                () -> Assertions.assertEquals(USER_ID, cartDto.getUser().getId(), "User ID should match in UserDto"),
                () -> Assertions.assertNotNull(cartDto.getProducts(), "Products should not be null"), // Powinno być inicjalizowane pustym zestawem
                () -> Assertions.assertTrue(cartDto.getProducts().isEmpty(), "ProductDto set should be empty")
        );
    }

    @Test
    @DisplayName("given CartDto with null products when map to CartEntity then return CartEntity with empty products")
    void givenCartDtoWithNullProducts_whenMapToEntity_thenReturnCartEntityWithEmptyProducts() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);

        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setUser(userDto);
        cartDto.setProducts(null);

        // When
        CartEntity cartEntity = cartMapper.toEntity(cartDto);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(cartEntity, "CartEntity should not be null"),
                () -> Assertions.assertEquals(CART_ID, cartEntity.getId(), "ID should match"),
                () -> Assertions.assertNotNull(cartEntity.getUserEntity(), "UserEntity should not be null"),
                () -> Assertions.assertEquals(USER_ID, cartEntity.getUserEntity().getId(), "User ID should match in UserEntity"),
                () -> Assertions.assertNotNull(cartEntity.getProductEntities(), "Products should not be null"), // Powinno być inicjalizowane pustym zestawem
                () -> Assertions.assertTrue(cartEntity.getProductEntities().isEmpty(), "ProductEntity set should be empty")
        );
    }

    @Test
    @DisplayName("given list of CartEntities when map to list of CartDtos then return correct list")
    void givenListOfCartEntities_whenMapToListOfCartDtos_thenReturnCorrectList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);

        Set<ProductEntity> productEntities = new HashSet<>();
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(PRODUCT_ID_1);
        productEntities.add(productEntity1);

        CartEntity cartEntity1 = new CartEntity();
        cartEntity1.setId(CART_ID);
        cartEntity1.setUserEntity(userEntity);
        cartEntity1.setProductEntities(productEntities);

        CartEntity cartEntity2 = new CartEntity();
        cartEntity2.setId(CART_ID + 1);
        cartEntity2.setUserEntity(userEntity);
        cartEntity2.setProductEntities(null);

        List<CartEntity> cartEntities = Arrays.asList(cartEntity1, cartEntity2);

        // When
        List<CartDto> cartDtos = cartMapper.toDtoList(cartEntities);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(cartDtos, "CartDtos list should not be null"),
                () -> Assertions.assertEquals(2, cartDtos.size(), "Size of CartDtos list should match"),
                () -> Assertions.assertNotNull(cartDtos.get(0).getProducts(), "Products in first CartDto should not be null"),
                () -> Assertions.assertEquals(1, cartDtos.get(0).getProducts().size(), "ProductDto set size in first CartDto should match"),
                () -> Assertions.assertNotNull(cartDtos.get(1).getProducts(), "Products in second CartDto should not be null"),
                () -> Assertions.assertTrue(cartDtos.get(1).getProducts().isEmpty(), "ProductDto set in second CartDto should be empty")
        );
    }

    @Test
    @DisplayName("given list of CartDtos when map to list of CartEntities then return correct list")
    void givenListOfCartDtos_whenMapToListOfCartEntities_thenReturnCorrectList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);

        Set<ProductDto> productDtos = new HashSet<>();
        ProductDto productDto1 = new ProductDto();
        productDto1.setId(PRODUCT_ID_1);
        productDtos.add(productDto1);

        CartDto cartDto1 = new CartDto();
        cartDto1.setId(CART_ID);
        cartDto1.setUser(userDto);
        cartDto1.setProducts(productDtos);

        CartDto cartDto2 = new CartDto();
        cartDto2.setId(CART_ID + 1);
        cartDto2.setUser(userDto);
        cartDto2.setProducts(null);

        List<CartDto> cartDtos = Arrays.asList(cartDto1, cartDto2);

        // When
        List<CartEntity> cartEntities = cartMapper.toEntityList(cartDtos);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(cartEntities, "CartEntities list should not be null"),
                () -> Assertions.assertEquals(2, cartEntities.size(), "Size of CartEntities list should match"),
                () -> Assertions.assertNotNull(cartEntities.get(0).getProductEntities(), "Products in first CartEntity should not be null"),
                () -> Assertions.assertEquals(1, cartEntities.get(0).getProductEntities().size(), "ProductEntity set size in first CartEntity should match"),
                () -> Assertions.assertNotNull(cartEntities.get(1).getProductEntities(), "Products in second CartEntity should not be null"),
                () -> Assertions.assertTrue(cartEntities.get(1).getProductEntities().isEmpty(), "ProductEntity set in second CartEntity should be empty")
        );
    }
}