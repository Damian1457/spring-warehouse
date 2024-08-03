package pl.wasik.damian.project.spring.warehouse.service;

import org.springframework.stereotype.Service;
import pl.wasik.damian.project.spring.warehouse.mapper.AddressMapper;
import pl.wasik.damian.project.spring.warehouse.repository.AddressRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.AddressEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private static final Logger LOGGER = Logger.getLogger(AddressService.class.getName());
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDto> getAll() {
        LOGGER.info("getAll()");
        List<AddressEntity> addressEntities = addressRepository.findAll();
        List<AddressDto> addressDtos = addressEntities.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        LOGGER.info("getAll(...) = " + addressDtos);
        return addressDtos;
    }

    public AddressDto create(AddressDto addressDto) {
        LOGGER.info("create(" + addressDto + ")");
        AddressEntity addressEntity = addressMapper.toEntity(addressDto);
        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);
        AddressDto savedAddressDto = addressMapper.toDto(savedAddressEntity);
        LOGGER.info("create(...) = " + savedAddressDto);
        return savedAddressDto;
    }

    public AddressDto read(Long addressId) {
        LOGGER.info("read(" + addressId + ")");
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        AddressDto addressDto = addressMapper.toDto(addressEntity);
        LOGGER.info("read(...) = " + addressDto);
        return addressDto;
    }
}
