package pl.wasik.damian.project.spring.warehouse.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;

import java.util.Collections;
import java.util.List;

@DisplayName("AddressMapper Unit Test")
class AddressMapperUnitTest {

    private static final Long ADDRESS_ID = 1L;
    private static final String STREET = "Main St";
    private static final String CITY = "Springfield";
    private static final String POSTAL_CODE = "12345";
    private static final String HOUSE_NUMBER = "1A";

    @Test
    @DisplayName("given AddressEntity when map to AddressDto then return AddressDto")
    void givenAddressEntity_whenMapToDto_thenReturnAddressDto() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        // When
        AddressDto addressDto = addressMapper.toDto(addressEntity);

        // Then
        Assertions.assertAll("addressDto",
                () -> Assertions.assertNotNull(addressDto, "AddressDto should not be null"),
                () -> Assertions.assertEquals(ADDRESS_ID, addressDto.getId(), "ID should match"),
                () -> Assertions.assertEquals(STREET, addressDto.getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, addressDto.getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, addressDto.getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, addressDto.getHouseNumber(), "HouseNumber should match")
        );
    }

    @Test
    @DisplayName("given AddressDto when map to AddressEntity then return AddressEntity")
    void givenAddressDto_whenMapToEntity_thenReturnAddressEntity() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);

        // When
        AddressEntity addressEntity = addressMapper.toEntity(addressDto);

        // Then
        Assertions.assertAll("addressEntity",
                () -> Assertions.assertNotNull(addressEntity, "AddressEntity should not be null"),
                () -> Assertions.assertEquals(ADDRESS_ID, addressEntity.getId(), "ID should match"),
                () -> Assertions.assertEquals(STREET, addressEntity.getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, addressEntity.getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, addressEntity.getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, addressEntity.getHouseNumber(), "HouseNumber should match")
        );
    }

    @Test
    @DisplayName("given List<AddressEntity> when map to List<AddressDto> then return List<AddressDto>")
    void givenAddressEntityList_whenMapToDtoList_thenReturnAddressDtoList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(ADDRESS_ID);
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);
        List<AddressEntity> addressEntities = Collections.singletonList(addressEntity);

        // When
        List<AddressDto> addressDtos = addressMapper.toDtoList(addressEntities);

        // Then
        Assertions.assertAll("addressDtos",
                () -> Assertions.assertNotNull(addressDtos, "AddressDtos should not be null"),
                () -> Assertions.assertEquals(1, addressDtos.size(), "AddressDtos size should be 1"),
                () -> Assertions.assertEquals(ADDRESS_ID, addressDtos.get(0).getId(), "ID should match"),
                () -> Assertions.assertEquals(STREET, addressDtos.get(0).getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, addressDtos.get(0).getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, addressDtos.get(0).getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, addressDtos.get(0).getHouseNumber(), "HouseNumber should match")
        );
    }

    @Test
    @DisplayName("given List<AddressDto> when map to List<AddressEntity> then return List<AddressEntity>")
    void givenAddressDtoList_whenMapToEntityList_thenReturnAddressEntityList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        AddressMapper addressMapper = new AddressMapper(modelMapper);
        AddressDto addressDto = new AddressDto();
        addressDto.setId(ADDRESS_ID);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setPostalCode(POSTAL_CODE);
        addressDto.setHouseNumber(HOUSE_NUMBER);
        List<AddressDto> addressDtos = Collections.singletonList(addressDto);

        // When
        List<AddressEntity> addressEntities = addressMapper.toEntityList(addressDtos);

        // Then
        Assertions.assertAll("addressEntities",
                () -> Assertions.assertNotNull(addressEntities, "AddressEntities should not be null"),
                () -> Assertions.assertEquals(1, addressEntities.size(), "AddressEntities size should be 1"),
                () -> Assertions.assertEquals(ADDRESS_ID, addressEntities.get(0).getId(), "ID should match"),
                () -> Assertions.assertEquals(STREET, addressEntities.get(0).getStreet(), "Street should match"),
                () -> Assertions.assertEquals(CITY, addressEntities.get(0).getCity(), "City should match"),
                () -> Assertions.assertEquals(POSTAL_CODE, addressEntities.get(0).getPostalCode(), "PostalCode should match"),
                () -> Assertions.assertEquals(HOUSE_NUMBER, addressEntities.get(0).getHouseNumber(), "HouseNumber should match")
        );
    }
}