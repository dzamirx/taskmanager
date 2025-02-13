package com.treat.taskmanager.service;

import com.treat.taskmanager.entity.Task;
import com.treat.taskmanager.entity.TaskDto;
import com.treat.taskmanager.repository.TaskRepository;
import com.treat.taskmanager.utils.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskDto addTask(TaskDto taskDto) {
        Task task = taskRepository.save(TaskMapper.toEntity(taskDto));
        return TaskMapper.toDto(task);
    }

    public TaskDto updateTask(String id, TaskDto taskDto) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setPriority(taskDto.getPriority());
                    existingTask.setDependencies(taskDto.getDependencies());
                    return TaskMapper.toDto(taskRepository.save(existingTask));
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<TaskDto> getTasks(Integer priority, Boolean overdue, String sortBy, String order) {
        return taskRepository.findAll().stream()
                .filter(task -> priority == null || task.getPriority().equals(priority))
                .filter(task -> overdue == null || (overdue && task.getDeadline().isBefore(java.time.LocalDate.now())))
                .sorted((t1, t2) -> {
                    int comparison = 0;
                    if ("priority".equals(sortBy)) {
                        comparison = t1.getPriority().compareTo(t2.getPriority());
                    } else if ("deadline".equals(sortBy)) {
                        comparison = t1.getDeadline().compareTo(t2.getDeadline());
                    }
                    return "desc".equals(order) ? -comparison : comparison;
                })
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> findExecutableTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getDependencies().isEmpty())
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public void markTaskAsComplete(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.getDependencies().stream().anyMatch(depId -> taskRepository.findById(depId).isEmpty())) {
            throw new IllegalStateException("Cannot complete task with unresolved dependencies");
        }
        task.setCompleted(true);
        taskRepository.save(task);
    }

}
