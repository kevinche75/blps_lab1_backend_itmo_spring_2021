package com.blps.airlineservice.service;

import com.blps.airlineservice.model.Task;
import com.blps.airlineservice.model.User;
import com.blps.airlineservice.repository.TaskRepository;
import com.blps.airlineservice.repository.UserRepository;
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
    public void msgListener(String msg){
        System.out.println(msg);
    }

    public void createTask(String login, Date startStamp, Date endStamp, String placeFrom, String placeTo){
        Task task = new Task();
        Optional<User> userOptional = userRepository.findById(login);
        if (userOptional.isPresent()) {
            task.setCreator(userOptional.get());
            task.setStartStamp(startStamp);
            task.setEndStamp(endStamp);
            task.setPlaceFrom(placeFrom);
            task.setPlaceTo(placeTo);
            taskRepository.saveAndFlush(task);
        }
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
