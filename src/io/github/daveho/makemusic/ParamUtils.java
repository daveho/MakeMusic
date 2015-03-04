package io.github.daveho.makemusic;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class ParamUtils {
	public static String asString(IMMData data) {
		StringBuilder buf = new StringBuilder();
		
		for (String paramName : data.getParamNames()) {
			buf.append(" ");
			buf.append(paramName);
			buf.append("=");
			buf.append(encodeValue(data, paramName));
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
			String valueStr = pair.substring(eq+1);
			decodeValue(data, paramName, valueStr);
		}
	}

	private static String encodeValue(IMMData data, String paramName) {
		if (data.hasStringParam(paramName)) {
			// URL encode
			try {
				return "\"" + URLEncoder.encode(data.getParamAsString(paramName), "UTF-8") + "\"";
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("Error encoding string-valued parameter", e);
			}
		} else {
			// Numeric value: no encoding needed
			return String.valueOf(data.getParam(paramName));
		}
	}

	private static void decodeValue(IMMData data, String paramName, String valueStr) {
		if (valueStr.startsWith("\"")) {
			// String-valued parameter
			valueStr = valueStr.substring(1, valueStr.length() - 1);
			try {
				data.setParam(paramName, URLDecoder.decode(valueStr, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("Error decoding string-valued parameter", e);
			}
		} else {
			// Numeric parameter
			data.setParam(paramName, Double.valueOf(valueStr));
		}
	}
}
