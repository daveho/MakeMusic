package io.github.daveho.makemusic;

import java.io.IOException;
import java.io.Reader;

import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.EffectsChainData;
import io.github.daveho.makemusic.data.TrackData;

public class CompositionDataReader {
	public CompositionData read(ICompositionDataSource dataSource) throws IOException {
		CompositionData compositionData = new CompositionData();
		
		CompositionFileReader cfr = new CompositionFileReader();
		try (Reader cr = dataSource.read("/composition.txt")) {
			cfr.read(cr, new CompositionFileReader.Events() {
				@Override
				public void onTrack(String trackPath) throws Throwable {
					readTrack(dataSource, trackPath, compositionData);
				}
			});
		}
		return compositionData;
	}

	private void readTrack(ICompositionDataSource dataSource, String trackPath, CompositionData compositionData)
			throws IOException {
		// Create a TrackData object
		TrackData trackData = new TrackData();
		
		// Read data in track file
		try (Reader tr = dataSource.read(trackPath)) {
			TrackFileReader tfr = new TrackFileReader();
			tfr.read(tr, new TrackFileReader.Events() {
				@Override
				public void onSynthData(ISynthData data) throws Throwable {
					trackData.setSynthData(data);
				}
				
				@Override
				public void onMidiMessageInterceptorData(
						IMidiMessageInterceptorData data) throws Throwable {
					trackData.addMessageInterceptorData(data);
				}
				
				@Override
				public void onMessageGeneratorData(IMessageGeneratorData data)
						throws Throwable {
					trackData.setMessageGeneratorData(data);
				}
			});
		}
		
		// TODO: right now, EffectsChainData doesn't contain any information
		EffectsChainData effectsChainData = new EffectsChainData();
		trackData.setEffectsChainData(effectsChainData);
		
		// Add TrackData to CompositionData
		compositionData.addTrackData(trackData);
	}
}
