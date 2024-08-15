package pl.wasik.damian.project.spring.warehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;

@Component
public class ProductMapper {

    private static final Logger LOGGER = Logger.getLogger(ProductMapper.class.getName());
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto toDto(ProductEntity productEntity) {
        LOGGER.info("toDto(" + productEntity + ")");
        ProductDto productDto = modelMapper.map(productEntity, ProductDto.class);
        if (productEntity.getImage() != null) {
            productDto.setImageBase64(Base64.getEncoder().encodeToString(productEntity.getImage()));
        }
        LOGGER.info("toDto(" + productDto + ")");
        return productDto;
    }

    public ProductEntity toEntity(ProductDto productDto) {
        LOGGER.info("toEntity(" + productDto + ")");
        ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
        if (productDto.getImageBase64() != null && !productDto.getImageBase64().isEmpty()) {
            productEntity.setImage(Base64.getDecoder().decode(productDto.getImageBase64()));
        }
        LOGGER.info("toEntity(" + productEntity + ")");
        return productEntity;
    }

    public List<ProductDto> toDtoList(List<ProductEntity> productEntities) {
        LOGGER.info("toDtoList(" + productEntities + ")");
        List<ProductDto> productDtos = productEntities.stream().map(this::toDto).collect(Collectors.toList());
        LOGGER.info("toDtoList(" + productDtos + ")");
        return productDtos;
    }

    public List<ProductEntity> toEntityList(List<ProductDto> productDtos) {
        LOGGER.info("toEntityList(" + productDtos + ")");
        List<ProductEntity> productEntities = productDtos.stream().map(this::toEntity).collect(Collectors.toList());
        LOGGER.info("toEntityList(" + productEntities + ")");
        return productEntities;
    }
}
