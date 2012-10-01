package org.exampledriven.cxfexample.web;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.exampledriven.cxfexample.data.LocationData;
import org.exampledriven.cxfexample.exception.DuplicateLocationException;
import org.exampledriven.cxfexample.exception.LocationNotFoundException;
import org.exampledriven.cxfexample.exception.ValidationException;
import org.exampledriven.cxfexample.webservice.LocationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/")
@SessionAttributes("locationData")
public class LocationClientController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String applicationURI;

	private LocationClient locationClientSOAP;
	private LocationClient locationClientREST;
	
	private LocationClient getLocationClient(HttpServletRequest request, LocationClient.CLIENT_TYPE clientType) {
		
		if (clientType.equals(LocationClient.CLIENT_TYPE.SOAP)) {
			if (locationClientSOAP == null) {
				locationClientSOAP = new LocationClient(getApplicationURI(request), LocationClient.CLIENT_TYPE.SOAP);
			}
			
			return locationClientSOAP;
		}
		
		if (clientType.equals(LocationClient.CLIENT_TYPE.REST)) {
			if (locationClientREST == null) {
				locationClientREST = new LocationClient(getApplicationURI(request), LocationClient.CLIENT_TYPE.REST);
			}
			
			return locationClientREST;
		}
		
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model) {

		model.addAttribute("locationData", new LocationData());
		
		return "locationClient";
		
	}
	
	/**
	 * returns the URI of the application 
	 * @param req
	 * @return
	 */
	private String getApplicationURI(HttpServletRequest req) {
		if (applicationURI == null) {
			String port;
			
		    if ("http".equalsIgnoreCase(req.getScheme()) && req.getServerPort() != 80 ||
		            "https".equalsIgnoreCase(req.getScheme()) && req.getServerPort() != 443 ) {
		        port = ":" + req.getServerPort();
		    } else {
		        port = "";
		    }
		    
	        applicationURI = req.getScheme() + "://" + req.getServerName() +
	                port + req.getContextPath();
	        
	        logger.debug("Application URL was set to " + applicationURI );
		}
		
		return applicationURI;
	}	

	@RequestMapping(value="/read", method = RequestMethod.GET)
	public String readLocation(@RequestParam String protocol, @RequestParam("location") String location, Model model, HttpServletRequest request) throws LocationNotFoundException {
		
		logger.debug("location param is " + location);
		
		LocationData locationDataResp = getLocationClient(request, LocationClient.CLIENT_TYPE.fromString(protocol)).readLocation(location);

		logger.debug("location result is " +  locationDataResp);

		List<LocationData> locationDataList = new LinkedList<LocationData>();
		locationDataList.add(locationDataResp);
		model.addAttribute("locationDataList", locationDataList);
		
		return "locationClient";
		
	} 
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String createLocation(@RequestParam String protocol, LocationData locationData, Model model, HttpServletRequest request, BindingResult bindingResult) throws DuplicateLocationException {

		logger.debug("location param is " + locationData);
		
		try {
			LocationData locationDataResp = getLocationClient(request, LocationClient.CLIENT_TYPE.fromString(protocol)).createLocation(locationData);
			List<LocationData> locationDataList = new LinkedList<LocationData>();
			locationDataList.add(locationDataResp);
			model.addAttribute("locationDataList", locationDataList);
			logger.debug("location result is " +  locationDataResp);
		} catch (ValidationException e) {
			for (String field : e.getExceptionData().getData().keySet()) {
				bindingResult.rejectValue(field, null, e.getExceptionData().getData().get(field));
			}
		}
		
		return "locationClient";
		
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String updateLocation(@RequestParam String protocol, LocationData locationData, Model model, HttpServletRequest request) throws DuplicateLocationException {

		logger.debug("location param is " + locationData);
		
		LocationData locationDataResp = getLocationClient(request, LocationClient.CLIENT_TYPE.fromString(protocol)).updateorCreateLocation(locationData);
		/*
		new JAXBElement<LocationData>();

		LocationData locationDataResp = locationClient.createLocation(locationData.getLocation(), locationData);
		 */

		logger.debug("location result is " +  locationDataResp);

		List<LocationData> locationDataList = new LinkedList<LocationData>();
		locationDataList.add(locationDataResp);
		model.addAttribute("locationDataList", locationDataList);
		
		return "locationClient";
		
	}

	@RequestMapping(value="/readall", method = RequestMethod.GET)
	public String readAllLocation(@RequestParam String protocol, Model model, HttpServletRequest request) throws DuplicateLocationException {
		Collection<LocationData> locationDataList = getLocationClient(request, LocationClient.CLIENT_TYPE.fromString(protocol)).readAllLocations();

		model.addAttribute("locationDataList", locationDataList);
		
		return "locationClient";
	}	

	@RequestMapping(value="/deleteall", method = RequestMethod.GET)
	public String deleteAllLocation(@RequestParam String protocol, Model model, HttpServletRequest request) throws DuplicateLocationException {
		getLocationClient(request, LocationClient.CLIENT_TYPE.fromString(protocol)).deleteAllLocation();

		return "locationClient";
	}	

	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String deleteAllLocation(@RequestParam String protocol, @RequestParam String location, Model model, HttpServletRequest request) throws DuplicateLocationException, LocationNotFoundException {
		getLocationClient(request, LocationClient.CLIENT_TYPE.fromString(protocol)).deleteLocation(location);

		return "locationClient";
	}	
	
}
