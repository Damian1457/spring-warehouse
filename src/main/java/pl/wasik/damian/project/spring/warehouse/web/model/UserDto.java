package pl.wasik.damian.project.spring.warehouse.web;


public class UserDto {
    private Long id;
    private String nip;
    private String email;
    private String phoneNumber;
    private AddressDto address;
    
    public UserDto() {
    }

    public UserDto(Long id, String nip, String email, String phoneNumber, AddressDto address) {
        this.id = id;
        this.nip = nip;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", nip='" + nip + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                '}';
    }
}
