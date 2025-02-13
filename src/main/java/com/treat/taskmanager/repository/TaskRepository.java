package com.treat.taskmanager.repository;

import com.treat.taskmanager.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
