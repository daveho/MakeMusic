package io.github.daveho.makemusic;

public interface MMPlayback extends Cloneable {
	public Class<? extends MMData> getDataType();
	public void setData(MMData data);
	public MMPlayback clone();
}
