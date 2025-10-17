# Hướng dẫn Seed Data

## Tổng quan
File seed data này sẽ tự động tạo dữ liệu mẫu khi khởi động ứng dụng Spring Boot.

## Cấu trúc dữ liệu được tạo

### 1. Quốc gia (Countries)
- Việt Nam (VN)
- Hoa Kỳ (US) 
- Nhật Bản (JP)

### 2. Vai trò (Roles)
- ADMIN: Quản trị viên hệ thống
- MANAGER: Quản lý dự án
- DEVELOPER: Lập trình viên
- DESIGNER: Thiết kế giao diện
- TESTER: Kiểm thử viên

### 3. Công ty (Companies)
- Công ty TNHH Globits Technology (GLOBITS)
- Tập đoàn FPT (FPT)
- Tập đoàn Công nghiệp - Viễn thông Quân đội (VIETTEL)

### 4. Phòng ban (Departments)
- Phòng Kỹ thuật (ENG)
- Phòng Nhân sự (HR)
- Phòng Marketing (MKT)

### 5. Người dùng (Users)
- admin@globits.com (mật khẩu: 123456)
- manager@globits.com (mật khẩu: 123456)
- developer@globits.com (mật khẩu: 123456)
- designer@globits.com (mật khẩu: 123456)
- tester@globits.com (mật khẩu: 123456)

### 6. Người (Persons)
- Nguyễn Văn Admin
- Trần Thị Manager
- Lê Văn Developer
- Phạm Thị Designer
- Hoàng Văn Tester

### 7. Dự án (Projects)
- Nền tảng Thương mại Điện tử (ECOM001)
- Ứng dụng Ngân hàng Di động (BANK001)
- Hệ thống Chatbot AI (AI001)

### 8. Nhiệm vụ (Tasks)
- Thiết kế Cơ sở Dữ liệu
- Phát triển API Backend
- Thiết kế Giao diện
- Kiểm thử Bảo mật

## Cách hoạt động

1. **DataLoader**: Component tự động chạy khi ứng dụng khởi động
2. **SeedDataService**: Service chứa logic seed dữ liệu
3. **Kiểm tra dữ liệu**: Chỉ seed khi chưa có dữ liệu trong database

## Lưu ý

- Dữ liệu chỉ được tạo một lần khi database trống
- Tất cả mật khẩu mặc định là: `123456`
- Dữ liệu được tạo bằng tiếng Việt
- Các entity có quan hệ với nhau được tạo theo đúng thứ tự

## Chạy ứng dụng

```bash
mvn spring-boot:run
```

Dữ liệu sẽ được tự động tạo khi ứng dụng khởi động lần đầu.
