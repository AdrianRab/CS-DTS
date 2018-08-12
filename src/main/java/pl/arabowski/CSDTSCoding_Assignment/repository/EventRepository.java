package pl.arabowski.CSDTSCoding_Assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.arabowski.CSDTSCoding_Assignment.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	Event findById(long id);
}
