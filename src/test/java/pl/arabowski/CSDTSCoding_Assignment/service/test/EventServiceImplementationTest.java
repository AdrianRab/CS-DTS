package pl.arabowski.CSDTSCoding_Assignment.service.test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import pl.arabowski.CSDTSCoding_Assignment.service.EventServiceImplementation;

public class EventServiceImplementationTest {


	private EventServiceImplementation eventServiceImpl = new EventServiceImplementation();
	
	@Test
	public void checkDurationCount() {
		//given
		long duration = 17; 
		BigInteger startTime = new BigInteger("1419377495195");
		BigInteger finishTime = new BigInteger("1419377495212");
		//when
		BigInteger result = eventServiceImpl.countDuration(startTime, finishTime);
		//then
		assertEquals("Numbers are not equal", duration, result.longValue());
		
	}

}
