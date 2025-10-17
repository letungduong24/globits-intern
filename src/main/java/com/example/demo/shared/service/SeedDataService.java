package com.example.demo.shared.service;

import com.example.demo.company.entity.Company;
import com.example.demo.company.CompanyRepository;
import com.example.demo.country.entity.Country;
import com.example.demo.country.CountryRepository;
import com.example.demo.department.entity.Department;
import com.example.demo.department.DepartmentRepository;
import com.example.demo.person.entity.Person;
import com.example.demo.person.PersonRepository;
import com.example.demo.project.entity.Project;
import com.example.demo.project.ProjectRepository;
import com.example.demo.role.entity.Role;
import com.example.demo.role.RoleRepository;
import com.example.demo.task.entity.Task;
import com.example.demo.task.TaskRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeedDataService {

    private final CountryRepository countryRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void seedData() {
        log.info("Bắt đầu seed dữ liệu...");
        
        // Kiểm tra xem đã có dữ liệu chưa
        if (userRepository.count() > 0) {
            log.info("Dữ liệu đã tồn tại, bỏ qua seed data");
            return;
        }

        try {
            // Seed Countries
            seedCountries();
            
            // Seed Roles
            seedRoles();
            
            // Seed Companies
            seedCompanies();
            
            // Seed Departments
            seedDepartments();
            
            // Seed Users
            seedUsers();
            
            // Seed Persons
            seedPersons();
            
            // Seed Projects
            seedProjects();
            
            // Seed Tasks
            seedTasks();
            
            log.info("Seed dữ liệu hoàn thành thành công!");
            
        } catch (Exception e) {
            log.error("Lỗi khi seed dữ liệu: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể seed dữ liệu", e);
        }
    }

    private void seedCountries() {
        log.info("Đang seed dữ liệu quốc gia...");
        
        Country vietnam = Country.builder()
                .name("Việt Nam")
                .description("Cộng hòa Xã hội Chủ nghĩa Việt Nam")
                .code("VN")
                .build();
        
        Country usa = Country.builder()
                .name("Hoa Kỳ")
                .description("Hợp chủng quốc Hoa Kỳ")
                .code("US")
                .build();
        
        Country japan = Country.builder()
                .name("Nhật Bản")
                .description("Nhật Bản")
                .code("JP")
                .build();
        
        countryRepository.save(vietnam);
        countryRepository.save(usa);
        countryRepository.save(japan);
    }

    private void seedRoles() {
        log.info("Đang seed dữ liệu vai trò...");
        
        Role admin = Role.builder()
                .role("ADMIN")
                .description("Quản trị viên hệ thống với quyền truy cập đầy đủ")
                .build();
        
        Role manager = Role.builder()
                .role("MANAGER")
                .description("Quản lý dự án với quyền quản lý")
                .build();
        
        Role developer = Role.builder()
                .role("DEVELOPER")
                .description("Lập trình viên phần mềm")
                .build();
        
        Role designer = Role.builder()
                .role("DESIGNER")
                .description("Thiết kế giao diện người dùng")
                .build();
        
        Role tester = Role.builder()
                .role("TESTER")
                .description("Kiểm thử viên chất lượng")
                .build();
        
        roleRepository.save(admin);
        roleRepository.save(manager);
        roleRepository.save(developer);
        roleRepository.save(designer);
        roleRepository.save(tester);
    }

    private void seedCompanies() {
        log.info("Đang seed dữ liệu công ty...");
        
        Company globits = Company.builder()
                .name("Công ty TNHH Globits Technology")
                .address("123 Đường Công nghệ, Quận 1, TP.HCM")
                .code("GLOBITS")
                .build();
        
        Company fpt = Company.builder()
                .name("Tập đoàn FPT")
                .address("17 Duy Tân, Cầu Giấy, Hà Nội")
                .code("FPT")
                .build();
        
        Company viettel = Company.builder()
                .name("Tập đoàn Công nghiệp - Viễn thông Quân đội")
                .address("1 Giang Văn Minh, Ba Đình, Hà Nội")
                .code("VIETTEL")
                .build();
        
        companyRepository.save(globits);
        companyRepository.save(fpt);
        companyRepository.save(viettel);
    }

    private void seedDepartments() {
        log.info("Đang seed dữ liệu phòng ban...");
        
        Company globits = companyRepository.findByCode("GLOBITS").orElse(null);
        Company fpt = companyRepository.findByCode("FPT").orElse(null);
        
        if (globits != null) {
            Department engineering = Department.builder()
                    .name("Phòng Kỹ thuật")
                    .code("ENG")
                    .company(globits)
                    .build();
            
            Department hr = Department.builder()
                    .name("Phòng Nhân sự")
                    .code("HR")
                    .company(globits)
                    .build();
            
            Department marketing = Department.builder()
                    .name("Phòng Marketing")
                    .code("MKT")
                    .company(globits)
                    .build();
            
            departmentRepository.save(engineering);
            departmentRepository.save(hr);
            departmentRepository.save(marketing);
        }
        
        if (fpt != null) {
            Department fptEng = Department.builder()
                    .name("Phòng Kỹ thuật FPT")
                    .code("FPT_ENG")
                    .company(fpt)
                    .build();
            
            departmentRepository.save(fptEng);
        }
    }

    private void seedUsers() {
        log.info("Đang seed dữ liệu người dùng...");
        
        String password = "123456";
        
        User admin = User.builder()
                .email("admin@globits.com")
                .password(password)
                .isActive(true)
                .build();
        
        User manager = User.builder()
                .email("manager@globits.com")
                .password(password)
                .isActive(true)
                .build();
        
        User developer = User.builder()
                .email("developer@globits.com")
                .password(password)
                .isActive(true)
                .build();
        
        User designer = User.builder()
                .email("designer@globits.com")
                .password(password)
                .isActive(true)
                .build();
        
        User tester = User.builder()
                .email("tester@globits.com")
                .password(password)
                .isActive(true)
                .build();
        
        userRepository.save(admin);
        userRepository.save(manager);
        userRepository.save(developer);
        userRepository.save(designer);
        userRepository.save(tester);
    }

    private void seedPersons() {
        log.info("Đang seed dữ liệu người...");
        
        Company globits = companyRepository.findByCode("GLOBITS").orElse(null);
        User admin = userRepository.findByEmail("admin@globits.com").orElse(null);
        User manager = userRepository.findByEmail("manager@globits.com").orElse(null);
        User developer = userRepository.findByEmail("developer@globits.com").orElse(null);
        User designer = userRepository.findByEmail("designer@globits.com").orElse(null);
        User tester = userRepository.findByEmail("tester@globits.com").orElse(null);
        
        if (admin != null && globits != null) {
            Person adminPerson = Person.builder()
                    .fullName("Nguyễn Văn Admin")
                    .gender("Nam")
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .phoneNumber("+84 123 456 789")
                    .address("123 Đường Admin, Quận 1, TP.HCM")
                    .user(admin)
                    .company(globits)
                    .build();
            
            personRepository.save(adminPerson);
        }
        
        if (manager != null && globits != null) {
            Person managerPerson = Person.builder()
                    .fullName("Trần Thị Manager")
                    .gender("Nữ")
                    .birthDate(LocalDate.of(1985, 5, 15))
                    .phoneNumber("+84 234 567 890")
                    .address("456 Đường Manager, Quận 2, TP.HCM")
                    .user(manager)
                    .company(globits)
                    .build();
            
            personRepository.save(managerPerson);
        }
        
        if (developer != null && globits != null) {
            Person developerPerson = Person.builder()
                    .fullName("Lê Văn Developer")
                    .gender("Nam")
                    .birthDate(LocalDate.of(1992, 3, 20))
                    .phoneNumber("+84 345 678 901")
                    .address("789 Đường Developer, Quận 3, TP.HCM")
                    .user(developer)
                    .company(globits)
                    .build();
            
            personRepository.save(developerPerson);
        }
        
        if (designer != null && globits != null) {
            Person designerPerson = Person.builder()
                    .fullName("Phạm Thị Designer")
                    .gender("Nữ")
                    .birthDate(LocalDate.of(1988, 11, 25))
                    .phoneNumber("+84 456 789 012")
                    .address("321 Đường Designer, Quận 4, TP.HCM")
                    .user(designer)
                    .company(globits)
                    .build();
            
            personRepository.save(designerPerson);
        }
        
        if (tester != null && globits != null) {
            Person testerPerson = Person.builder()
                    .fullName("Hoàng Văn Tester")
                    .gender("Nam")
                    .birthDate(LocalDate.of(1991, 9, 12))
                    .phoneNumber("+84 567 890 123")
                    .address("654 Đường Tester, Quận 5, TP.HCM")
                    .user(tester)
                    .company(globits)
                    .build();
            
            personRepository.save(testerPerson);
        }
    }

    private void seedProjects() {
        log.info("Đang seed dữ liệu dự án...");
        
        Company globits = companyRepository.findByCode("GLOBITS").orElse(null);
        
        if (globits != null) {
            Project ecommerce = Project.builder()
                    .name("Nền tảng Thương mại Điện tử")
                    .code("ECOM001")
                    .description("Giải pháp thương mại điện tử toàn diện với các tính năng hiện đại")
                    .company(globits)
                    .build();
            
            Project banking = Project.builder()
                    .name("Ứng dụng Ngân hàng Di động")
                    .code("BANK001")
                    .description("Ứng dụng ngân hàng di động bảo mật cho các giao dịch tài chính")
                    .company(globits)
                    .build();
            
            Project chatbot = Project.builder()
                    .name("Hệ thống Chatbot AI")
                    .code("AI001")
                    .description("Chatbot thông minh cho hỗ trợ khách hàng")
                    .company(globits)
                    .build();
            
            projectRepository.save(ecommerce);
            projectRepository.save(banking);
            projectRepository.save(chatbot);
        }
    }

    private void seedTasks() {
        log.info("Đang seed dữ liệu nhiệm vụ...");
        
        Person developer = personRepository.findByFullName("Lê Văn Developer").orElse(null);
        Person designer = personRepository.findByFullName("Phạm Thị Designer").orElse(null);
        Person tester = personRepository.findByFullName("Hoàng Văn Tester").orElse(null);
        
        Project ecommerce = projectRepository.findByCode("ECOM001").orElse(null);
        Project banking = projectRepository.findByCode("BANK001").orElse(null);
        
        if (developer != null && ecommerce != null) {
            Task task1 = Task.builder()
                    .name("Thiết kế Cơ sở Dữ liệu")
                    .startTime(LocalDate.of(2024, 1, 1))
                    .endTime(LocalDate.of(2024, 1, 15))
                    .description("Thiết kế lược đồ cơ sở dữ liệu cho nền tảng thương mại điện tử")
                    .priority(1)
                    .status(1)
                    .person(developer)
                    .project(ecommerce)
                    .build();
            
            Task task2 = Task.builder()
                    .name("Phát triển API Backend")
                    .startTime(LocalDate.of(2024, 1, 15))
                    .endTime(LocalDate.of(2024, 3, 15))
                    .description("Tạo các API RESTful cho nền tảng thương mại điện tử")
                    .priority(1)
                    .status(2)
                    .person(developer)
                    .project(ecommerce)
                    .build();
            
            taskRepository.save(task1);
            taskRepository.save(task2);
        }
        
        if (designer != null && ecommerce != null) {
            Task task3 = Task.builder()
                    .name("Thiết kế Giao diện")
                    .startTime(LocalDate.of(2024, 1, 1))
                    .endTime(LocalDate.of(2024, 1, 30))
                    .description("Thiết kế giao diện người dùng và trải nghiệm người dùng")
                    .priority(2)
                    .status(3)
                    .person(designer)
                    .project(ecommerce)
                    .build();
            
            taskRepository.save(task3);
        }
        
        if (tester != null && banking != null) {
            Task task4 = Task.builder()
                    .name("Kiểm thử Bảo mật")
                    .startTime(LocalDate.of(2024, 1, 20))
                    .endTime(LocalDate.of(2024, 2, 20))
                    .description("Thực hiện các biện pháp bảo mật cho ứng dụng ngân hàng")
                    .priority(1)
                    .status(2)
                    .person(tester)
                    .project(banking)
                    .build();
            
            taskRepository.save(task4);
        }
    }
}
