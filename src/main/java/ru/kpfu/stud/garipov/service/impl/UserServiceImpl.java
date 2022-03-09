package ru.kpfu.stud.garipov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;
import ru.kpfu.stud.garipov.model.User;
import ru.kpfu.stud.garipov.repository.UserRepository;
import ru.kpfu.stud.garipov.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDto getByEmail(String email) {
        return userRepository.getUserByEmail(email).map(UserDto::fromModel).orElse(null);
    }

    @Override
    public UserDto save(CreateUserDto createUserDto) {
        User user = new User(
                createUserDto.getName(),
                createUserDto.getEmail(),
                encoder.encode(createUserDto.getPassword())
        );
        return UserDto.fromModel(userRepository.save(user));
    }

    @Override
    public UserDto getById(Integer id) {
        return userRepository.findById(id).map(UserDto::fromModel).orElse(null);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserDto::fromModel).collect(Collectors.toList());
    }
}
