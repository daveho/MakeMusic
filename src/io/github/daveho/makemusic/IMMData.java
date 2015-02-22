package io.github.daveho.makemusic;

/**
 * Data object that represents some named numeric parameters.
 * All MakeMusic data objects implement this interface.
 * 
 * @author David Hovemeyer
 */
public interface IMMData {
	/**
	 * Determine whether the object has the given parameter.
	 * 
	 * @param paramName parameter name
	 * @return true if the object has the named parameter, false otherwise
	 */
	public boolean hasParam(String paramName);
	
	/**
	 * Get the value of the named parameter.
	 * 
	 * @param paramName parameter name
	 * @return the value of the named parameter
	 */
	public double getParam(String paramName);
	
	/**
	 * Get the value of the named parameter as a <code>long</code>.
	 * 
	 * @param paramName parameter name
	 * @return the value of the named parameter as a <code>long</code>
	 */
	public long getParamAsLong(String paramName);
	
	/**
	 * Get the value of the named parameter as an <code>int</code>.
	 * 
	 * @param paramName parameter name
	 * @return the value of the named parameter as an <code>int</code>
	 */
	public int getParamAsInt(String paramName);
	
	/**
	 * Set a parameter value.
	 * 
	 * @param paramName parameter name
	 * @param value     parameter value
	 */
	public void setParam(String paramName, double value);
}
