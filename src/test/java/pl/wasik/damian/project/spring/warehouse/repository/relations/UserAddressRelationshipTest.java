package pl.wasik.damian.project.spring.warehouse.repository.relations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.UserRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;

@SpringBootTest
@DisplayName("UserEntity and AddressEntity Relationship Test")
public class UserAddressRelationshipTest {

    private static final String STREET = "Test Street";
    private static final String CITY = "Test City";
    private static final String POSTAL_CODE = "00-000";
    private static final String HOUSE_NUMBER = "123";
    private static final String NIP = "1234567890";
    private static final String EMAIL = "test@example.com";
    private static final String PHONE_NUMBER = "123-456-789";

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Given user and address entities, when associated, then relationship is correct")
    public void givenUserAndAddressEntities_whenAssociated_thenRelationshipIsCorrect() {
        // Given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        UserEntity userEntity = new UserEntity();
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);
        
        userEntity.setAddressEntity(addressEntity);
        addressEntity.setUserEntity(userEntity);

        // When
        userRepository.save(userEntity);
        UserEntity foundUserEntity = userRepository.findById(userEntity.getId()).orElse(null);

        // Then
        Assertions.assertAll("Verify relationship between UserEntity and AddressEntity",
                () -> Assertions.assertNotNull(foundUserEntity, "UserEntity should not be null"),
                () -> Assertions.assertNotNull(foundUserEntity.getAddressEntity(), "AddressEntity should not be null"),
                () -> Assertions.assertEquals(STREET, foundUserEntity.getAddressEntity().getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, foundUserEntity.getAddressEntity().getCity(), "City should match")
        );
    }
}