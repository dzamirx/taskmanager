package com.treat.taskmanager.utils;

import com.treat.taskmanager.entity.Task;
import com.treat.taskmanager.entity.TaskDto;

public class TaskMapper {

    public static TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getDeadline(),
                task.getDependencies(),
                task.isCompleted()
        );
    }

    public static Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }
        return new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getPriority(),
                taskDto.getDeadline(),
                taskDto.getDependencies(),
                taskDto.isCompleted()
        );
    }
}