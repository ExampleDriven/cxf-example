package org.exampledriven.cxfexample.client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class LocationClientTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Test
	public void getLocation() {
		
		String city = "Budapest";
		
//		LocationData locationData = new LocationClient().getLocation(city);
//		logger.debug("", locationData);
//		
//		assertEquals(city, locationData.getLocation());
		
	}
	
}
