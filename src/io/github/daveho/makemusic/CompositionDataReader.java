package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.CompositionData;

public class CompositionDataReader {
	private ICompositionDataSource dataSource;
	private CompositionData compositionData;
	
	public CompositionDataReader(ICompositionDataSource dataSource) {
		this.dataSource = dataSource;
	}
}
