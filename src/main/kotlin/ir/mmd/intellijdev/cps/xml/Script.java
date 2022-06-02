package ir.mmd.intellijdev.cps.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Script {
	@JacksonXmlProperty(isAttribute = true, localName = "run-at")
	public RunAt runAt;
	
	@JacksonXmlText
	public String content;
	
	// public Script(
	// 	@JacksonXmlProperty(isAttribute = true, localName = "run-at")
	// 	RunAt runAt,
	//
	// 	@JacksonXmlText
	// 	String content
	// ) {
	// 	this.runAt = runAt;
	// 	this.content = content;
	// }
}
