package io.github.daveho.makemusic.data;

import io.github.daveho.makemusic.IMMData;

import java.util.Collection;
import java.util.Collections;
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
	
	@Override
	public Collection<String> getParamNames() {
		return Collections.unmodifiableSet(paramMap.keySet());
	}
}
