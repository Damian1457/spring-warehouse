package pl.wasik.damian.project.spring.warehouse.web.model;

import java.util.Set;

public class CartDto {
    private Long id;
    private UserDto user;
    private Set<ProductDto> products;

    public CartDto() {
    }

    public CartDto(Long id, UserDto user, Set<ProductDto> products) {
        this.id = id;
        this.user = user;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDto> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
