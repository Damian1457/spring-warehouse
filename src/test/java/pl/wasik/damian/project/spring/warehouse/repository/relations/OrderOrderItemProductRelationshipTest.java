package pl.wasik.damian.project.spring.warehouse.repository.relations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.OrderItemRepository;
import pl.wasik.damian.project.spring.warehouse.repository.OrderRepository;
import pl.wasik.damian.project.spring.warehouse.repository.ProductRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.OrderEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.OrderItemEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@DisplayName("OrderEntity, OrderItemEntity, and ProductEntity Relationship Test")
public class OrderOrderItemProductRelationshipTest {

    private static final String PRODUCT_NAME = "Test Product";
    private static final String PRODUCT_DESCRIPTION = "Test Description";
    private static final int PRODUCT_CAPACITY = 100;
    private static final int PRODUCT_STOCK = 50;
    private static final double PRODUCT_PRICE = 19.99;
    private static final int ORDER_ITEM_QUANTITY = 2;
    private static final String ORDER_STATUS = "PENDING";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Given order, order item, and product entities, when associated, then relationships are correct")
    public void givenOrderOrderItemProductEntities_whenAssociated_thenRelationshipsAreCorrect() {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(PRODUCT_NAME);
        productEntity.setCapacity(PRODUCT_CAPACITY);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        productEntity.setStock(PRODUCT_STOCK);
        productEntity.setPrice(PRODUCT_PRICE);
        productRepository.save(productEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setStatus(ORDER_STATUS);

        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProductEntity(productEntity);
        orderItemEntity.setQuantity(ORDER_ITEM_QUANTITY);
        orderItemEntity.setPrice(productEntity.getPrice());
        orderItemEntity.setOrderEntity(orderEntity);

        Set<OrderItemEntity> orderItemEntities = new HashSet<>();
        orderItemEntities.add(orderItemEntity);
        orderEntity.setOrderItemEntities(orderItemEntities);

        // When
        orderRepository.save(orderEntity);
        OrderEntity foundOrderEntity = orderRepository.findById(orderEntity.getId()).orElse(null);

        // Then
        Assertions.assertAll("Verify relationships between OrderEntity, OrderItemEntity, and ProductEntity",
                () -> Assertions.assertNotNull(foundOrderEntity, "OrderEntity should not be null"),
                () -> Assertions.assertEquals(1, foundOrderEntity.getOrderItemEntities().size(), "OrderEntity should contain one OrderItemEntity"),
                () -> Assertions.assertEquals(PRODUCT_NAME, foundOrderEntity.getOrderItemEntities().iterator().next().getProductEntity().getName(), "ProductEntity name should match")
        );
    }
}
