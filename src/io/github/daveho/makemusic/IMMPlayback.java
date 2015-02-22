package io.github.daveho.makemusic;


public interface IMMPlayback {
	public Class<? extends IMMData> getDataType();
	public void setData(IMMData data);
}
