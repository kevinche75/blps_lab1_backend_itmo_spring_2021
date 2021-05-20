package com.blps.airlineservice.service;

import com.blps.airlineservice.model.Task;
import com.blps.airlineservice.model.User;
import com.blps.airlineservice.repository.TaskRepository;
import com.blps.airlineservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@EnableKafka
@Service
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @KafkaListener(topics="aviasales.tasks.add")
    public void msgListener(ConsumerRecord<Long, String> record) throws JsonProcessingException {
        Task task = new ObjectMapper().readValue(record.value(), Task.class);
        System.out.println(task);
        taskRepository.saveAndFlush(task);
    }

    public void saveTask(Task task){
        taskRepository.saveAndFlush(task);
    }

    public void deleteTask(long id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
        }
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
}
