package org.exampledriven.cxfexample.exception.meta;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Exception")
public class ExceptionData {

	private String message;
	private Map<String, String> data;
	
	public ExceptionData() {
	}
	
	public ExceptionData(String message, Map<String, String> data) {
		this.setMessage(message);
		this.setData(data);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static void main(String[] args) throws JAXBException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		
		ExceptionData em = new ExceptionData("hello", map);
		
		JAXBContext context = JAXBContext.newInstance(ExceptionData.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(em, System.out);	
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}	
	
	
}
