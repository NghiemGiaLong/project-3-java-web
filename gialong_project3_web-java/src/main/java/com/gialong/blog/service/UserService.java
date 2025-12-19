package com.gialong.blog.service;

import com.gialong.blog.payload.UserDto;
import java.util.List;

public interface UserService {

    Long getUserIdByUsername(String username);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    UserDto updateUserDetails(Long userId, UserDto userDto);

    UserDto updateUserRole(Long userId, String newRoleName);

    void toggleUserStatus(Long userId);
}