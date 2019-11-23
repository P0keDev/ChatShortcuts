package dev.p0ke.chatshortcuts.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ShortcutHandler {
	
	private File shortcutFile;
	private HashMap<String, String> shortcuts;
	private Gson gson;
	
	public ShortcutHandler(File file) {
		shortcutFile = file;
		shortcuts = new HashMap<String, String>();
		gson = new Gson();
	}
	
	public void loadShortcuts() {
		if(shortcutFile.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(shortcutFile));
				shortcuts = gson.fromJson(br.readLine(), new TypeToken<HashMap<String, String>>() {}.getType());
				br.close();
			} catch (Exception e) {
				System.out.println("Error loading shortcuts file: ");
				e.printStackTrace();
			}
		} else {
			saveShortcuts();
		}
	}
	
	public void saveShortcuts() {
		try {
			shortcutFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(shortcutFile));
			bw.write(gson.toJson(shortcuts));
			bw.close();
		} catch (Exception e) {
			System.out.println("Error saving shortcuts file: ");
			e.printStackTrace();
		}
	}
	
	public void clearShortcuts() {
		shortcuts.clear();
		saveShortcuts();
	}
	
	public void addShortcut(String key, String message) {
		shortcuts.put(key, message);
		saveShortcuts();
	}
	
	public HashMap<String, String> getShortcuts(){
		return shortcuts;
	}
	
	public String transformMessage(String message) {
		String transformedMessage = message;
		for(Entry<String, String> e : shortcuts.entrySet()) {
			transformedMessage = transformedMessage.replace(e.getKey(), e.getValue());
		}
		return transformedMessage;
	}

}
