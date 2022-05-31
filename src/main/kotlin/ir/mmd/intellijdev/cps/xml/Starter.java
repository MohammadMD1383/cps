package ir.mmd.intellijdev.cps.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "starter")
public class Starter {
	@JacksonXmlProperty(localName = "structure")
	public Structure structure;
	
	@JacksonXmlElementWrapper
	@JacksonXmlProperty(localName = "scripts")
	public List<Script> scripts;
}
