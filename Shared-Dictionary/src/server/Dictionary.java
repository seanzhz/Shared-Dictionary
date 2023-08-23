/*
 * 2022 S2 DS Assignment1
 * Creator: Hongzhuan Zhu
 * Student no: 1223535
 * Class name: Dictionary
 * Purpose: support function to add,remove,update,query to the real dictionary file
 * As well as to handle loading thing etc. 
 * 
 * */

package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Dictionary {

	private static HashMap<String, String> hash_words;
	private String filePath;
	private JSONObject jsonWords;

	public Dictionary(String filePath) {
		this.filePath = filePath;
		hash_words = new HashMap<>();

		if (filePath == null) {
			return;
		} else {
			loadFile();
		}

	}

	public void loadFile() {
		hash_words.clear();

		try {
			JSONParser jsonParser = new JSONParser();
			FileReader input = new FileReader(this.filePath);
			Object object = jsonParser.parse(input);
			this.jsonWords = (JSONObject) object;
			jsonToMap(this.jsonWords);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (Exception e) {
			System.out.println("Unexception Error");
		}
	}

	@SuppressWarnings("resource")
	public void updateFile() {
		try {
			mapToJson();
			FileWriter file = new FileWriter(this.filePath);
			file.write(this.jsonWords.toJSONString());
			file.flush();
		} catch (IOException e) {
			System.out.println("IO Exception Error");
		}

	}

	

	/**
	 * 
	 * Usefully function to converting between json and hashmap
	 * 
	 * */
	public void jsonToMap(JSONObject jObject) {

		for (Object o : jObject.keySet()) {
			String word = (String) o;
			String definition = jObject.get(word).toString();
			hash_words.put(word, definition);
		}

	}
	
	public void mapToJson() {
		this.jsonWords = new JSONObject(hash_words);
	}
	
	/**
	 * 
	 * 
	 * All functions to add, remove, query, update
	 * 
	 * 
	 * 
	 * */


	public String getMeaning(String word) {
		return hash_words.get(word);
	}

	public void addWord(String word, String meaning) {
		hash_words.put(word, meaning);
		updateFile();
	}

	public boolean wordExisting(String word) {
		return hash_words.get(word) != null;
	}

	public void removeWord(String word) {
		hash_words.remove(word);
		updateFile();
	}

	public void updateWord(String word, String meaning) {
		hash_words.replace(word, meaning);
		updateFile();
	}

}
