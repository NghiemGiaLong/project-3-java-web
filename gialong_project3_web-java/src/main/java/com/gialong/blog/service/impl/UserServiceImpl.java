package com.gialong.blog.service.impl;

import com.gialong.blog.entity.Role;
import com.gialong.blog.entity.User;
import com.gialong.blog.exception.BlogApiException;
import com.gialong.blog.exception.ResourceNotFoundException;
import com.gialong.blog.payload.UserDto;
import com.gialong.blog.repository.RoleRepository;
import com.gialong.blog.repository.UserRepository;
import com.gialong.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // Dùng LocalDateTime thay vì Date
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return user.getId();
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return mapToDto(user);
    }

    @Override
    public UserDto updateUserDetails(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    public UserDto updateUserRole(Long userId, String newRoleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Role role = roleRepository.findByRoleName(newRoleName)
                .orElseThrow(() -> new BlogApiException(HttpStatus.BAD_REQUEST, "Không tìm thấy Role: " + newRoleName));

        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    // --- Đã sửa tên hàm và thêm @Override ---
    @Override
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        boolean currentStatus = user.isActive();

        if (currentStatus) {
            // Đang Mở -> KHÓA
            user.setActive(false);
            user.setLockTime(LocalDateTime.now()); // Ghi lại giờ khóa
        } else {
            // Đang Khóa -> MỞ KHÓA
            user.setActive(true);
            user.setLockTime(null); // Xóa giờ khóa

            // Reset bộ đếm lỗi để user không bị khóa lại ngay lập tức
            if (user.getFailedAttempt() > 0) {
                user.setFailedAttempt(0);
            }
        }

        userRepository.save(user);
    }

    // --- Mapper ---
    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());

        if (user.getRole() != null) {
            dto.setRole(user.getRole().getRoleName());
        }
        return dto;
    }
}