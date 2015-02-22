package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.IMMData;

public interface IMMPlayback extends Cloneable {
	public Class<? extends IMMData> getDataType();
	public void setData(IMMData data);
	public IMMPlayback clone();
}
