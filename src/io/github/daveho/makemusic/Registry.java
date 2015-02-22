package io.github.daveho.makemusic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import io.github.daveho.makemusic.playback.IMessageGenerator;
import io.github.daveho.makemusic.playback.ISynth;

public class Registry {
	private static final Registry instance = new Registry();
	
	public static Registry getInstance() {
		return instance;
	}
	
	private Map<Class<? extends IMMData>, Class<? extends IMessageGenerator>> msgGenMap;
	private Map<Class<? extends IMMData>, Class<? extends ISynth>> synthMap;
	
	private Registry() {
		msgGenMap = new HashMap<>();
		synthMap = new HashMap<>();
		findPlaybackClasses("io.github.daveho.makemusic.playback", IMessageGenerator.class, msgGenMap);
		findPlaybackClasses("io.github.daveho.makemusic.playback", ISynth.class, synthMap);
	}
	
	private<E> void findPlaybackClasses(
			String pkgName,
			Class<E> playbackCls,
			Map<Class<? extends IMMData>, Class<? extends E>> map) {
		System.out.println("Adding " + playbackCls.getSimpleName() + " classes to registry");
		Reflections reflections = new Reflections(pkgName);
		Set<Class<? extends E>> playbackClasses = reflections.getSubTypesOf(playbackCls);
		if (playbackClasses.isEmpty()) {
			throw new RuntimeException("No " + playbackCls.getSimpleName() + " classes found");
		}
		for (Class<? extends E> cls : playbackClasses) {
			MMPlayback annotation = cls.getAnnotation(MMPlayback.class);
			if (annotation != null) {
				System.out.println("Registry: " + annotation.dataClass().getSimpleName() + " => " + cls.getSimpleName());
				map.put(annotation.dataClass(), cls);
			}
		}
	}

	/**
	 * Create a {@link IMessageGenerator} using given {@link IMMData}.
	 * 
	 * @param data the {@link IMMData}
	 * @return a {@link IMessageGenerator}
	 */
	public IMessageGenerator createMessageGenerator(IMMData data) {
		return createPlaybackObject(data, IMessageGenerator.class, msgGenMap);
	}

	/**
	 * Create a {@link ISynth} using given {@link IMMData}.
	 * 
	 * @param data (the {@link IMMData})
	 * @return a {@link ISynth}
	 */
	public ISynth createSynth(IMMData data) {
		return createPlaybackObject(data, ISynth.class, synthMap);
	}

	private<E extends IMMPlayback> E createPlaybackObject(IMMData data, Class<E> playbackCls,
			Map<Class<? extends IMMData>, Class<? extends E>> map) {
		System.out.println("Looking for key " + data.getClass().getSimpleName());
		for (Class<?> key : map.keySet()) {
			System.out.println("Map has key " + key.getSimpleName());
		}
		Class<? extends E> cls = map.get(data.getClass());
		if (cls == null) {
			throw new RuntimeException("No " + playbackCls.getSimpleName() + " found for " + data.getClass().getSimpleName());
		}
		try {
			return cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not create " + playbackCls.getSimpleName() + " for " + data.getClass().getSimpleName());
		}
	}
}
