package io.github.daveho.makemusic;

import java.util.List;

public class ParamUtils {
	public static String asString(IMMData data) {
		StringBuilder buf = new StringBuilder();
		
		for (String paramName : data.getParamNames()) {
			buf.append(" ");
			buf.append(paramName);
			buf.append("=");
			buf.append(data.getParam(paramName));
		}
		
		return buf.toString();
	}

	public static void fromTokens(IMMData data, List<String> tokens) {
		for (String pair : tokens) {
			int eq = pair.indexOf('=');
			if (eq < 0) {
				throw new RuntimeException("Invalid param/value pair: " + pair);
			}
			String paramName = pair.substring(0, eq);
			Double value = Double.valueOf(pair.substring(eq+1));
			data.setParam(paramName, value);
		}
	}
}
