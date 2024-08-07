package pl.wasik.damian.project.spring.warehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wasik.damian.project.spring.warehouse.mapper.UserMapper;
import pl.wasik.damian.project.spring.warehouse.repository.UserRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;
import pl.wasik.damian.project.spring.warehouse.web.model.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Test")
class UserServiceUnitTest {

    private static final Long USER_ID = 1L;
    private static final String NIP = "1234567890";
    private static final String EMAIL = "user@example.com";
    private static final String PHONE_NUMBER = "123-456-789";
    private static final Long ADDRESS_ID = 1L;
    private static final String STREET = "Main St";
    private static final String CITY = "Springfield";
    private static final String POSTAL_CODE = "12345";
    private static final String HOUSE_NUMBER = "1A";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Given UserId When GetUserById Then Return UserDto")
    void givenUserId_whenGetUserById_thenReturnUserDto() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);
        userEntity.setAddressEntity(addressEntity);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setNip(NIP);
        userDto.setEmail(EMAIL);
        userDto.setPhoneNumber(PHONE_NUMBER);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);
        userDto.setAddress(addressDto);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        // When
        UserDto foundUser = userService.read(USER_ID);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundUser, "Expected user to be present"),
                () -> Assertions.assertEquals(userDto, foundUser, "Expected userDto to match the found userDto"),
                () -> Assertions.assertEquals(addressDto, foundUser.getAddress(), "Expected addressDto to match the found addressDto")
        );
    }

    @Test
    @DisplayName("Given UserDto When CreateUser Then Return Created UserDto")
    void givenUserDto_whenCreateUser_thenReturnCreatedUserDto() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setNip(NIP);
        userDto.setEmail(EMAIL);
        userDto.setPhoneNumber(PHONE_NUMBER);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);
        userDto.setAddress(addressDto);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);
        userEntity.setAddressEntity(addressEntity);

        when(userMapper.toEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userDto);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // When
        UserDto createdUser = userService.create(userDto);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdUser, "Expected created user to be not null"),
                () -> Assertions.assertEquals(USER_ID, createdUser.getId()),
                () -> Assertions.assertEquals(NIP, createdUser.getNip()),
                () -> Assertions.assertEquals(EMAIL, createdUser.getEmail()),
                () -> Assertions.assertEquals(PHONE_NUMBER, createdUser.getPhoneNumber()),
                () -> Assertions.assertNotNull(createdUser.getAddress(), "Expected created address to be not null"),
                () -> Assertions.assertEquals(ADDRESS_ID, createdUser.getAddress().getId(), "Expected address ID to match"),
                () -> Assertions.assertEquals(STREET, createdUser.getAddress().getStreet(), "Expected street to match"),
                () -> Assertions.assertEquals(CITY, createdUser.getAddress().getCity(), "Expected city to match"),
                () -> Assertions.assertEquals(POSTAL_CODE, createdUser.getAddress().getPostalCode(), "Expected postal code to match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, createdUser.getAddress().getHouseNumber(), "Expected house number to match")
        );
    }

    @Test
    @DisplayName("Given List of UserEntities When GetAllUsers Then Return List of UserDtos")
    void givenUserEntitiesList_whenGetAllUsers_thenReturnUserDtoList() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);
        userEntity.setAddressEntity(addressEntity);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setNip(NIP);
        userDto.setEmail(EMAIL);
        userDto.setPhoneNumber(PHONE_NUMBER);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);
        userDto.setAddress(addressDto);

        List<UserEntity> userEntities = Collections.singletonList(userEntity);
        List<UserDto> userDtos = Collections.singletonList(userDto);

        when(userRepository.findAll()).thenReturn(userEntities);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        // When
        List<UserDto> foundUsers = userService.getAll();

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundUsers, "Expected found users to be not null"),
                () -> Assertions.assertEquals(userDtos.size(), foundUsers.size(), "Expected list size to match"),
                () -> Assertions.assertEquals(userDtos.get(0), foundUsers.get(0), "Expected first user to match")
        );
    }

    @Test
    @DisplayName("Given UserId When DeleteUserById Then Verify Deletion")
    void givenUserId_whenDeleteUserById_thenVerifyDeletion() {
        // Given
        Long userIdToDelete = USER_ID;

        // When
        userService.delete(userIdToDelete);

        // Then
        verify(userRepository).deleteById(userIdToDelete);
    }

    @Test
    @DisplayName("Given UserId and UserDto When UpdateUser Then Return Updated UserDto")
    void givenUserId_whenUpdateUser_thenReturnUpdatedUserDto() {
        // Given
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setId(USER_ID);
        existingUserEntity.setNip(NIP);
        existingUserEntity.setEmail(EMAIL);
        existingUserEntity.setPhoneNumber(PHONE_NUMBER);

        AddressEntity existingAddressEntity = new AddressEntity();
        existingAddressEntity.setId(ADDRESS_ID);
        existingAddressEntity.setStreet(STREET);
        existingAddressEntity.setCity(CITY);
        existingAddressEntity.setPostalCode(POSTAL_CODE);
        existingAddressEntity.setHouseNumber(HOUSE_NUMBER);
        existingAddressEntity.setUserEntity(existingUserEntity);
        existingUserEntity.setAddressEntity(existingAddressEntity);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setNip(NIP);
        userDto.setEmail(EMAIL);
        userDto.setPhoneNumber(PHONE_NUMBER);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);
        userDto.setAddress(addressDto);

        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(USER_ID);
        updatedUserEntity.setNip(NIP);
        updatedUserEntity.setEmail(EMAIL);
        updatedUserEntity.setPhoneNumber(PHONE_NUMBER);
        updatedUserEntity.setAddressEntity(existingAddressEntity);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(existingUserEntity));
        when(userMapper.toEntity(userDto)).thenReturn(updatedUserEntity);
        when(userRepository.save(updatedUserEntity)).thenReturn(updatedUserEntity);
        when(userMapper.toDto(updatedUserEntity)).thenReturn(userDto);

        // When
        UserDto updatedUser = userService.update(USER_ID, userDto);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(updatedUser, "Expected updated user to be not null"),
                () -> Assertions.assertEquals(userDto.getId(), updatedUser.getId(), "Expected updated user ID to match"),
                () -> Assertions.assertEquals(userDto.getNip(), updatedUser.getNip(), "Expected updated user NIP to match"),
                () -> Assertions.assertEquals(userDto.getEmail(), updatedUser.getEmail(), "Expected updated user email to match"),
                () -> Assertions.assertEquals(userDto.getPhoneNumber(), updatedUser.getPhoneNumber(), "Expected updated user phone number to match"),
                () -> Assertions.assertEquals(addressDto.getId(), updatedUser.getAddress().getId(), "Expected updated address ID to match"),
                () -> Assertions.assertEquals(addressDto.getStreet(), updatedUser.getAddress().getStreet(), "Expected updated address street to match"),
                () -> Assertions.assertEquals(addressDto.getCity(), updatedUser.getAddress().getCity(), "Expected updated address city to match"),
                () -> Assertions.assertEquals(addressDto.getPostalCode(), updatedUser.getAddress().getPostalCode(), "Expected updated address postal code to match"),
                () -> Assertions.assertEquals(addressDto.getHouseNumber(), updatedUser.getAddress().getHouseNumber(), "Expected updated address house number to match")
        );
    }
}
