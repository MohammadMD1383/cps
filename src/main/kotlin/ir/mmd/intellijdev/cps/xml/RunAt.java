package ir.mmd.intellijdev.cps.xml;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RunAt {
	@JsonProperty("beginning")
	Beginning,
	
	@JsonProperty("end")
	End
}
