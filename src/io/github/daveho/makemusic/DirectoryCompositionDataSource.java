package io.github.daveho.makemusic;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DirectoryCompositionDataSource implements ICompositionDataSource {
	private String baseDir;
	
	public DirectoryCompositionDataSource(String baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public Reader read(String path) throws IOException {
		if (!path.startsWith("/")) {
			throw new IOException("Path doesn't start with \"/\": " + path);
		}
		return new FileReader(baseDir + path);
	}
}
