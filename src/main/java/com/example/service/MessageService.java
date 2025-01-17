package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessage(){
        return messageRepository.findAll();
    }

    public Message getMessage(Integer id){
        return messageRepository.findByMessageId(id);
    }

    public void deleteMessage(String id){
        messageRepository.deleteById(null);
    }

    public List<Message> getAllMessagesByUser(Integer accountId){
        return messageRepository.findAllByPostedBy(accountId);
    }
}
