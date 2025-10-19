package com.example.demo.service.impl;

import com.example.demo.repositories.PersonRepository;
import com.example.demo.domain.Person;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.domain.Project;
import com.example.demo.service.TaskService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.util.PagedUtil;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.dto.TaskDto;
import com.example.demo.specification.TaskSpecification;
import com.example.demo.specification.request.TaskSpecificationRequest;
import com.example.demo.domain.Task;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            PersonRepository personRepository,
            ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAllAsDto();
    }

    @Override
    public PagedResponse<TaskDto> getAllTasks(TaskSpecificationRequest taskSpecificationRequest, PaginationRequest paginationRequest) {
        Pageable pageable = paginationRequest.toPageable();
        Specification<Task> spec = TaskSpecification.filter(
                taskSpecificationRequest.getCompanyId(),
                taskSpecificationRequest.getProjectId(),
                taskSpecificationRequest.getPersonId(),
                taskSpecificationRequest.getStatus(),
                taskSpecificationRequest.getPriority(),
                taskSpecificationRequest.getName()
        );
        Page<Task> taskPage = taskRepository.findAll(spec, pageable);
        List<TaskDto> contentMapped = taskRepository.findAllAsDto();
        return PagedUtil.ToPagedResponse(taskPage, contentMapped);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));
    }

    @Override
    public List<TaskDto> getTasksByPersonId(Long personId) {
        return taskRepository.findByPersonIdAsDto(personId);
    }

    @Override
    public List<TaskDto> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectIdAsDto(projectId);
    }

    @Override
    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskDto.toEntity();
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        task.setProject(project);
        Person person = personRepository.findById(taskDto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        if (!project.getPersons().contains(person)) {
            throw new ResourceNotFoundException("Person không thuộc dự án này");
        }
        task.setPerson(person);
        Task saved = taskRepository.save(task);
        return TaskDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public TaskDto updateTask(TaskDto taskDto) {
        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));

        // Cập nhật các trường
        if (taskDto.getName() != null) {
            task.setName(taskDto.getName());
        }
        if (taskDto.getStartTime() != null) {
            task.setStartTime(taskDto.getStartTime());
        }
        if (taskDto.getEndTime() != null) {
            task.setEndTime(taskDto.getEndTime());
        }
        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getPriority() != null) {
            task.setPriority(taskDto.getPriority());
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }
        
        Task saved = taskRepository.save(task);
        return TaskDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public TaskDto deleteTask(Long id) {
        TaskDto dto = taskRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));
        
        taskRepository.deleteById(id);
        return dto;
    }

    @Override
    @Transactional
    public TaskDto assignPerson(Long taskId, Long personId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));

        if (task.getProject() == null) {
            throw new ResourceNotFoundException("Task chưa được gán dự án");
        }

        if (person.getCompany() == null) {
            throw new ResourceNotFoundException("Person chưa được gán công ty");
        }

        if (task.getProject().getCompany() == null) {
            throw new ResourceNotFoundException("Dự án chưa được gán công ty");
        }

        if (!task.getProject().getCompany().getId().equals(person.getCompany().getId())) {
            throw new DuplicateResourceException("Person không thuộc công ty của dự án");
        }

        if (!task.getProject().getPersons().contains(person)) {
            throw new ResourceNotFoundException("Person không thuộc dự án này");
        }

        if (task.getPerson() != null && task.getPerson().getId().equals(personId)) {
            throw new DuplicateResourceException("Task đã được gán cho person này");
        }

        task.setPerson(person);
        Task saved = taskRepository.save(task);
        return TaskDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public TaskDto assignProject(Long taskId, Long projectId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));

        if (task.getPerson() != null) {
            if (task.getPerson().getCompany() == null) {
                throw new ResourceNotFoundException("Person chưa được gán công ty");
            }

            if (project.getCompany() == null) {
                throw new ResourceNotFoundException("Dự án chưa được gán công ty");
            }

            if (!project.getCompany().getId().equals(task.getPerson().getCompany().getId())) {
                throw new DuplicateResourceException("Person không thuộc công ty của dự án");
            }

            if (!project.getPersons().contains(task.getPerson())) {
                throw new ResourceNotFoundException("Person không thuộc dự án này");
            }
        }

        if (task.getProject() != null && task.getProject().getId().equals(projectId)) {
            throw new DuplicateResourceException("Task đã được gán cho dự án này");
        }

        task.setProject(project);
        Task saved = taskRepository.save(task);
        return TaskDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public TaskDto removePerson(Long taskId, Long personId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));

        if (task.getPerson() == null || !task.getPerson().getId().equals(personId)) {
            throw new ResourceNotFoundException("Person không thuộc task này");
        }

        task.setPerson(null);
        Task saved = taskRepository.save(task);
        return TaskDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public TaskDto removeProject(Long taskId, Long projectId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));

        if (task.getProject() == null || !task.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException("Dự án không thuộc task này");
        }

        task.setProject(null);
        Task saved = taskRepository.save(task);
        return TaskDto.fromEntity(saved);
    }

    @Override
    public Resource exportTasksToExcel() {
        try {
            // Get all tasks without pagination
            List<TaskDto> taskDtos = taskRepository.findAllAsDto();

            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Tasks");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Project", "Description", "Start Time", "End Time", "Priority", "Status", "Person"};
            
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create data rows
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            int rowNum = 1;

            for (TaskDto task : taskDtos) {
                Row row = sheet.createRow(rowNum++);
                
                // Project
                row.createCell(0).setCellValue(task.getProjectName() != null ? task.getProjectName() : "");
                
                // Description
                row.createCell(1).setCellValue(task.getDescription() != null ? task.getDescription() : "");
                
                // Start Time
                row.createCell(2).setCellValue(task.getStartTime() != null ? task.getStartTime().format(dateFormatter) : "");
                
                // End Time
                row.createCell(3).setCellValue(task.getEndTime() != null ? task.getEndTime().format(dateFormatter) : "");
                
                // Priority
                row.createCell(4).setCellValue(task.getPriority() != null ? task.getPriority().toString() : "");
                
                // Status
                row.createCell(5).setCellValue(task.getStatus() != null ? task.getStatus().toString() : "");
                
                // Person
                row.createCell(6).setCellValue(task.getPersonName() != null ? task.getPersonName() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Error creating Excel file", e);
        }
    }
}
