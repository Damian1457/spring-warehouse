package pl.wasik.damian.project.spring.warehouse.service;

import org.springframework.stereotype.Service;
import pl.wasik.damian.project.spring.warehouse.mapper.UserMapper;
import pl.wasik.damian.project.spring.warehouse.repository.UserRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.UserEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.UserDto;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAll() {
        LOGGER.info("getAll()");
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = userEntities.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        LOGGER.info("getAll(...) = " + userDtos);
        return userDtos;
    }

    public UserDto create(UserDto userDto) {
        LOGGER.info("create(" + userDto + ")");
        UserEntity userEntity = userMapper.toEntity(userDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserDto savedUserDto = userMapper.toDto(savedUserEntity);
        LOGGER.info("create(...) = " + savedUserDto);
        return savedUserDto;
    }

    public UserDto read(Long userId) {
        LOGGER.info("read(" + userId + ")");
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDto userDto = userMapper.toDto(userEntity);
        LOGGER.info("read(...) = " + userDto);
        return userDto;
    }

    public UserDto update(Long userId, UserDto userDto) {
        LOGGER.info("update(" + userId + ", " + userDto + ")");
        UserEntity existingUserEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity userEntityToUpdate = userMapper.toEntity(userDto);
        userEntityToUpdate.setId(userId);
        if (existingUserEntity.getAddressEntity() != null) {
            userEntityToUpdate.getAddressEntity().setId(existingUserEntity.getAddressEntity().getId());
            userEntityToUpdate.getAddressEntity().setUserEntity(userEntityToUpdate);
        }
        UserEntity updatedUserEntity = userRepository.save(userEntityToUpdate);
        UserDto updatedUserDto = userMapper.toDto(updatedUserEntity);
        LOGGER.info("update(...) = " + updatedUserDto);
        return updatedUserDto;
    }

    public void delete(Long userId) {
        LOGGER.info("delete(" + userId + ")");
        userRepository.deleteById(userId);
        LOGGER.info("delete(...)");
    }
}
