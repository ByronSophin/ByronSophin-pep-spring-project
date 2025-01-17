package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    public Message findByMessageId(Integer id);
    public List<Message> findAllByPostedBy(Integer postedBy);
}
