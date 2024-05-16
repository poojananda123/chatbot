package com.chatbot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chatbot.entity.BotEntity;

@Repository
public interface BotRepo extends MongoRepository<BotEntity,String> {

}
