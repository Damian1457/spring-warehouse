package pl.wasik.damian.project.spring.warehouse.repository.relations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.CartRepository;
import pl.wasik.damian.project.spring.warehouse.repository.UserRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;

@SpringBootTest
@DisplayName("UserEntity and CartEntity Relationship Test")
public class UserCartRelationshipTest {

    private static final String NIP = "1234567890";
    private static final String EMAIL = "test@example.com";
    private static final String PHONE_NUMBER = "123-456-789";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("Given user and cart entities, when associated, then relationship is correct")
    public void givenUserAndCartEntities_whenAssociated_thenRelationshipIsCorrect() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);

        CartEntity cartEntity = new CartEntity();
        
        cartEntity.setUserEntity(userEntity);
        userEntity.setCartEntity(cartEntity);

        // When
        userRepository.save(userEntity);
        cartRepository.save(cartEntity);
        CartEntity foundCartEntity = cartRepository.findById(cartEntity.getId()).orElse(null);

        // Then
        Assertions.assertAll("Verify relationship between UserEntity and CartEntity",
                () -> Assertions.assertNotNull(foundCartEntity, "CartEntity should not be null"),
                () -> Assertions.assertEquals(userEntity.getId(), foundCartEntity.getUserEntity().getId(), "UserEntity in CartEntity should match the saved UserEntity")
        );
    }
}
