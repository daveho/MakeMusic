package io.github.daveho.makemusic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation which should be specified on all classes implementing
 * {@link IMMData}.
 * 
 * @author David Hovemeyer
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MMData {
	/**
	 * Get the short "code" that identifies the type of data
	 * of the annotated {@link IMMData} class.
	 * 
	 * @return the code
	 */
	String code();
}
