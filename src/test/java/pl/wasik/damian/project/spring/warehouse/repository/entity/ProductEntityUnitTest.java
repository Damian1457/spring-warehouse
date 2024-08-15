package pl.wasik.damian.project.spring.warehouse.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Product Entity Unit Test")
public class ProductEntityUnitTest {

    private static final String TEST_NAME = "Milk";

    @Test
    @DisplayName("Given product entity, when set product name, then name is correct")
    void givenProductEntity_whenSetName_thenNameIsCorrect() {
        // Given
        ProductEntity productEntity = new ProductEntity();

        // When
        productEntity.setName(TEST_NAME);

        //Then
        Assertions.assertEquals(TEST_NAME, productEntity.getName(), "The values are not identical");
    }
}
