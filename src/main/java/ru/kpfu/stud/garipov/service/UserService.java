package ru.kpfu.stud.garipov.service;

import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getByEmail(String email);

    UserDto save(CreateUserDto createUserDto);

    UserDto getById(Integer id);

    List<UserDto> getAll();
}
