package io.github.daveho.makemusic;

import java.io.IOException;
import java.io.Writer;

public interface ICompositionDataSink {
	public Writer write(String path) throws IOException;
}
