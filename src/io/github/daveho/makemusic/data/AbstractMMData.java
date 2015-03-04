package io.github.daveho.makemusic.data;

import io.github.daveho.makemusic.IMMData;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMMData implements IMMData {
	private Map<String, Object> paramMap;
	
	public AbstractMMData() {
		paramMap = new HashMap<String, Object>();
	}
	
	@Override
	public boolean hasParam(String paramName) {
		return paramMap.containsKey(paramName);
	}
	
	@Override
	public boolean hasStringParam(String paramName) {
		if (!hasParam(paramName)) {
			return false;
		}
		return paramMap.get(paramName) instanceof String;
	}
	
	@Override
	public double getParam(String paramName) {
		return ((Double) paramMap.get(paramName)).doubleValue();
	}
	
	@Override
	public long getParamAsLong(String paramName) {
		return (long) getParam(paramName);
	}
	
	@Override
	public int getParamAsInt(String paramName) {
		return (int) getParam(paramName);
	}
	
	@Override
	public String getParamAsString(String paramName) {
		if (!paramMap.containsKey(paramName)) {
			throw new IllegalArgumentException("No such parameter: " + paramName);
		}
		return paramMap.get(paramName).toString();
	}

	@Override
	public void setParam(String paramName, double value) {
		paramMap.put(paramName, value);
	}
	
	@Override
	public void setParam(String paramName, String value) {
		paramMap.put(paramName, value);
	}
	
	@Override
	public Collection<String> getParamNames() {
		return Collections.unmodifiableSet(paramMap.keySet());
	}
}
