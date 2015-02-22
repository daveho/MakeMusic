package io.github.daveho.makemusic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MMPlayback {
	Class<? extends IMMData> dataClass();
}
