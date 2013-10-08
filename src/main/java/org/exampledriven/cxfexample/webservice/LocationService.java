package org.exampledriven.cxfexample.webservice;

import java.util.Collection;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.exampledriven.cxfexample.data.LocationData;
import org.exampledriven.cxfexample.exception.DuplicateLocationException;
import org.exampledriven.cxfexample.exception.LocationNotFoundException;

@Path("/location/")
@WebService
public interface LocationService {

	@WebMethod
	@GET
	@Path("{location}")
	@Descriptions({
		@Description(value = "returns a location data ", target = DocTarget.METHOD),
		@Description(value = "the location data", target = DocTarget.RETURN)
	})
    public LocationData readLocation(@Description(value = "the string representation of the location") @PathParam("location") @NotNull @Size(max=10, min=5) String location) throws LocationNotFoundException;
	
	@WebMethod
	@GET
	@Path("*")
	@Descriptions({
		@Description(value = "returns all locations", target = DocTarget.METHOD),
		@Description(value = "the location data", target = DocTarget.RETURN)
	})
    public Collection<LocationData> readAllLocations();
	
	@WebMethod
	@POST
	@Descriptions({
		@Description(value = "stores a new location data", target = DocTarget.METHOD),
		@Description(value = "the newly created location data", target = DocTarget.RETURN)
	})
	public LocationData createLocation(@Valid LocationData locationData) throws DuplicateLocationException;
	
	@WebMethod
	@PUT
	@Descriptions({
		@Description(value = "updates or creates a new location data", target = DocTarget.METHOD),
		@Description(value = "the newly created location data", target = DocTarget.RETURN)
	})
	public LocationData updateorCreateLocation(@Valid LocationData locationData);

	@WebMethod
	@DELETE
	@Path("{location}")
	@Descriptions({
		@Description(value = "deletes a location data", target = DocTarget.METHOD),
		@Description(value = "the location data", target = DocTarget.RETURN)
	})
    public void deleteLocation(@Description(value = "the string representation of the location") @PathParam("location") @NotNull @Size(max=10, min=5) String location) throws LocationNotFoundException;

	@WebMethod
	@DELETE
	@Path("*")
	@Descriptions({
		@Description(value = "deletes All location data", target = DocTarget.METHOD)
	})
    public void deleteAllLocation();
	
}
