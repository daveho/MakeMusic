package io.github.daveho.makemusic.data;

/**
 * Data object that represents some named numeric parameters.
 * All MakeMusic data objects implement this interface.
 */
public interface IMMData extends Cloneable {
	/**
	 * Get the short "code" that identifies this type of data.
	 * @return the code
	 */
	public String getCode();
	public boolean hasParam(String paramName);
	public double getParam(String paramName);
	public long getParamAsLong(String paramName);
	public int getParamAsInt(String paramName);
	public void setParam(String paramName, double value);
	
	public IMMData clone();
}
