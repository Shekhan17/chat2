package com.chat.repos;

import com.chat.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findByTextMessageIgnoreCase(String textMessage);
}
