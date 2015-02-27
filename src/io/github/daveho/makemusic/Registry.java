package io.github.daveho.makemusic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

/**
 * Registry for finding {@link IMMPlayback} objects ({@link IMessageGenerator}s
 * and {@link ISynth}s) corresponding to a particular {@link IMMData} object.
 * All playback objects are found automatically because they are annotated
 * with the {@link MMPlayback} annotation.
 * 
 * @author David Hovemeyer
 */
public class Registry {
	private static final Registry instance = new Registry();
	
	/**
	 * Get the singleton instance of {@link Registry}.
	 * 
	 * @return the singleton instance
	 */
	public static Registry getInstance() {
		return instance;
	}
	
	private Map<Class<? extends IMMData>, Class<? extends IMessageGenerator>> msgGenMap;
	private Map<Class<? extends IMMData>, Class<? extends ISynth>> synthMap;
	private Map<String, Class<? extends IMMData>> msgGenDataMap;
	private Map<String, Class<? extends IMMData>> synthDataMap;
	
	private Registry() {
		msgGenMap = new HashMap<>();
		synthMap = new HashMap<>();
		msgGenDataMap = new HashMap<>();
		synthDataMap = new HashMap<>();
		findPlaybackClasses("io.github.daveho.makemusic.playback", IMessageGenerator.class, msgGenMap, msgGenDataMap);
		findPlaybackClasses("io.github.daveho.makemusic.playback", ISynth.class, synthMap, synthDataMap);
	}
	
	private<E> void findPlaybackClasses(
			String pkgName,
			Class<E> playbackCls,
			Map<Class<? extends IMMData>, Class<? extends E>> map,
			Map<String, Class<? extends IMMData>> dataMap) {
//		System.out.println("Adding " + playbackCls.getSimpleName() + " classes to registry");
		Reflections reflections = new Reflections(pkgName);
		Set<Class<? extends E>> playbackClasses = reflections.getSubTypesOf(playbackCls);
		if (playbackClasses.isEmpty()) {
			throw new RuntimeException("No " + playbackCls.getSimpleName() + " classes found");
		}
		for (Class<? extends E> cls : playbackClasses) {
			MMPlayback annotation = cls.getAnnotation(MMPlayback.class);
			if (annotation != null) {
//				System.out.println("Registry: " + annotation.dataClass().getSimpleName() + " => " + cls.getSimpleName());
				Class<? extends IMMData> dataClass = annotation.dataClass();

				MMData dataAnnotation = dataClass.getAnnotation(MMData.class);
				if (dataAnnotation == null) {
					throw new RuntimeException("Data class " + dataClass.getSimpleName() + " not annotated with " + MMData.class.getSimpleName());
				}
				
				map.put(dataClass, cls);
				dataMap.put(dataAnnotation.code(), dataClass);
			}
		}
	}

	/**
	 * Create a {@link IMessageGenerator} using given {@link IMesssageGeneratorData}.
	 * 
	 * @param data the {@link IMessageGeneratorData}
	 * @return a {@link IMessageGenerator}
	 */
	public IMessageGenerator createMessageGenerator(IMessageGeneratorData data) {
		return createPlaybackObject(data, IMessageGenerator.class, msgGenMap);
	}

	/**
	 * Create a {@link ISynth} using given {@link ISynthData}.
	 * 
	 * @param data (the {@link ISynthData})
	 * @return a {@link ISynth}
	 */
	public ISynth createSynth(ISynthData data) {
		return createPlaybackObject(data, ISynth.class, synthMap);
	}

	private<E extends IMMPlayback> E createPlaybackObject(IMMData data, Class<E> playbackCls,
			Map<Class<? extends IMMData>, Class<? extends E>> map) {
		Class<? extends E> cls = map.get(data.getClass());
		if (cls == null) {
			throw new RuntimeException("No " + playbackCls.getSimpleName() + " found for " + data.getClass().getSimpleName());
		}
		try {
			return cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not create " + playbackCls.getSimpleName() + " for " + data.getClass().getSimpleName(), e);
		}
	}

	public IMessageGeneratorData createMessageGeneratorData(String code) {
		return createDataObject(code, IMessageGeneratorData.class, msgGenDataMap);
	}

	public ISynthData createSynthData(String code) {
		return createDataObject(code, ISynthData.class, synthDataMap);
	}
	
	public<E extends IMMData> E createDataObject(String code, Class<E> cls, Map<String, Class<? extends IMMData>> dataMap) {
		Class<? extends IMMData> dataClass = dataMap.get(code);
		if (dataClass == null) {
			throw new RuntimeException("Unknown " + cls.getSimpleName() + " class for code " + code);
		}
		try {
			return cls.cast(dataClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not create " + dataClass.getSimpleName(), e);
		}
	}
}
