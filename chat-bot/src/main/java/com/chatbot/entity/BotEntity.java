package com.chatbot.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
public class BotEntity {

	    @Id
	    private String id;
	    private String text;
	    private double[] embedding;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public double[] getEmbedding() {
			return embedding;
		}
		public void setEmbedding(double[] embedding) {
			this.embedding = embedding;
		}
		public BotEntity(String id, String text, double[] embedding) {
			super();
			this.id = id;
			this.text = text;
			this.embedding = embedding;
		}
		public BotEntity() {
			super();
			// TODO Auto-generated constructor stub
		}

	    // Getters and setters
	
}
