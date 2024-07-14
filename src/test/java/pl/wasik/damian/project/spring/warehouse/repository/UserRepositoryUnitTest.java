package pl.wasik.damian.project.spring.warehouse.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;

@SpringBootTest
@DisplayName("User Repository Unit Test")
class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Given user repository, when add user, then user is saved")
    void givenUserRepository_whenAddUser_thenUserIsSaved() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setNip("91103108331");
        userEntity.setEmail("Test@Email");
        userEntity.setPhoneNumber("730981484");

        // When
        UserEntity savedUser = userRepository.save(userEntity);

        //Then
        Assertions.assertNotNull(savedUser, "The user has not been saved.");
    }
}