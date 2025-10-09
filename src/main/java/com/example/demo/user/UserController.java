package com.example.demo.user;

import com.example.demo.user.dto.request.AssignRoleDto;
import com.example.demo.user.dto.request.CreateUserDto;
import com.example.demo.user.dto.response.UserDto;
import com.example.demo.user.dto.request.UpdateUserDto;
import com.example.demo.user.service.UserService;
import com.example.demo.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers(){
        List<UserDto> countries = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<UserDto>>builder()
                        .success(true)
                        .message("Lấy users thành công")
                        .data(countries)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody CreateUserDto user){
        UserDto created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserDto>builder()
                        .success(true)
                        .message("Tạo user thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@Valid @RequestBody UpdateUserDto userDto){
        UserDto updated = userService.updateUser(userDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserDto>builder()
                        .success(true)
                        .message("Cập nhật user thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> deleteUserById(@PathVariable Long id) {
        UserDto deleted = userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserDto>builder()
                        .success(true)
                        .message("Xóa user thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserDto>builder()
                        .success(true)
                        .message("Lấy user thành công")
                        .data(user)
                        .build());
    }

    @GetMapping("/{email}/by-email")
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email){
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserDto>builder()
                        .success(true)
                        .message("Lấy user thành công")
                        .data(user)
                        .build());
    }

    @PostMapping("/assign-role")
    public ResponseEntity<ApiResponse<UserDto>> assignRole(@Valid @RequestBody AssignRoleDto assignRoleDto){
        UserDto user = userService.assignRole(assignRoleDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserDto>builder()
                        .success(true)
                        .message("Gán role cho user thành công")
                        .data(user)
                        .build());
    }

    @PostMapping("/remove-role")
    public ResponseEntity<ApiResponse<UserDto>> removeRole(@Valid @RequestBody AssignRoleDto assignRoleDto){
        UserDto user = userService.removeRole(assignRoleDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserDto>builder()
                        .success(true)
                        .message("Xóa role khỏi user thành công")
                        .data(user)
                        .build());
    }
}
