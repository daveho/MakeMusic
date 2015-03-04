package io.github.daveho.makemusic;

import java.util.Collection;

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
	 * Get the value of the named parameter as a string.
	 * 
	 * @param paramName parameter name
	 * @return the value of the named parameter as a string
	 */
	public String getParamAsString(String paramName);
	
	/**
	 * Set a parameter value.
	 * 
	 * @param paramName parameter name
	 * @param value     parameter value
	 */
	public void setParam(String paramName, double value);
	
	/**
	 * Set a parameter value as a string.
	 * 
	 * @param paramName parameter name
	 * @param value     paramete value string
	 */
	public void setParam(String paramName, String value);
	
	/**
	 * Get the names of all parameters stored in this object.
	 * 
	 * @return names of all parameters
	 */
	public Collection<String> getParamNames();
	
	/**
	 * Get the code for the type of {@link IMMData} represented
	 * by this object.  This is generally defined by the
	 * class's {@link MMData} annotation.
	 */
	public default String getCode() {
		MMData annotation = this.getClass().getAnnotation(MMData.class);
		if (annotation == null) {
			throw new RuntimeException(this.getClass().getSimpleName() +
					" has no " + MMData.class.getSimpleName() + " annotation");
		}
		return annotation.code();
	}
}
