package pl.wasik.damian.project.spring.warehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.wasik.damian.project.spring.warehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.CartDto;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    private static final Logger LOGGER = Logger.getLogger(CartMapper.class.getName());
    private final ModelMapper modelMapper;

    public CartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartDto toDto(CartEntity cartEntity) {
        LOGGER.info("toDto(" + cartEntity + ")");
        CartDto cartDto = modelMapper.map(cartEntity, CartDto.class);
        if (cartEntity.getProductEntities() != null) {
            Set<ProductDto> productDtos = cartEntity.getProductEntities().stream()
                    .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                    .collect(Collectors.toSet());
            cartDto.setProducts(productDtos);
        } else {
            cartDto.setProducts(new HashSet<>());
        }
        LOGGER.info("toDto(" + cartDto + ")");
        return cartDto;
    }

    public CartEntity toEntity(CartDto cartDto) {
        LOGGER.info("toEntity(" + cartDto + ")");
        CartEntity cartEntity = modelMapper.map(cartDto, CartEntity.class);
        if (cartDto.getUser() != null) {
            UserEntity userEntity = modelMapper.map(cartDto.getUser(), UserEntity.class);
            cartEntity.setUserEntity(userEntity);
        } else {
            cartEntity.setUserEntity(null);
        }

        if (cartDto.getProducts() != null) {
            Set<ProductEntity> productEntities = cartDto.getProducts().stream()
                    .map(productDto -> modelMapper.map(productDto, ProductEntity.class))
                    .collect(Collectors.toSet());
            cartEntity.setProductEntities(productEntities);
        } else {
            cartEntity.setProductEntities(new HashSet<>());
        }
        LOGGER.info("toEntity(" + cartEntity + ")");
        return cartEntity;
    }

    public List<CartDto> toDtoList(List<CartEntity> cartEntities) {
        LOGGER.info("toDtoList(" + cartEntities + ")");
        List<CartDto> cartDtos = cartEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        LOGGER.info("toDtoList(" + cartDtos + ")");
        return cartDtos;
    }

    public List<CartEntity> toEntityList(List<CartDto> cartDtos) {
        LOGGER.info("toEntityList(" + cartDtos + ")");
        List<CartEntity> cartEntities = cartDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        LOGGER.info("toEntityList(" + cartEntities + ")");
        return cartEntities;
    }
}
