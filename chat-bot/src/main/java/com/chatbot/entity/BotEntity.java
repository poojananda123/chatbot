package com.chatbot.entity;

import java.util.Map;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatbot")
public class BotEntity {

	    @Id
	    private String id;
	    private String documentText;
	    private String vector;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDocumentText() {
			return documentText;
		}
		public void setDocumentText(String documentText) {
			this.documentText = documentText;
		}
		public String getVector() {
			return vector;
		}
		public void setVector(String vector) {
			this.vector = vector;
		}
		public BotEntity(String id, String documentText, String vector) {
			super();
			this.id = id;
			this.documentText = documentText;
			this.vector = vector;
		}
		public BotEntity() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "BotEntity [id=" + id + ", documentText=" + documentText + ", vector=" + vector + "]";
		}
		
	    
	
}
