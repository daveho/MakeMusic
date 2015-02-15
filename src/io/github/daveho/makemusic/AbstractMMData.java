package io.github.daveho.makemusic;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMMData implements MMData {
	private Map<String, Double> propertyMap;
	
	public AbstractMMData() {
		propertyMap = new HashMap<String, Double>();
	}
	
	@Override
	public boolean hasProperty(String propertyName) {
		return propertyMap.containsKey(propertyName);
	}
	
	@Override
	public double getProperty(String propertyName) {
		return propertyMap.get(propertyName);
	}
	
	@Override
	public long getPropertyAsLong(String propertyName) {
		return (long) propertyMap.get(propertyName).doubleValue();
	}
	
	@Override
	public int getPropertyAsInt(String propertyName) {
		return (int) propertyMap.get(propertyName).doubleValue();
	}

	@Override
	public void setProperty(String propertyName, double value) {
		propertyMap.put(propertyName, value);
	}
	
	public MMData clone() {
		try {
			return (MMData) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException("Should not happen", e);
		}
	}
}
