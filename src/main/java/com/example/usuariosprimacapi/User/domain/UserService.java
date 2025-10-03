package com.example.usuariosprimacapi.User.domain;

import com.example.usuariosprimacapi.User.dto.UserRequestDto;
import com.example.usuariosprimacapi.User.dto.UserResponseDto;
import com.example.usuariosprimacapi.User.dto.UserPatchDto;
import com.example.usuariosprimacapi.User.infrastructure.UserRepository;
import com.example.usuariosprimacapi.util.DateTimeUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getAllUsers() {
        // For backward compatibility, return first 100 users
        Pageable pageable = PageRequest.of(0, 100, Sort.by("id"));
        return userRepository.findAllPaginated(pageable).getContent().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    
    public Page<UserResponseDto> getAllUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return userRepository.findAllPaginated(pageable)
                .map(this::mapToResponseDto);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return mapToResponseDto(user);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = mapToEntity(userRequestDto);
        user.setCreatedAt(DateTimeUtils.nowUtc());
        user.setUpdatedAt(DateTimeUtils.nowUtc());
        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Update fields
        existingUser.setUsername(userRequestDto.getUsername());
        existingUser.setEmail(userRequestDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        existingUser.setRole(userRequestDto.getRole());
        existingUser.setPhone(userRequestDto.getPhone());
        existingUser.setStreet(userRequestDto.getStreet());
        existingUser.setCity(userRequestDto.getCity());
        existingUser.setState(userRequestDto.getState());
        existingUser.setUpdatedAt(DateTimeUtils.nowUtc());

        User updatedUser = userRepository.save(existingUser);
        return mapToResponseDto(updatedUser);
    }

    public UserResponseDto patchUser(Long id, UserPatchDto userPatchDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Update only non-null fields
        if (userPatchDto.getUsername() != null) {
            existingUser.setUsername(userPatchDto.getUsername());
        }
        if (userPatchDto.getEmail() != null) {
            existingUser.setEmail(userPatchDto.getEmail());
        }
        if (userPatchDto.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userPatchDto.getPassword()));
        }
        if (userPatchDto.getRole() != null) {
            existingUser.setRole(userPatchDto.getRole());
        }
        if (userPatchDto.getPhone() != null) {
            existingUser.setPhone(userPatchDto.getPhone());
        }
        if (userPatchDto.getStreet() != null) {
            existingUser.setStreet(userPatchDto.getStreet());
        }
        if (userPatchDto.getCity() != null) {
            existingUser.setCity(userPatchDto.getCity());
        }
        if (userPatchDto.getState() != null) {
            existingUser.setState(userPatchDto.getState());
        }
        existingUser.setUpdatedAt(DateTimeUtils.nowUtc());

        User updatedUser = userRepository.save(existingUser);
        return mapToResponseDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private User mapToEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRequestDto.getRole())
                .phone(userRequestDto.getPhone())
                .street(userRequestDto.getStreet())
                .city(userRequestDto.getCity())
                .state(userRequestDto.getState())
                .build();
    }

    private UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .street(user.getStreet())
                .city(user.getCity())
                .state(user.getState())
                .build();
    }
}
