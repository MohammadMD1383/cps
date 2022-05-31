package ir.mmd.intellijdev.cps.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Structure {
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "directory")
	public List<Directory> directories;
	
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "file")
	public List<File> files;
}
