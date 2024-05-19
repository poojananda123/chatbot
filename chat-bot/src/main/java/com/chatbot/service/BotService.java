package com.chatbot.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.reduce3.CosineDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.chatbot.entity.BotEntity;
import com.chatbot.repository.BotRepo;

import ai.djl.modality.nlp.preprocess.SimpleTokenizer;
import ai.djl.modality.nlp.preprocess.Tokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.FileSystemDocumentLoader;


//import dev.langchain4j.data.document.Document;
//
//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.memory.chat.MessageWindowChatMemory;
//
//import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
//import dev.langchain4j.service.AiServices;
//import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
//import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;


@Service
public class BotService {

@Autowired
private BotRepo botRepo;


@Autowired
   private MongoTemplate mongoTemplate;


private Tokenizer tokenizer;



	interface Assistant {

	    String chat(String userMessage);
	}


	    public List<BotEntity> findSimilarDocuments(double queryEmbedding) {
	        List<BotEntity> documents = mongoTemplate.findAll(BotEntity.class);
System.out.println("999999999995455"+documents.toString());
	        double SIMILARITY_THRESHOLD = 0.5;

	        // Calculate similarity scores
	        CosineDistance cosineDistance = new CosineDistance();
	        List<BotEntity> matchingDocuments=new ArrayList<BotEntity>(); 
	        for (BotEntity document : documents) {
	            String documentEmbedding = document.getVector();
	            double similarityScore = cosineSimilarity(Math.abs(queryEmbedding) , Double.parseDouble(documentEmbedding));
	            System.out.println(similarityScore+"similarityScore");
	            BotEntity b=new BotEntity();
	            b.setVector(Double.toString(similarityScore));
	            b.setId(document.getId());
	            b.setDocumentText(document.getDocumentText());
	            if (similarityScore >= SIMILARITY_THRESHOLD) {
	                matchingDocuments.add(b);
	            }
	            System.out.println("#####################"+matchingDocuments.toString());
	        }

	        // Sort documents by similarity score
//	        documents.sort((d1, d2) -> d1.compare(d2.getVector(), d1.getVector()));
	        matchingDocuments.sort((d1, d2) -> Double.compare( Double.parseDouble(d2.getVector()),  Double.parseDouble(d1.getVector())));


	        return matchingDocuments;
	    }
	    
	    private double cosineSimilarity(double vector1, double vector2) {
	        // Compute dot product
	        double dotProduct = 0;
//	        for (int i = 0; i < vector1.length; i++) {
//	            dotProduct += vector1[i] * vector2[i];
//	        }
	        dotProduct += vector1 * vector2;
	        // Compute magnitudes
	        double magnitude1 = 0;
	        double magnitude2 = 0;
//	        for (double v : vector1) {
	            magnitude1 += vector1 * vector1;
//	        }
//	        for (double v : vector2) {
	            magnitude2 += vector2 * vector2;
//	        }
	        magnitude1 = Math.sqrt(magnitude1);
	        magnitude2 = Math.sqrt(magnitude2);

	        // Compute cosine similarity
	        return dotProduct / (magnitude1 * magnitude2);
	    }
	
	
	 public String askQuestion(String question) throws UnsupportedEncodingException {
		 System.out.println("======================================================");
		 System.out.println("Question: " + question.toString());
//	        String answer = chain.execute(question);
//		 String answer = assistant.chat(question);
//		 System.out.println("Answer: " + answer);
//	        log.debug("======================================================");
		 String decodedString = URLDecoder.decode(question, "UTF-8");
//		 String[] parts = decodedString.split("=");
//         String value = parts.length > 1 ? parts[1] : null;
         JSONObject jsonObject = new JSONObject(decodedString);
         
         // Get the value of the "question" field
         String questionValue = jsonObject.getString("question");
		 System.out.println("Question: " + questionValue);
		 List<BotEntity> questionList=null;
	        Map<String, Double> vector = vectorize(questionValue);
	        System.out.println("||||||||||||||||************"+vector);
	        for(Map.Entry<String, Double> v: vector.entrySet()) {
	        	BotEntity docVector = new BotEntity();
		        docVector.setDocumentText(v.getKey());
		        docVector.setVector(v.getValue().toString());
//		        botRepo.save(docVector);
		        
		        questionList= findSimilarDocuments(Double.parseDouble(docVector.getVector()));
	        }
	        System.out.println("00000000000000000000"+questionList.toString());
	        for( BotEntity q: questionList) {
	        	questionValue+="\n"+q.getDocumentText();
	        	System.out.println("||||||||||||||||"+questionValue);
	        }
	       
	        return questionValue;    
	 }
	
	 public void pdfDocToVector(String fileName) throws IOException {
//		 String OPENAI_API_KEY = "sk-proj-zy9g6rgIg8Vnjd3VMJhvT3BlbkFJLkr7lAspFyAi0VzQSXKv";
//		 List<Document> documents = FileSystemDocumentLoader.loadDocuments("uploads");
//		  List<String> fileContents = new ArrayList<>();
	        
	        // Iterate over all files in the directory
//	        try {
//				Files.walk(Path.of("/uploads"))
//				     .forEach(file -> {
//				         try {
				             // Read the content of each file into a string
//				             byte[] bytes = Files.readAllBytes(file);
//	        	
//				             byte[] bytes = Files.readAllBytes(Path.of("uploads/XII-chemistry-science.pdf"));
		 System.out.println("*******************"+"uploads/"+fileName);
		 FileInputStream fileInputStream = new FileInputStream("uploads/"+fileName);
//
//				             String content = new String(bytes, StandardCharsets.UTF_8);
//				             
//				             // Add the content to the list
//				             fileContents.add(content);
//				           saveDocument(content);
//				             
//				         } catch (IOException e) {
//				             e.printStackTrace();
//				         }
//				     });
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
//		 InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
//		 EmbeddingStoreIngestor.ingest(documents, embeddingStore);
//
//			Assistant assistant = AiServices.builder(Assistant.class)
//			    .chatLanguageModel(OpenAiChatModel.withApiKey(OPENAI_API_KEY))
//			    .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
//			    .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
//			    .build();
//		
		 
		 
		 PDDocument document = PDDocument.load(fileInputStream);
	            PDFTextStripper stripper = new PDFTextStripper();
	            String text = stripper.getText(document);
	            System.out.println(text);
//	            saveDocument(text);
		        Map<String, Double> vector = vectorize(text);
		        for(Map.Entry<String, Double> v: vector.entrySet()) {
		        	BotEntity docVector = new BotEntity();
			        docVector.setDocumentText(v.getKey());
			        docVector.setVector(v.getValue().toString());
			        botRepo.save(docVector);	
		        
		        }
	            // Tokenize the text (you can use your tok
		 

	    }
	 
	  public Map<String, Double> vectorize(String document) {
	        // Tokenize the document
//	        List<String> tokens = tokenizer.tokenize(document);
	        String[] tokens = document.split("\n");

	        // Count term frequencies
	        Map<String, Integer> termFreqMap = new HashMap<>();
	        for (String token : tokens) {
	            termFreqMap.put(token, termFreqMap.getOrDefault(token, 0) + 1);
	        }

	        // Calculate TF-IDF
	        Map<String, Double> tfIdfMap = new HashMap<>();
	        for (Map.Entry<String, Integer> entry : termFreqMap.entrySet()) {
	            double tf = (double) entry.getValue() / tokens.length;
	            double idf = Math.log((double) tokens.length / (1 + termFreqMap.get(entry.getKey())));
	            tfIdfMap.put(entry.getKey(), tf * idf);
	        }

	        return tfIdfMap;
	    }
	
	 public void saveDocument(String fileName) throws IOException {
		 pdfDocToVector(fileName);
		 
//	        Map<String, Double> vector = vectorize(text);
//	        for(Map.Entry<String, Double> v: vector.entrySet()) {
//	        	BotEntity docVector = new BotEntity();
//		        docVector.setDocumentText(v.getKey());
//		        docVector.setVector(v.getValue().toString());
//		        botRepo.save(docVector);	
//	        
//	        }
	        
	    }
}
