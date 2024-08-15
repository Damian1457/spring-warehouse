package pl.wasik.damian.project.spring.warehouse.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@DisplayName("Product Repository Unit Test")
public class ProductRepositoryUnitTest {

    public static final String TEST_NAME = "Milk";
    public static final String PATH_IMAGES_TEST_IMAGE_PNG = "/images/test-image.png";

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Given product repository, when add product, then product is saved")
    void givenProductRepository_whenAddProduct_thenProductIsSaved() {
        // Given
        byte[] sampleImageData = new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47,
                (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D};
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(TEST_NAME);
        productEntity.setImage(sampleImageData);

        // When
        ProductEntity savedProduct = productRepository.save(productEntity);

        //Then
        Assertions.assertNotNull(savedProduct, "The product has not been saved");
    }

    @Test
    @DisplayName("Given product repository, when add product with image, then product is saved")
    void givenProductRepository_whenAddProductWithImage_thenProductIsSaved() throws Exception {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(TEST_NAME);
        Path imagePath = Paths.get(getClass().getResource(PATH_IMAGES_TEST_IMAGE_PNG).toURI());
        byte[] imageBytes = Files.readAllBytes(imagePath);
        productEntity.setImage(imageBytes);

        // When
        ProductEntity savedProduct = productRepository.save(productEntity);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(savedProduct, "The product has not been saved"),
                () -> Assertions.assertArrayEquals(imageBytes, savedProduct.getImage(), "The image data does not match")
        );
    }
}
