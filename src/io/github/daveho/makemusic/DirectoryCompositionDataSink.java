package io.github.daveho.makemusic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DirectoryCompositionDataSink implements ICompositionDataSink {
	private String baseDir;
	
	public DirectoryCompositionDataSink(String baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public Writer write(String path) throws IOException {
		if (!path.startsWith("/")) {
			throw new IOException("Path " + path + " does not start with /");
		}
		
		String fullPath = baseDir + path;
		
		// Ensure parent directory exists
		int lastSlash = fullPath.lastIndexOf('/');
		File dir = new File(fullPath.substring(0, lastSlash));
		dir.mkdirs();
		
		return new FileWriter(fullPath);
	}
}
