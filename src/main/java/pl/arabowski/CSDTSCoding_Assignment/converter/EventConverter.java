package pl.arabowski.CSDTSCoding_Assignment.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.arabowski.CSDTSCoding_Assignment.model.Event;
import pl.arabowski.CSDTSCoding_Assignment.repository.EventRepository;

@Component
public class EventConverter implements Converter<String, Event> {

	@Autowired
	EventRepository eventRepo;

	@Override
	public Event convert(String id) {
		return eventRepo.findById(Long.parseLong(id));
	}

}
