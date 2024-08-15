package pl.wasik.damian.project.spring.warehouse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wasik.damian.project.spring.warehouse.mapper.ProductMapper;
import pl.wasik.damian.project.spring.warehouse.repository.ProductRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> findAll() {
        LOGGER.info("findAll()");
        List<ProductDto> productsDto = productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        LOGGER.info("findAll(...) = " + productsDto);
        return productsDto;
    }

    public ProductDto create(ProductDto productDto, MultipartFile image) throws IOException {
        LOGGER.info("Creating product: " + productDto.getName());
        if (image != null && !image.isEmpty()) {
            String imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());
            productDto.setImageBase64(imageBase64);
        }
        ProductEntity productEntity = productMapper.toEntity(productDto);
        ProductEntity savedEntity = productRepository.save(productEntity);
        ProductDto savedProductDto = productMapper.toDto(savedEntity);
        LOGGER.info("create(...) = " + savedProductDto);
        return savedProductDto;
    }

    public ProductDto read(Long productId) {
        LOGGER.info("read(" + productId + ")");
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        ProductDto productDto = productMapper.toDto(productEntity);
        LOGGER.info("read(...) = " + productDto);
        return productDto;
    }

    public ProductDto update(Long id, ProductDto productDto, MultipartFile image) throws IOException {
        LOGGER.info("update()");
        if (image != null && !image.isEmpty()) {
            String imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());
            productDto.setImageBase64(imageBase64);
        }
        ProductEntity mappedProductEntity = productMapper.toEntity(productDto);
        mappedProductEntity.setId(id);
        ProductEntity updatedProductEntity = productRepository.save(mappedProductEntity);
        ProductDto updatedProductDto = productMapper.toDto(updatedProductEntity);
        LOGGER.info("update(...) = " + updatedProductDto);
        return updatedProductDto;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        productRepository.deleteById(id);
        LOGGER.info("delete(...) = ");
    }
}

