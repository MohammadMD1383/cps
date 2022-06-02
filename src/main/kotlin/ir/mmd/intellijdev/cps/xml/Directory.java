package ir.mmd.intellijdev.cps.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Directory {
	@JacksonXmlProperty(isAttribute = true, localName = "name")
	public String name;
	
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "directory")
	public List<Directory> directories;
	
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "file")
	public List<File> files;
	
	public Directory() {
	}
	
	public Directory(
		// @JacksonXmlProperty(isAttribute = true, localName = "name")
		String name,
		
		// @JacksonXmlElementWrapper(useWrapping = false)
		// @JacksonXmlProperty(localName = "directory")
		List<Directory> directories,
		
		// @JacksonXmlElementWrapper(useWrapping = false)
		// @JacksonXmlProperty(localName = "file")
		List<File> files
	) {
		this.name = name;
		this.directories = directories;
		this.files = files;
	}
	
	public Directory withParentPath(String path) {
		return new Directory(path + "/" + name, directories, files);
	}
}
