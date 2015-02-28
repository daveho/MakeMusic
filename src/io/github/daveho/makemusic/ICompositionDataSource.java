package io.github.daveho.makemusic;

import java.io.IOException;
import java.io.Reader;

public interface ICompositionDataSource {
	public Reader read(String path) throws IOException;
}
