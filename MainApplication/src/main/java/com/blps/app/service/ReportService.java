package com.blps.app.service;

import com.blps.app.model.Task;
import com.blps.app.model.User;
import com.blps.app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@EnableKafka
@Service
public class ReportService {

    private KafkaTemplate<Long, Task> template;
    private UserRepository userRepository;

    public ReportService(KafkaTemplate<Long, Task> template, UserRepository userRepository){
        this.template = template;
        this.userRepository = userRepository;
    }

    public boolean createTask(String userId, Date startDate, Date endDate, String from, String to){
        Task task = new Task();
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            return false;
        }
        task.setCreator(user.get());
        task.setPlaceFrom(from);
        task.setPlaceTo(to);
        task.setStartStamp(startDate);
        task.setEndStamp(endDate);
        template.send("aviasales.tasks.add", 0L, task);
        return true;
    }

    @KafkaListener(topics="aviasales.tasks.response")
    public void msgListener(String message){
        System.out.println(message);
    }
}
