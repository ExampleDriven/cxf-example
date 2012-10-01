package org.exampledriven.cxfexample.exception.meta;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.exampledriven.cxfexample.exception.DuplicateLocationException;
import org.exampledriven.cxfexample.exception.LocationNotFoundException;
import org.exampledriven.cxfexample.exception.ValidationException;


public class LocationResponseExceptionMapper implements ResponseExceptionMapper<LocationBaseException> {

	@Override
	public LocationBaseException fromResponse(Response r) {
		
		if (r.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			return new LocationNotFoundException();
		} 

		if (r.getStatus() == Status.CONFLICT.getStatusCode()) {
			return new DuplicateLocationException();
		}

		if (r.getStatus() == Status.BAD_REQUEST.getStatusCode()) {

			JAXBContext context;
			try {
				context = JAXBContext.newInstance(ExceptionData.class);
				Unmarshaller um = context.createUnmarshaller();
				ExceptionData ed = (ExceptionData) um.unmarshal((InputStream)r.getEntity());
				return new ValidationException(ed.getData());
			} catch (JAXBException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			
		}
		
		return null;
	}
	
}
