package pl.wasik.damian.project.spring.warehouse.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User Entity Unit Test")
class UserEntityUnitTest {

    private static final String TEST_NIP = "91103108331";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PHONE_NUMBER = "123456789";

    @Test
    @DisplayName("Given user entity, when set NIP, then NIP is correct")
    void givenUserEntity_whenSetNip_thenNipIsCorrect() {
        // Given
        UserEntity userEntity = new UserEntity();

        // When
        userEntity.setNip(TEST_NIP);

        // Then
        Assertions.assertEquals(TEST_NIP, userEntity.getNip());
    }

    @Test
    @DisplayName("Given user entity, when set email, then email is correct")
    void givenUserEntity_whenSetEmail_thenEmailIsCorrect() {
        // Given
        UserEntity userEntity = new UserEntity();

        // When
        userEntity.setEmail(TEST_EMAIL);

        // Then
        Assertions.assertEquals(TEST_EMAIL, userEntity.getEmail());
    }

    @Test
    @DisplayName("Given user entity, when set phone number, then phone number is correct")
    void givenUserEntity_whenSetPhoneNumber_thenPhoneNumberIsCorrect() {
        // Given
        UserEntity userEntity = new UserEntity();

        // When
        userEntity.setPhoneNumber(TEST_PHONE_NUMBER);

        // Then
        Assertions.assertEquals(TEST_PHONE_NUMBER, userEntity.getPhoneNumber());
    }
}