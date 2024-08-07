package pl.wasik.damian.project.spring.warehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.UserDto;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static final Logger LOGGER = Logger.getLogger(UserMapper.class.getName());
    private final ModelMapper modelMapper;
    private final AddressMapper addressMapper;

    public UserMapper(ModelMapper modelMapper, AddressMapper addressMapper) {
        this.modelMapper = modelMapper;
        this.addressMapper = addressMapper;
    }

    public UserDto toDto(UserEntity userEntity) {
        LOGGER.info("toDto(" + userEntity + ")");
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        if (userEntity.getAddressEntity() != null) {
            userDto.setAddress(addressMapper.toDto(userEntity.getAddressEntity()));
        }
        LOGGER.info("toDto(" + userDto + ")");
        return userDto;
    }

    public UserEntity toEntity(UserDto userDto) {
        LOGGER.info("toEntity(" + userDto + ")");
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        if (userDto.getAddress() != null) {
            AddressEntity addressEntity = addressMapper.toEntity(userDto.getAddress());
            if (userEntity.getId() != null && userEntity.getAddressEntity() != null) {
                addressEntity.setId(userEntity.getAddressEntity().getId());
            }
            addressEntity.setUserEntity(userEntity);
            userEntity.setAddressEntity(addressEntity);
        }
        LOGGER.info("toEntity(" + userEntity + ")");
        return userEntity;
    }

    public List<UserDto> toDtoList(List<UserEntity> userEntities) {
        LOGGER.info("toDtoList(" + userEntities + ")");
        List<UserDto> userDtos = userEntities.stream().map(this::toDto).collect(Collectors.toList());
        LOGGER.info("toDtoList(" + userDtos + ")");
        return userDtos;
    }

    public List<UserEntity> toEntityList(List<UserDto> userDtos) {
        LOGGER.info("toEntityList(" + userDtos + ")");
        List<UserEntity> userEntities = userDtos.stream().map(this::toEntity).collect(Collectors.toList());
        LOGGER.info("toEntityList(" + userEntities + ")");
        return userEntities;
    }
}