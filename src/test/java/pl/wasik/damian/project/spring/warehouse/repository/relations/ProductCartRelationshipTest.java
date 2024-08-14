package pl.wasik.damian.project.spring.warehouse.repository.relations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.CartRepository;
import pl.wasik.damian.project.spring.warehouse.repository.ProductRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@DisplayName("ProductEntity and CartEntity Relationship Test")
public class ProductCartRelationshipTest {

    private static final String PRODUCT_NAME = "Test Product";
    private static final String PRODUCT_DESCRIPTION = "Test Description";
    private static final int PRODUCT_CAPACITY = 100;
    private static final int PRODUCT_STOCK = 50;
    private static final double PRODUCT_PRICE = 19.99;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("Given product and cart entities, when associated, then relationship is correct")
    public void givenProductAndCartEntities_whenAssociated_thenRelationshipIsCorrect() {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(PRODUCT_NAME);
        productEntity.setCapacity(PRODUCT_CAPACITY);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        productEntity.setStock(PRODUCT_STOCK);
        productEntity.setPrice(PRODUCT_PRICE);

        CartEntity cartEntity = new CartEntity();
        Set<ProductEntity> productEntities = new HashSet<>();
        productEntities.add(productEntity);
        cartEntity.setProductEntities(productEntities);

        // When
        cartRepository.save(cartEntity);
        CartEntity foundCartEntity = cartRepository.findById(cartEntity.getId()).orElse(null);

        // Then
        Assertions.assertAll("Verify relationship between ProductEntity and CartEntity",
                () -> Assertions.assertNotNull(foundCartEntity, "CartEntity should not be null"),
                () -> Assertions.assertEquals(1, foundCartEntity.getProductEntities().size(), "CartEntity should contain one ProductEntity"),
                () -> Assertions.assertEquals(PRODUCT_NAME, foundCartEntity.getProductEntities().iterator().next().getName(), "ProductEntity name should match")
        );
    }
}
