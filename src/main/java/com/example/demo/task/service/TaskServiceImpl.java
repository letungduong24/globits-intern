package com.example.demo.task.service;

import com.example.demo.company.entity.Company;
import com.example.demo.person.PersonRepository;
import com.example.demo.person.entity.Person;
import com.example.demo.project.ProjectRepository;
import com.example.demo.project.entity.Project;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.util.PagedUtil;
import com.example.demo.task.TaskRepository;
import com.example.demo.task.dto.TaskMapper;
import com.example.demo.task.dto.request.CreateTaskDto;
import com.example.demo.task.dto.response.TaskDto;
import com.example.demo.task.specification.TaskSpecification;
import com.example.demo.task.specification.TaskSpecificationRequest;
import com.example.demo.task.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
}
