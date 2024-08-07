package pl.wasik.damian.project.spring.warehouse.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;
import pl.wasik.damian.project.spring.warehouse.web.model.UserDto;

import java.util.Collections;
import java.util.List;

@DisplayName("UserMapper Unit Test")
class UserMapperUnitTest {

    private static final Long USER_ID = 1L;
    private static final String NIP = "1234567890";
    private static final String EMAIL = "user@example.com";
    private static final String PHONE_NUMBER = "123-456-789";
    private static final Long ADDRESS_ID = 1L;
    private static final String STREET = "Main St";
    private static final String CITY = "Springfield";
    private static final String POSTAL_CODE = "12345";
    private static final String HOUSE_NUMBER = "1A";

    @Test
    @DisplayName("given UserEntity when map to UserDto then return UserDto")
    void givenUserEntity_whenMapToDto_thenReturnUserDto() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        UserMapper userMapper = new UserMapper(modelMapper, addressMapper);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);
        userEntity.setAddressEntity(addressEntity);

        // When
        UserDto userDto = userMapper.toDto(userEntity);

        // Then
        Assertions.assertAll("userDto",
                () -> Assertions.assertNotNull(userDto, "UserDto should not be null"),
                () -> Assertions.assertEquals(USER_ID, userDto.getId(), "ID should match"),
                () -> Assertions.assertEquals(NIP, userDto.getNip(), "NIP should match"),
                () -> Assertions.assertEquals(EMAIL, userDto.getEmail(), "Email should match"),
                () -> Assertions.assertEquals(PHONE_NUMBER, userDto.getPhoneNumber(), "PhoneNumber should match"),
                () -> Assertions.assertNotNull(userDto.getAddress(), "Address should not be null"),
                () -> Assertions.assertEquals(ADDRESS_ID, userDto.getAddress().getId(), "Address ID should match"),
                () -> Assertions.assertEquals(STREET, userDto.getAddress().getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, userDto.getAddress().getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, userDto.getAddress().getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, userDto.getAddress().getHouseNumber(), "HouseNumber should match")
        );
    }

    @Test
    @DisplayName("given UserDto when map to UserEntity then return UserEntity")
    void givenUserDto_whenMapToEntity_thenReturnUserEntity() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        UserMapper userMapper = new UserMapper(modelMapper, addressMapper);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setNip(NIP);
        userDto.setEmail(EMAIL);
        userDto.setPhoneNumber(PHONE_NUMBER);
        userDto.setAddress(addressDto);

        // When
        UserEntity userEntity = userMapper.toEntity(userDto);

        // Then
        Assertions.assertAll("userEntity",
                () -> Assertions.assertNotNull(userEntity, "UserEntity should not be null"),
                () -> Assertions.assertEquals(USER_ID, userEntity.getId(), "ID should match"),
                () -> Assertions.assertEquals(NIP, userEntity.getNip(), "NIP should match"),
                () -> Assertions.assertEquals(EMAIL, userEntity.getEmail(), "Email should match"),
                () -> Assertions.assertEquals(PHONE_NUMBER, userEntity.getPhoneNumber(), "PhoneNumber should match"),
                () -> Assertions.assertNotNull(userEntity.getAddressEntity(), "AddressEntity should not be null"),
                () -> Assertions.assertEquals(ADDRESS_ID, userEntity.getAddressEntity().getId(), "Address ID should match"),
                () -> Assertions.assertEquals(STREET, userEntity.getAddressEntity().getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, userEntity.getAddressEntity().getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, userEntity.getAddressEntity().getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, userEntity.getAddressEntity().getHouseNumber(), "HouseNumber should match"),
                () -> Assertions.assertEquals(userEntity, userEntity.getAddressEntity().getUserEntity(), "UserEntity should match in AddressEntity")
        );
    }

    @Test
    @DisplayName("given List<UserEntity> when map to List<UserDto> then return List<UserDto>")
    void givenUserEntityList_whenMapToDtoList_thenReturnUserDtoList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        UserMapper userMapper = new UserMapper(modelMapper, addressMapper);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);
        userEntity.setAddressEntity(addressEntity);
        
        // When
        List<UserEntity> userEntities = Collections.singletonList(userEntity);
        List<UserDto> userDtos = userMapper.toDtoList(userEntities);

        // Then
        Assertions.assertAll("userDtos",
                () -> Assertions.assertNotNull(userDtos, "UserDtos should not be null"),
                () -> Assertions.assertEquals(1, userDtos.size(), "UserDtos size should be 1"),
                () -> Assertions.assertEquals(USER_ID, userDtos.get(0).getId(), "ID should match"),
                () -> Assertions.assertEquals(NIP, userDtos.get(0).getNip(), "NIP should match"),
                () -> Assertions.assertEquals(EMAIL, userDtos.get(0).getEmail(), "Email should match"),
                () -> Assertions.assertEquals(PHONE_NUMBER, userDtos.get(0).getPhoneNumber(), "PhoneNumber should match"),
                () -> Assertions.assertNotNull(userDtos.get(0).getAddress(), "Address should not be null"),
                () -> Assertions.assertEquals(ADDRESS_ID, userDtos.get(0).getAddress().getId(), "Address ID should match"),
                () -> Assertions.assertEquals(STREET, userDtos.get(0).getAddress().getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, userDtos.get(0).getAddress().getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, userDtos.get(0).getAddress().getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, userDtos.get(0).getAddress().getHouseNumber(), "HouseNumber should match")
        );
    }

    @Test
    @DisplayName("given List<UserDto> when map to List<UserEntity> then return List<UserEntity>")
    void givenUserDtoList_whenMapToEntityList_thenReturnUserEntityList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        UserMapper userMapper = new UserMapper(modelMapper, addressMapper);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);

        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setNip(NIP);
        userDto.setEmail(EMAIL);
        userDto.setPhoneNumber(PHONE_NUMBER);
        userDto.setAddress(addressDto);

        // When
        List<UserDto> userDtos = Collections.singletonList(userDto);
        List<UserEntity> userEntities = userMapper.toEntityList(userDtos);

        // Then
        Assertions.assertAll("userEntities",
                () -> Assertions.assertNotNull(userEntities, "UserEntities should not be null"),
                () -> Assertions.assertEquals(1, userEntities.size(), "UserEntities size should be 1"),
                () -> Assertions.assertEquals(USER_ID, userEntities.get(0).getId(), "ID should match"),
                () -> Assertions.assertEquals(NIP, userEntities.get(0).getNip(), "NIP should match"),
                () -> Assertions.assertEquals(EMAIL, userEntities.get(0).getEmail(), "Email should match"),
                () -> Assertions.assertEquals(PHONE_NUMBER, userEntities.get(0).getPhoneNumber(), "PhoneNumber should match"),
                () -> Assertions.assertNotNull(userEntities.get(0).getAddressEntity(), "AddressEntity should not be null"),
                () -> Assertions.assertEquals(ADDRESS_ID, userEntities.get(0).getAddressEntity().getId(), "Address ID should match"),
                () -> Assertions.assertEquals(STREET, userEntities.get(0).getAddressEntity().getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, userEntities.get(0).getAddressEntity().getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, userEntities.get(0).getAddressEntity().getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, userEntities.get(0).getAddressEntity().getHouseNumber(), "HouseNumber should match"),
                () -> Assertions.assertEquals(userEntities.get(0), userEntities.get(0).getAddressEntity().getUserEntity(), "UserEntity should be set in AddressEntity")
        );
    }
}