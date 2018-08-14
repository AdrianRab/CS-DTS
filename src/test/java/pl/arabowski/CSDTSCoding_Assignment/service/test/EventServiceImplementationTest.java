package pl.arabowski.CSDTSCoding_Assignment.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pl.arabowski.CSDTSCoding_Assignment.model.Event;
import pl.arabowski.CSDTSCoding_Assignment.service.EventServiceImplementation;

public class EventServiceImplementationTest {


	private EventServiceImplementation eventServiceImpl = new EventServiceImplementation();
	
	@Test
	public void checkDurationCount() {
		//given
		long duration = 17; 
		String startTime = "1419377495195";
		String finishTime = "1419377495212";
		//when
		long result = eventServiceImpl.countDuration(startTime, finishTime);
		//then
		assertEquals("Numbers are not equal", duration, result);
	}
	
	@Test
	public void checkConversionToObject() throws JsonParseException, JsonMappingException, IOException {
		//given
		String json = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495212}";
		Event event = new Event();
		event.setId("scsmbstgra");
		event.setState("STARTED");
		event.setType("APPLICATION_LOG");
		event.setHost("12345");
		event.setTimestamp("1491377495212");
		//when
		Event result = eventServiceImpl.convertToObject(json);
		//then
		assertTrue(event.getId().equals(result.getId()));
		assertTrue(event.getTimestamp().equals(result.getTimestamp()));
	}
	
	@Test
	public void checkReadFromFile() {
		//given
		String filePath = "src/test/resources/file/inputTest.txt";
		Set<Event> events = new HashSet<>();
		Event event = new Event();
		event.setAlert(true);
		event.setId("scsmbstgra");
		event.setState("FINISHED");
		event.setType("APPLICATION_LOG");
		event.setHost("12345");
		event.setTimestamp("1491377495217");
		event.setDuration(5);
		event.setStartTime("1491377495212");
		event.setFinishTime("1491377495217");
		events.add(event);
		//when
		Set<Event> result = eventServiceImpl.readFromFile(filePath);
		//then
		assertTrue(events.size()==result.size());
		for(Event e: result) {
			assertTrue(e.getDuration()== event.getDuration());
			assertFalse(e.getState().equals(event.getState()));
			assertTrue(e.getState().equals("STARTED"));
			assertTrue(e.getStartTime().equals(event.getStartTime()));
		}
	} 
	
	@Test
	public void WithReversedOrder() {
		//given
		String filePath = "src/test/resources/file/inputTest2.txt";
		Set<Event> events = new HashSet<>();
		Event event = new Event();
		event.setAlert(true);
		event.setId("scsmbstgra");
		event.setState("FINISHED");
		event.setType("APPLICATION_LOG");
		event.setHost("12345");
		event.setTimestamp("1491377495217");
		event.setDuration(5);
		event.setStartTime("1491377495212");
		event.setFinishTime("1491377495217");
		events.add(event);
		//when
		Set<Event> result = eventServiceImpl.readFromFile(filePath);
		//then
		assertTrue(events.size()==result.size());
		for(Event e: result) {
			assertTrue(e.getType().equals(event.getType()));
			assertTrue(e.getState().equals(event.getState()));
			assertFalse(e.getState().equals("STARTED"));
			assertTrue(e.getStartTime().equals(event.getStartTime()));
		}
	} 
 
}



