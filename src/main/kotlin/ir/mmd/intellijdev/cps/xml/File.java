package ir.mmd.intellijdev.cps.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class File {
	@JacksonXmlProperty(isAttribute = true, localName = "name")
	public String name;
	
	@JacksonXmlText
	public String content;
}
