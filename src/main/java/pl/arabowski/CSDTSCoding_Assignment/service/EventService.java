package pl.arabowski.CSDTSCoding_Assignment.service;

import java.io.IOException;
import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pl.arabowski.CSDTSCoding_Assignment.model.Event;

public interface EventService {
	
	public Event convertToObject(String jsonFile) throws JsonParseException, JsonMappingException, IOException;
	
	public BigInteger countDuration(BigInteger startTime, BigInteger finishTime);
	
	public Iterable<?> readFromFile (String filePath);
	
	public void saveToDB(Event event);
	
	public void processLogs(String path);
	
}
