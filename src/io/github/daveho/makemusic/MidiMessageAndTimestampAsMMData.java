package io.github.daveho.makemusic;

import java.util.ArrayList;
import java.util.Collection;

public class MidiMessageAndTimestampAsMMData implements IMMData {
	private byte[] data;
	private long timeStamp;
	
	public MidiMessageAndTimestampAsMMData() {
		data = new byte[0];
		timeStamp = 0L;
	}

	@Override
	public boolean hasParam(String paramName) {
		if (paramName.equals("b0")) {
			return data.length > 0;
		} else if (paramName.equals("b1")) {
			return data.length > 1;
		} else if (paramName.equals("b2")) {
			return data.length > 2;
		} else if (paramName.equals("b3")) {
			return data.length > 3;
		} else if (paramName.equals("ts")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double getParam(String paramName) {
		if (paramName.equals("b0")) {
			return data[0];
		} else if (paramName.equals("b1")) {
			return data[1];
		} else if (paramName.equals("b2")) {
			return data[2];
		} else if (paramName.equals("b3")) {
			return data[3];
		} else if (paramName.equals("ts")) {
			return timeStamp;
		} else {
			throw new IllegalArgumentException("Invalid parameter name: " + paramName);
		}
	}

	@Override
	public long getParamAsLong(String paramName) {
		return (long) (double) getParam(paramName);
	}

	@Override
	public int getParamAsInt(String paramName) {
		return (int) (double) getParam(paramName);
	}
	
	@Override
	public String getParamAsString(String paramName) {
		return Double.toString(getParam(paramName));
	}

	@Override
	public void setParam(String paramName, double value) {
		if (paramName.equals("b0")) {
			ensureDataLen(1);
			data[0] = (byte) value;
		} else if (paramName.equals("b1")) {
			ensureDataLen(2);
			data[1] = (byte) value;
		} else if (paramName.equals("b2")) {
			ensureDataLen(3);
			data[2] = (byte) value;
		} else if (paramName.equals("b3")) {
			ensureDataLen(4);
			data[3] = (byte) value;
		} else if (paramName.equals("ts")) {
			timeStamp = (long) value;
		} else {
			throw new IllegalArgumentException("Invalid parameter name: " + paramName);
		}
	}
	
	@Override
	public void setParam(String paramName, String value) {
		throw new IllegalArgumentException(MidiMessageAndTimestampAsMMData.class.getSimpleName() +
				" does not support string-valued parameters");
	}

	private void ensureDataLen(int len) {
		if (data.length < len) {
			byte[] a = new byte[len];
			System.arraycopy(data, 0, a, 0, data.length);
			data = a;
		}
	}

	@Override
	public Collection<String> getParamNames() {
		ArrayList<String> result = new ArrayList<>();
		
		result.add("ts");
		if (data.length > 0) { result.add("b0"); }
		if (data.length > 1) { result.add("b1"); }
		if (data.length > 2) { result.add("b2"); }
		if (data.length > 3) { result.add("b3"); }
		
		return result;
	}
	
	// TODO: conversion to/from MidiMessageAndTimestamp
}
