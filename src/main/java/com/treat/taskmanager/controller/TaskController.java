package com.treat.taskmanager.controller;

import com.treat.taskmanager.entity.TaskDto;
import com.treat.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasksmanager")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.addTask(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDto));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(@RequestParam(required = false) Integer priority, @RequestParam(required = false) Boolean overdue, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String order) {
        return ResponseEntity.ok(taskService.getTasks(priority, overdue, sortBy, order));
    }

    @GetMapping("/executable")
    public ResponseEntity<List<TaskDto>> findExecutableTasks() {
        return ResponseEntity.ok(taskService.findExecutableTasks());
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> markTaskAsComplete(@PathVariable String id) {
        taskService.markTaskAsComplete(id);
        return ResponseEntity.noContent().build();
    }


}
