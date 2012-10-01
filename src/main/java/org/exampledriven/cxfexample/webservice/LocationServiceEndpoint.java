package org.exampledriven.cxfexample.webservice;

import org.exampledriven.cxfexample.data.LocationData;
import org.exampledriven.cxfexample.exception.DuplicateLocationException;
import org.exampledriven.cxfexample.exception.LocationNotFoundException;
import org.exampledriven.cxfexample.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("locationService")
public class LocationServiceEndpoint implements LocationService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, LocationData> locations = new HashMap<String, LocationData>();

	@Autowired
	private Validator validator;
	
	//security handled by URL mapping in the xml 
	//@Secured("ROLE_RESTCLIENT")
	@Override
    public LocationData readLocation(String location) throws LocationNotFoundException {
		
		LocationData locationData = locations.get(location);
		
		if (locationData == null) {
			throw new LocationNotFoundException();
		}
		
		return locationData;
		
    }

	@Override
	public LocationData createLocation(LocationData locationData) throws DuplicateLocationException {
		
		if (locations.get(locationData.getLocation()) != null) {
			throw new DuplicateLocationException();
		}

		BeanPropertyBindingResult br = new BeanPropertyBindingResult(locationData, "locationData");
		validator.validate(locationData, br);
		if (br.hasErrors()) {
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError e : br.getFieldErrors()) {
				logger.debug(e.getDefaultMessage());
				errors.put(e.getField(), e.getDefaultMessage());
			}
			
			throw new ValidationException(errors);
		}

		setNewID(locationData);
		storeLocation(locationData);
		
		return locationData;
	}

	@Override
	public LocationData updateorCreateLocation(LocationData locationData) {

		if (locations.get(locationData.getLocation()) == null) {
			setNewID(locationData);
		}
		
		storeLocation(locationData);
		
		return locationData;
		
	}

	private void setNewID(LocationData locationData) {
		//setting the ID
		String id = UUID.randomUUID().toString();
		locationData.setId(id);
		
	}
	
	private void storeLocation(LocationData locationData) {
		
		locations.put(locationData.getLocation(), locationData);
		
	}

	@Override
	public Collection<LocationData> readAllLocations() {
		return locations.values();
	}

	@Override
	public void deleteLocation(String location) throws LocationNotFoundException {
		LocationData locationData = locations.get(location);
		
		if (locationData == null) {
			throw new LocationNotFoundException();
		}

		locations.remove(location);
		
	}

	@Override
	public void deleteAllLocation() {
		
		locations.clear();
		
	}
    
}