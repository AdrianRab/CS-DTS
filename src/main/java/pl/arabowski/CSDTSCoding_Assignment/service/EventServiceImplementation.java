package pl.arabowski.CSDTSCoding_Assignment.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.arabowski.CSDTSCoding_Assignment.model.Event;
import pl.arabowski.CSDTSCoding_Assignment.repository.EventRepository;

@Service
public class EventServiceImplementation implements EventService {

	@Autowired
	private EventRepository eventRepo;

	@Override
	public void processLogs(String path) {
		Set<Event> events = readFromFile(path);
		for (Event e : events) {
			saveToDB(e);
		}

	}

	@Override
	public Event convertToObject(String jsonFile) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Event event = objectMapper.readValue(jsonFile, Event.class);
		return event;
	}

	@Override
	public long countDuration(String startTime, String finishTime) {
		BigInteger start = new BigInteger(startTime);
		BigInteger finish = new BigInteger(finishTime);
		BigInteger duration = finish.subtract(start);
		return duration.longValue();
	}
	
	@Override
	public Set<Event> readFromFile(String filePath){
		File file = new File(filePath);
		Map<String, Event> events = new HashMap<>();
		Set<Event>result = new HashSet<>();
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String log = scan.nextLine();
				if(!(log.equals(null) || log.equals(""))) {
					Event logEvent = convertToObject(log.trim());
					if(logEvent.getState().equals("FINISHED")) {
						logEvent.setFinishTime(logEvent.getTimestamp());
					}else if(logEvent.getState().equals("STARTED")) {
						logEvent.setStartTime(logEvent.getTimestamp());
					}
	 
					if (events.containsKey(logEvent.getId())) {
						if (logEvent.getState().equals("FINISHED")) {
							for (Entry<String, Event> e : events.entrySet()) {
								if (e.getKey().equals(logEvent.getId())) {
									Event EventFromMap = e.getValue();
									EventFromMap.setFinishTime((logEvent.getTimestamp()));
									e.setValue(EventFromMap);
								}
							}
						} else if (logEvent.getState().equals("STARTED")) {
							for (Entry<String, Event> e : events.entrySet()) {
								if (e.getKey().equals(logEvent.getId())) {
									Event EventFromMap = e.getValue();
									EventFromMap.setStartTime((logEvent.getTimestamp()));
									e.setValue(EventFromMap);
								}
							}
						}
					} else {
						events.put(logEvent.getId(),logEvent);
					}
				}
			
			}
			for (Entry<String, Event> e : events.entrySet()) {
				result.add(e.getValue());
			}
			
			for (Event e : result) {
				e.setDuration(countDuration(e.getStartTime(), e.getFinishTime()));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found, check the path.");
			e.printStackTrace();
		} catch (JsonParseException e) {
			System.out.println("Parse from String to Event object was not successful.");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void saveToDB(Event event) {
		if (event.getDuration() > 4) {
			event.setAlert(true);
		}
		eventRepo.save(event);
	}

}
