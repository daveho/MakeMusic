package io.github.daveho.makemusic.playback;

import io.github.daveho.makemusic.data.MMData;

public interface MMPlayback extends Cloneable {
	public Class<? extends MMData> getDataType();
	public void setData(MMData data);
	public MMPlayback clone();
}
