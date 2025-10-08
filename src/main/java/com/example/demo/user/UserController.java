package com.example.demo.user;

import com.example.demo.user.dto.AssignRoleDto;
import com.example.demo.user.dto.CreateUserDto;
import com.example.demo.user.dto.ResponseUserDto;
import com.example.demo.user.dto.ResponseUserWithPersonDto;
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
    public ResponseEntity<ApiResponse<List<ResponseUserDto>>> getUsers(){
        List<ResponseUserDto> countries = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseUserDto>>builder()
                        .success(true)
                        .message("Lấy users thành công")
                        .data(countries)
                        .build());
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
    public ResponseEntity<ApiResponse<ResponseUserDto>> getUserById(@PathVariable Long id){
        ResponseUserDto user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserDto>builder()
                        .success(true)
                        .message("Lấy user thành công")
                        .data(user)
                        .build());
    }

    @GetMapping("/{email}/by-email")
    public ResponseEntity<ApiResponse<ResponseUserDto>> getUserByEmail(@PathVariable String email){
        ResponseUserDto user = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserDto>builder()
                        .success(true)
                        .message("Lấy user thành công")
                        .data(user)
                        .build());
    }

    @GetMapping("/with-person/{id}")
    public ResponseEntity<ApiResponse<ResponseUserWithPersonDto>> getUserWithPersonById(@PathVariable Long id){
        ResponseUserWithPersonDto user = userService.getUserWithPersonById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserWithPersonDto>builder()
                        .success(true)
                        .message("Lấy user với person thành công")
                        .data(user)
                        .build());
    }

    @GetMapping("/with-person/{email}/by-email")
    public ResponseEntity<ApiResponse<ResponseUserWithPersonDto>> getUserWithPersonByEmail(@PathVariable String email){
        ResponseUserWithPersonDto user = userService.getUserWithPersonByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserWithPersonDto>builder()
                        .success(true)
                        .message("Lấy user với person thành công")
                        .data(user)
                        .build());
    }

    @PostMapping("/assign-role")
    public ResponseEntity<ApiResponse<ResponseUserDto>> assignRole(@Valid @RequestBody AssignRoleDto assignRoleDto){
        ResponseUserDto user = userService.assignRole(assignRoleDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserDto>builder()
                        .success(true)
                        .message("Gán role cho user thành công")
                        .data(user)
                        .build());
    }

    @PostMapping("/remove-role")
    public ResponseEntity<ApiResponse<ResponseUserDto>> removeRole(@Valid @RequestBody AssignRoleDto assignRoleDto){
        ResponseUserDto user = userService.removeRole(assignRoleDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseUserDto>builder()
                        .success(true)
                        .message("Xóa role khỏi user thành công")
                        .data(user)
                        .build());
    }
}
