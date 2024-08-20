package pl.wasik.damian.project.spring.warehouse.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@DisplayName("Cart Entity Unit Test")
public class CartEntityUnitTest {

    public static final String TEST_PRODUCT_1 = "Product 1";
    public static final String TEST_PRODUCT_2 = "Product 2";
    public static final long TEST_ID_1 = 1L;
    public static final long TEST_ID_2 = 2L;
    public static final String TEST_EMAIL = "damianwasik.coach@gmail.com";

    @Test
    @DisplayName("Given cart entity, when set products, then check that product is correct")
    void givenCartEntity_whenSetProducts_thenCheckThatProductIsCorrect() {
        // Given
        CartEntity cartEntity = new CartEntity();
        ProductEntity product1 = new ProductEntity();
        product1.setId(TEST_ID_1);
        product1.setName(TEST_PRODUCT_1);

        ProductEntity product2 = new ProductEntity();
        product2.setId(TEST_ID_2);
        product2.setName(TEST_PRODUCT_2);

        Set<ProductEntity> products = new HashSet<>();
        products.add(product1);
        products.add(product2);

        // When
        cartEntity.setProductEntities(products);
        Set<ProductEntity> retrievedProducts = cartEntity.getProductEntities();

        // Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, retrievedProducts.size(), "The number of products in the cart should be 2"),
                () -> Assertions.assertEquals(products, retrievedProducts, "The products set in the cart should be correctly stored")
        );
    }

    @Test
    @DisplayName("Given cart entity, when set user, then check that user is correct")
    void givenCartEntity_whenSetUser_thenCheckThatUserIsCorrect() {
        // Given
        CartEntity cartEntity = new CartEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(TEST_EMAIL);

        // When
        cartEntity.setUserEntity(userEntity);
        userEntity.setCartEntity(cartEntity);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(userEntity, cartEntity.getUserEntity(), "UserEntity should be correctly set in CartEntity"),
                () -> Assertions.assertEquals(cartEntity, userEntity.getCartEntity(), "CartEntity should be correctly set in UserEntity"),
                () -> Assertions.assertEquals(TEST_EMAIL, cartEntity.getUserEntity().getEmail(), "User email should be correctly set in CartEntity")
        );
    }
}
