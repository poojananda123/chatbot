package com.chatbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.repository.BotRepo;

import dev.langchain4j.data.document.Document;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;


@Service
public class BotService {

@Autowired
private BotRepo botRepo;

	interface Assistant {

	    String chat(String userMessage);
	}

	
	 public String askQuestion(String question) {
//		 String OPENAI_API_KEY = "sk-proj-zy9g6rgIg8Vnjd3VMJhvT3BlbkFJLkr7lAspFyAi0VzQSXKv";
//		 List<Document> documents = FileSystemDocumentLoader.loadDocuments("uploads");
//		 InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
//		 EmbeddingStoreIngestor.ingest(documents, embeddingStore);
//
//			Assistant assistant = AiServices.builder(Assistant.class)
//			    .chatLanguageModel(OpenAiChatModel.withApiKey(OPENAI_API_KEY))
//			    .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
//			    .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
//			    .build();
//			
		 System.out.println("======================================================");
		 System.out.println("Question: " + question);
//	        String answer = chain.execute(question);
//		 String answer = assistant.chat(question);
//		 System.out.println("Answer: " + answer);
//	        log.debug("======================================================");
	        return "Application under maintainance! please try after sometime";
	    }
}
