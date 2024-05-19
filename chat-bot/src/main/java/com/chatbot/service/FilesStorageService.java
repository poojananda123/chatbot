package com.chatbot.service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatbot.repository.BotRepo;

@Service
public class FilesStorageService {

	@Autowired
	private BotRepo botRepo;
	
	@Autowired
	private BotService botService;
	
//	private final ParagraphVectors paragraphVectors;
	
	  private final Path root = Paths.get("./uploads");


	  public void init() {
	    try {
	      Files.createDirectories(root);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not initialize folder for upload!");
	    }
	  }
	  
	public void save(MultipartFile file) {
	    try {
//	    	init();
	      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
	      botService.saveDocument(file.getOriginalFilename());
	      
	    } catch (Exception e) {
	      if (e instanceof FileAlreadyExistsException) {
	        throw new RuntimeException("A file of that name already exists.");
	      }

	      throw new RuntimeException(e.getMessage());
	    }
	  }
}
