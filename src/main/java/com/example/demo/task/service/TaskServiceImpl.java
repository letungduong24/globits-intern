package com.example.demo.task.service;

import com.example.demo.person.PersonRepository;
import com.example.demo.person.entity.Person;
import com.example.demo.project.ProjectRepository;
import com.example.demo.project.entity.Project;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.util.PagedUtil;
import com.example.demo.task.TaskRepository;
import com.example.demo.task.dto.TaskMapper;
import com.example.demo.task.dto.request.*;
import com.example.demo.task.dto.response.TaskDto;
import com.example.demo.task.specification.TaskSpecification;
import com.example.demo.task.specification.TaskSpecificationRequest;
import com.example.demo.task.entity.Task;
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
    private final TaskMapper taskMapper;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            TaskMapper taskMapper,
            PersonRepository personRepository,
            ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskMapper.toDTOs((taskRepository.findAll()));
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
        List<TaskDto> contentMapped = taskMapper.toDTOs(taskPage.getContent());
        return PagedUtil.ToPagedResponse(taskPage, contentMapped);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));
        return taskMapper.toDTO(task);
    }

    @Override
    public List<TaskDto> getTasksByPersonId(Long personId) {
        List<Task> tasks = taskRepository.findByPersonId(personId);
        return taskMapper.toDTOs(tasks);
    }

    @Override
    public List<TaskDto> getTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return taskMapper.toDTOs(tasks);
    }

    @Override
    @Transactional
    public TaskDto createTask(CreateTaskDto createTaskDto) {
        Task task =  taskMapper.toEntity(createTaskDto);
        Project project = projectRepository.findById(createTaskDto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        task.setProject(project);
        Person person = personRepository.findById(createTaskDto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));
        if (!project.getPersons().contains(person)) {
            throw new ResourceNotFoundException("Person không thuộc dự án này");
        }
        task.setPerson(person);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public TaskDto updateTask(UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findById(updateTaskDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));

        taskMapper.updateEntity(task, updateTaskDto);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public TaskDto deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));

        TaskDto dto = taskMapper.toDTO(task);
        taskRepository.delete(task);
        return dto;
    }

    @Override
    @Transactional
    public TaskDto assignPerson(AssignPerson dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));
        Person person = personRepository.findById(dto.getPersonId())
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

        if (task.getPerson() != null && task.getPerson().getId().equals(dto.getPersonId())) {
            throw new DuplicateResourceException("Task đã được gán cho person này");
        }

        task.setPerson(person);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public TaskDto assignProject(AssignProject dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));
        Project project = projectRepository.findById(dto.getProjectId())
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

        if (task.getProject() != null && task.getProject().getId().equals(dto.getProjectId())) {
            throw new DuplicateResourceException("Task đã được gán cho dự án này");
        }

        task.setProject(project);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public TaskDto removePerson(AssignPerson dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));

        if (task.getPerson() == null || !task.getPerson().getId().equals(dto.getPersonId())) {
            throw new ResourceNotFoundException("Person không thuộc task này");
        }

        task.setPerson(null);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public TaskDto removeProject(AssignProject dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy task"));

        if (task.getProject() == null || !task.getProject().getId().equals(dto.getProjectId())) {
            throw new ResourceNotFoundException("Dự án không thuộc task này");
        }

        task.setProject(null);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    @Override
    public Resource exportTasksToExcel() {
        try {
            // Get all tasks without pagination
            List<Task> tasks = taskRepository.findAll();
            List<TaskDto> taskDtos = taskMapper.toDTOs(tasks);

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
                row.createCell(0).setCellValue(task.getProject() != null ? task.getProject().getName() : "");
                
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
                row.createCell(6).setCellValue(task.getPerson() != null ? task.getPerson().getFullName() : "");
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
