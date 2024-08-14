package pl.wasik.damian.project.spring.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
