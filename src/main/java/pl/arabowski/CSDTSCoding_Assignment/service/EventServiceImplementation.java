package pl.arabowski.CSDTSCoding_Assignment.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
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
		for(Event e : events) {
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
	public long countDuration(long startTime, long finishTime) {
		long duration = startTime - finishTime;
		return duration;
	}

	@Override
	public Set<Event> readFromFile(String filePath) {
		File file = new File(filePath);
		Set<Event> events = new HashSet<>();
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String log = scan.nextLine();
				Event logEvent = convertToObject(log.trim());

				if (events.contains(logEvent)) {
					if (logEvent.getState().equals("FINISHED")) {
						for (Event e : events) {
							if (e.getId().equals(logEvent.getId())) {
								e.setFinishTime(logEvent.getTimestamp());
							}
						}
					} else if (logEvent.getState().equals("STARTED")) {
						for (Event e : events) {
							if (e.getId().equals(logEvent.getId())) {
								e.setStartTime(logEvent.getTimestamp());
							}
						}
					}
				} else {
					events.add(logEvent);
				}
			}
			
			for(Event e : events) {
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
		
		return events;
	}

	@Override
	public void saveToDB(Event event) {
		if(event.getDuration()>4) {
			event.setAlert(true);
		}
		eventRepo.save(event);
	}


}
