package pl.wasik.damian.project.spring.warehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class AddressMapper {

    private static final Logger LOGGER = Logger.getLogger(AddressMapper.class.getName());
    private final ModelMapper modelMapper;

    public AddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AddressDto toDto(AddressEntity addressEntity) {
        LOGGER.info("toDto(" + addressEntity + ")");
        AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
        LOGGER.info("toDto(" + addressDto + ")");
        return addressDto;
    }

    public AddressEntity toEntity(AddressDto addressDto) {
        LOGGER.info("toEntity(" + addressDto + ")");
        AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);
        LOGGER.info("toEntity(" + addressEntity + ")");
        return addressEntity;
    }

    public List<AddressDto> toDtoList(List<AddressEntity> addressEntities) {
        LOGGER.info("toDtoList(" + addressEntities + ")");
        List<AddressDto> addressDtos = addressEntities.stream().map(this::toDto).collect(Collectors.toList());
        LOGGER.info("toDtoList(" + addressDtos + ")");
        return addressDtos;
    }

    public List<AddressEntity> toEntityList(List<AddressDto> addressDtos) {
        LOGGER.info("toEntityList(" + addressDtos + ")");
        List<AddressEntity> addressEntities = addressDtos.stream().map(this::toEntity).collect(Collectors.toList());
        LOGGER.info("toEntityList(" + addressEntities + ")");
        return addressEntities;
    }
}
