package pl.arabowski.CSDTSCoding_Assignment.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pl.arabowski.CSDTSCoding_Assignment.model.Event;

public interface EventService {
	
	public Event convertToObject(String jsonFile) throws JsonParseException, JsonMappingException, IOException;
	
	public long countDuration(String startTime, String finishTime);
	
	public Iterable<?> readFromFile (String filePath);
	
	public void saveToDB(Event event);
	
	public void processLogs(String path);
	
}
