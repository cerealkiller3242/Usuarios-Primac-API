package com.example.usuariosprimacapi.User.application;

import com.example.usuariosprimacapi.User.domain.UserService;
import com.example.usuariosprimacapi.User.dto.UserRequestDto;
import com.example.usuariosprimacapi.User.dto.UserResponseDto;
import com.example.usuariosprimacapi.User.dto.UserPatchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/paginated")
    public ResponseEntity<Page<UserResponseDto>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(userService.getAllUsersPaginated(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patchUser(@PathVariable Long id, @Valid @RequestBody UserPatchDto userPatchDto) {
        return ResponseEntity.ok(userService.patchUser(id, userPatchDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
