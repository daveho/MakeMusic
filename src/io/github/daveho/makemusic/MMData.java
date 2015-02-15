package io.github.daveho.makemusic;

/**
 * Data object that represents some named numeric parameters.
 * All MakeMusic data objects implement this interface.
 */
public interface MMData extends Cloneable {
	/**
	 * Get the short "code" that identifies this type of data.
	 * @return the code
	 */
	public String getCode();
	public boolean hasProperty(String propertyName);
	public double getProperty(String propertyName);
	public long getPropertyAsLong(String propertyName);
	public int getPropertyAsInt(String propertyName);
	public void setProperty(String propertyName, double value);
	
	public MMData clone();
}
