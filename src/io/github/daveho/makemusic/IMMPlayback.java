package io.github.daveho.makemusic;

/**
 * All playback objects (the runtime counterparts to the classes
 * implementing {@link IMMData}) implement this interface.
 * 
 * @author David Hovemeyer
 */
public interface IMMPlayback {
	/**
	 * Get the type of the corresponding {@link IMMData} class.
	 * 
	 * @return the type of the data class
	 */
	public Class<? extends IMMData> getDataType();
	
	/**
	 * Set the data object (containing parameters, data, etc.)
	 * It can be assumed to be an instance of the class returned
	 * by {@link #getDataType()}.
	 * 
	 * @param data the data object to set
	 */
	public void setData(IMMData data);
}
