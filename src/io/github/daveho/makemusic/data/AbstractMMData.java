package io.github.daveho.makemusic.data;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMMData implements IMMData {
	private Map<String, Double> paramMap;
	
	public AbstractMMData() {
		paramMap = new HashMap<String, Double>();
	}
	
	@Override
	public boolean hasParam(String paramName) {
		return paramMap.containsKey(paramName);
	}
	
	@Override
	public double getParam(String paramName) {
		return paramMap.get(paramName);
	}
	
	@Override
	public long getParamAsLong(String paramName) {
		return (long) paramMap.get(paramName).doubleValue();
	}
	
	@Override
	public int getParamAsInt(String paramName) {
		return (int) paramMap.get(paramName).doubleValue();
	}

	@Override
	public void setParam(String paramName, double value) {
		paramMap.put(paramName, value);
	}
	
	public IMMData clone() {
		try {
			return (IMMData) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException("Should not happen", e);
		}
	}
}
