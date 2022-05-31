package ir.mmd.intellijdev.cps.builder.model;

public class Path {
	public final String path;
	public final PathType type;
	
	public Path(
		String path,
		PathType type
	) {
		this.path = path;
		this.type = type;
	}
}
