package pl.wasik.damian.project.spring.warehouse.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;

@SpringBootTest
@DisplayName("User Repository Unit Test")
public class UserRepositoryUnitTest {

    public static final String TEST_NIP = "91103108331";
    public static final String TEST_EMAIL = "Test@Email";
    public static final String TEST_NUMBER = "7309814848";
    
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Given user repository, when add user, then user is saved")
    void givenUserRepository_whenAddUser_thenUserIsSaved() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setNip(TEST_NIP);
        userEntity.setEmail(TEST_EMAIL);
        userEntity.setPhoneNumber(TEST_NUMBER);

        // When
        UserEntity savedUser = userRepository.save(userEntity);

        //Then
        Assertions.assertNotNull(savedUser, "The user has not been saved");
    }
}