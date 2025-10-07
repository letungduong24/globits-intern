package com.example.demo.user;

import com.example.demo.user.dto.CreateUserDto;
import com.example.demo.user.dto.ResponseUserDto;
import com.example.demo.user.dto.UpdateUserDto;
import com.example.demo.user.service.UserService;
import com.example.demo.shared.ApiResponse;
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
    public List<ResponseUserDto> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ResponseUserDto>> createUser(@Valid @RequestBody CreateUserDto user){
        ResponseUserDto created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseUserDto>builder()
                        .success(true)
                        .message("Tạo user thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ResponseUserDto>> updateUser(@Valid @RequestBody UpdateUserDto userDto){
        ResponseUserDto updated = userService.updateUser(userDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserDto>builder()
                        .success(true)
                        .message("Cập nhật user thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseUserDto>> deleteUserById(@PathVariable Long id) {
        ResponseUserDto deleted = userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserDto>builder()
                        .success(true)
                        .message("Xóa user thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseUserDto getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseUserDto getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }
}
