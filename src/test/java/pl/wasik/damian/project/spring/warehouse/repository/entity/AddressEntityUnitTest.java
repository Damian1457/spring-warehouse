package pl.wasik.damian.project.spring.warehouse.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Address Entity Unit Test")
class AddressEntityUnitTest {

    private static final String TEST_STREET = "Main Street 123";
    private static final String TEST_CITY = "Anytown";
    private static final String TEST_POSTAL_CODE = "12345";
    private static final String TEST_HOUSE_NUMBER = "1A";

    @Test
    @DisplayName("Given address entity, when set street, then street is correct")
    public void givenAddressEntity_whenSetStreet_thenStreetIsCorrect() {
        // Given
        AddressEntity addressEntity = new AddressEntity();

        // When
        addressEntity.setStreet(TEST_STREET);

        // Then
        Assertions.assertEquals(TEST_STREET, addressEntity.getStreet());
    }

    @Test
    @DisplayName("Given address entity, when set city, then city is correct")
    public void givenAddressEntity_whenSetCity_thenCityIsCorrect() {
        // Given
        AddressEntity addressEntity = new AddressEntity();

        // When
        addressEntity.setCity(TEST_CITY);

        // Then
        Assertions.assertEquals(TEST_CITY, addressEntity.getCity());
    }

    @Test
    @DisplayName("Given address entity, when set postal code, then postal code is correct")
    public void givenAddressEntity_whenSetPostalCode_thenPostalCodeIsCorrect() {
        // Given
        AddressEntity addressEntity = new AddressEntity();

        // When
        addressEntity.setPostalCode(TEST_POSTAL_CODE);

        // Then
        Assertions.assertEquals(TEST_POSTAL_CODE, addressEntity.getPostalCode());
    }

    @Test
    @DisplayName("Given address entity, when set house number, then house number is correct")
    public void givenAddressEntity_whenSetHouseNumber_thenHouseNumberIsCorrect() {
        // Given
        AddressEntity addressEntity = new AddressEntity();

        // When
        addressEntity.setHouseNumber(TEST_HOUSE_NUMBER);

        // Then
        Assertions.assertEquals(TEST_HOUSE_NUMBER, addressEntity.getHouseNumber());
    }

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
}