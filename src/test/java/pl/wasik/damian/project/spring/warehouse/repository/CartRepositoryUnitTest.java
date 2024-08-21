package pl.wasik.damian.project.spring.warehouse.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;

@SpringBootTest
@DisplayName("Cart Repository Unit Test")
public class CartRepositoryUnitTest {

    public static final String TEST_NIP = "91103108331";
    public static final String TEST_EMAIL = "Test@Email";
    public static final String TEST_NUMBER = "7309814848";

    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("Given cart repository, when create cart, then cart is created")
    void givenCartRepository() {
        // Given
        CartEntity cartEntity = new CartEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setNip(TEST_NIP);
        userEntity.setEmail(TEST_EMAIL);
        userEntity.setPhoneNumber(TEST_NUMBER);
        userEntity.setCartEntity(cartEntity);

        cartEntity.setUserEntity(userEntity);

        // When
        CartEntity savedCart = cartRepository.save(cartEntity);

        //Then
        Assertions.assertNotNull(savedCart, "The cart has not been saved");
    }
}
