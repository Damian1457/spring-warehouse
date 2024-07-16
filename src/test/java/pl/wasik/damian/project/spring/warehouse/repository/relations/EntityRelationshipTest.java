package pl.wasik.damian.project.spring.warehouse.repository.relations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.AddressRepository;
import pl.wasik.damian.project.spring.warehouse.repository.UserRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;

@SpringBootTest
@DisplayName("Entity Relationship Test")
public class EntityRelationshipTest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_NIP = "1234567890";
    private static final String TEST_PHONE_NUMBER = "123-456-789";
    private static final String TEST_STREET = "Test Street";
    private static final String TEST_CITY = "Test City";
    private static final String TEST_POSTAL_CODE = "00-000";
    private static final String TEST_HOUSE_NUMBER = "123";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Given address entity, when set user entity, then user entity is correct")
    public void givenAddressEntity_whenSetUserEntity_thenUserEntityIsCorrect() {
        // Given
        AddressEntity addressEntity = new AddressEntity();
        UserEntity userEntity = new UserEntity();

        // When
        addressEntity.setUserEntity(userEntity);

        // Then
        Assertions.assertEquals(userEntity, addressEntity.getUserEntity());
    }

    @Test
    @DisplayName("Given user and address entities, when saved, then relationship is correct")
    public void givenUserAndAddressEntities_whenSaved_thenRelationshipIsCorrect() {
        // Given
        UserEntity user = new UserEntity();
        user.setEmail(TEST_EMAIL);
        user.setNip(TEST_NIP);
        user.setPhoneNumber(TEST_PHONE_NUMBER);

        AddressEntity address = new AddressEntity();
        address.setStreet(TEST_STREET);
        address.setCity(TEST_CITY);
        address.setPostalCode(TEST_POSTAL_CODE);
        address.setHouseNumber(TEST_HOUSE_NUMBER);

        // When
        user.setAddressEntity(address);
        address.setUserEntity(user);
        userRepository.save(user);

        // Then
        UserEntity foundUser = userRepository.findById(user.getId()).orElse(null);
        AddressEntity foundAddress = addressRepository.findById(address.getId()).orElse(null);

        Assertions.assertAll("Verify relationship between UserEntity and AddressEntity",
                () -> Assertions.assertNotNull(foundUser, "Found user should not be null"),
                () -> Assertions.assertNotNull(foundUser.getAddressEntity(), "Found user's address entity should not be null"),
                () -> Assertions.assertEquals(TEST_STREET, foundUser.getAddressEntity().getStreet(), "Street should match"),
                () -> Assertions.assertEquals(TEST_CITY, foundUser.getAddressEntity().getCity(), "City should match"),
                () -> Assertions.assertEquals(TEST_POSTAL_CODE, foundUser.getAddressEntity().getPostalCode(), "Postal code should match"),
                () -> Assertions.assertEquals(TEST_HOUSE_NUMBER, foundUser.getAddressEntity().getHouseNumber(), "House number should match"),
                () -> Assertions.assertNotNull(foundAddress.getUserEntity(), "Found address's user entity should not be null"),
                () -> Assertions.assertEquals(TEST_EMAIL, foundAddress.getUserEntity().getEmail(), "Email should match"),
                () -> Assertions.assertEquals(TEST_NIP, foundAddress.getUserEntity().getNip(), "NIP should match"),
                () -> Assertions.assertEquals(TEST_PHONE_NUMBER, foundAddress.getUserEntity().getPhoneNumber(), "Phone number should match")
        );
    }
}