package pl.wasik.damian.project.spring.warehouse.repository.relations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.spring.warehouse.repository.OrderRepository;
import pl.wasik.damian.project.spring.warehouse.repository.UserRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.OrderEntity;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;

import java.time.LocalDateTime;

@SpringBootTest
@DisplayName("OrderEntity, UserEntity, and AddressEntity Relationship Test")
public class OrderUserAddressRelationshipTest {

    private static final String STREET = "Test Street";
    private static final String CITY = "Test City";
    private static final String POSTAL_CODE = "00-000";
    private static final String HOUSE_NUMBER = "123";
    private static final String NIP = "1234567890";
    private static final String EMAIL = "test@example.com";
    private static final String PHONE_NUMBER = "123-456-789";
    private static final String ORDER_STATUS = "PENDING";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Given order, user, and address entities, when associated, then relationships are correct")
    public void givenOrderUserAddressEntities_whenAssociated_thenRelationshipsAreCorrect() {
        // Given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(STREET);
        addressEntity.setCity(CITY);
        addressEntity.setPostalCode(POSTAL_CODE);
        addressEntity.setHouseNumber(HOUSE_NUMBER);

        UserEntity userEntity = new UserEntity();
        userEntity.setNip(NIP);
        userEntity.setEmail(EMAIL);
        userEntity.setPhoneNumber(PHONE_NUMBER);
        userEntity.setAddressEntity(addressEntity);

        addressEntity.setUserEntity(userEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setStatus(ORDER_STATUS);
        orderEntity.setUserEntity(userEntity);
        orderEntity.setDeliveryAddressEntity(addressEntity);

        // When
        userRepository.save(userEntity);
        orderRepository.save(orderEntity);
        OrderEntity foundOrderEntity = orderRepository.findById(orderEntity.getId()).orElse(null);

        // Then
        Assertions.assertAll("Verify relationships between OrderEntity, UserEntity, and AddressEntity",
                () -> Assertions.assertNotNull(foundOrderEntity, "OrderEntity should not be null"),
                () -> Assertions.assertEquals(userEntity.getId(), foundOrderEntity.getUserEntity().getId(), "UserEntity in OrderEntity should match the saved UserEntity"),
                () -> Assertions.assertEquals(addressEntity.getId(), foundOrderEntity.getDeliveryAddressEntity().getId(), "AddressEntity in OrderEntity should match the saved AddressEntity")
        );
    }
}
