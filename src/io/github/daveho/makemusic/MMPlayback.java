package io.github.daveho.makemusic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation which should be specified on all classes implementing the
 * {@link IMMPlayback} interface.
 * 
 * @author David Hovemeyer
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MMPlayback {
	/**
	 * Get the type of the corresponding data objects
	 * (implementing {@link IMMData}) providing the data to
	 * be played back by the annotated playback class.
	 * 
	 * @return the type of the corresponding data objects
	 */
	Class<? extends IMMData> dataClass();
}
