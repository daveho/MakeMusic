package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.EffectsChainData;
import io.github.daveho.makemusic.data.GervillData;
import io.github.daveho.makemusic.data.MetronomeData;
import io.github.daveho.makemusic.data.MidiMessageRecorderData;
import io.github.daveho.makemusic.data.PlayLiveData;
import io.github.daveho.makemusic.data.TrackData;
import io.github.daveho.makemusic.playback.CompositionPlayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.beadsproject.beads.core.AudioContext;

public class Playground {
	private class PlayerTask implements Runnable {
		private volatile boolean done = false;
		
		@Override
		public void run() {
			AudioContext ac = new AudioContext();
			CompositionPlayer player = createPlayer();
			player.start(ac);
			
			while (!done) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					// Interrupted
				}
			}
			
			System.out.println("Shutting down CompositionPlayer");
			player.stop();
		}
		
		public void setDone(boolean done) {
			this.done = done;
		}
	}
	
	private interface Command {
		public void execute(String arg) throws IOException;
	}
	
	private class DemoCommand implements Command {
		@Override
		public void execute(String arg) {
			// Load demo CompositionData
			compositionData = createDemoCompositionData();
		}
	}
	
	private class ReadCommand implements Command {
		@Override
		public void execute(String arg) throws IOException {
			String dirName = arg.trim();
			DirectoryCompositionDataSource source = new DirectoryCompositionDataSource(dirName);
			CompositionDataReader cdr = new CompositionDataReader();
			compositionData = cdr.read(source);
			System.out.println("Read composition data from " + dirName);
		}
	}
	
	private class WriteCommand implements Command {
		@Override
		public void execute(String arg) throws IOException {
			if (compositionData == null) {
				System.out.println("No composition data to write");
			} else {
				String dirName = arg.trim();
				DirectoryCompositionDataSink sink = new DirectoryCompositionDataSink(dirName);
				CompositionDataWriter cdw = new CompositionDataWriter();
				cdw.write(compositionData, sink);
				System.out.println("Wrote composition data to " + dirName);
			}
		}
	}
	
	private class StartCommand implements Command {
		@Override
		public void execute(String arg) throws IOException {
			if (compositionData == null) {
				System.out.println("No composition data to play");
			} else if (thread == null) {
				task = new PlayerTask();
				thread = new Thread(task);
				thread.start();
				System.out.println("Started player thread");
			}
		}
	}
	
	private class StopCommand implements Command {
		@Override
		public void execute(String arg) throws IOException {
			if (thread != null) {
				task.setDone(true);
				thread.interrupt();
				boolean running;
				do {
					try {
						thread.join();
					} catch (InterruptedException e) {
						System.out.println("This should not happen");
					}
					running = thread.isAlive();
				} while (running);
				System.out.println("Player thread has shut down");
				thread = null;
				task = null;
			}
		}
	}
	
	private class QuitCommand implements Command {
		@Override
		public void execute(String arg) throws IOException {
			if (thread != null) {
				System.out.println("Player thread is running");
			} else {
				quitCommandLoop = true;
			}
		}
	}
	
	private static final int BPM = 100;
	
	private Thread thread;
	private PlayerTask task;
	private CompositionData compositionData;
	private Map<String, Command> commandMap;
	private boolean quitCommandLoop;

	public Playground() {
		commandMap = new HashMap<>();
		commandMap.put("demo", new DemoCommand());
		commandMap.put("read", new ReadCommand());
		commandMap.put("write", new WriteCommand());
		commandMap.put("start", new StartCommand());
		commandMap.put("stop", new StopCommand());
		commandMap.put("quit", new QuitCommand());
	}
	
	public CompositionPlayer createPlayer() {
		if (compositionData == null) {
			throw new IllegalStateException("No composition data");
		}
		CompositionPlayer player = new CompositionPlayer(compositionData);
		return player;
	}

	private CompositionData createDemoCompositionData() {
		CompositionData compositionData = new CompositionData();
		
		addMetronomeTrack(compositionData);
		addPlayLiveTrack(compositionData);
		return compositionData;
	}

	private void addMetronomeTrack(CompositionData compositionData) {
		TrackData td = new TrackData();
		
		MetronomeData md = new MetronomeData();
		
		md.setParam("intervalMs", (double)(60*1000)/BPM);
		
		td.setMessageGeneratorData(md);
		
		GervillData gd = new GervillData();
		td.setSynthData(gd);
		
		EffectsChainData ecd = new EffectsChainData();
		td.setEffectsChainData(ecd);
		
		compositionData.addTrackData(td);
	}
	
	public void addPlayLiveTrack(CompositionData compositionData) {
		TrackData td = new TrackData();
		
		PlayLiveData plmgd = new PlayLiveData();
		plmgd.setParam("patch", 54);
		td.setMessageGeneratorData(plmgd);
		
		// Set up a MidiMessageRecorder to record the midi messages
		// and save them to the CompositionData
		MidiMessageRecorderData mmrd = new MidiMessageRecorderData();
		td.addMessageInterceptorData(mmrd);
		
		GervillData gd = new GervillData();
		td.setSynthData(gd);
		
		EffectsChainData ecd = new EffectsChainData();
		td.setEffectsChainData(ecd);
		
		compositionData.addTrackData(td);
	}

	public void commandLoop() throws IOException {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		quitCommandLoop = false;
		while (!quitCommandLoop) {
			System.out.print("> ");
			System.out.flush();
			
			String cmd = in.nextLine();
			if (cmd == null) {
				break;
			}
			
			cmd = cmd.trim();
			String arg = "";
			int space = cmd.indexOf(' ');
			if (space >= 0) {
				arg = cmd.substring(space+1);
				cmd = cmd.substring(0, space);
			}
			Command c = commandMap.get(cmd);
			if (c != null) {
				c.execute(arg);
			} else {
				System.out.println("Unknown command: " + cmd);
			}
		}
		System.out.println("cya");
	}
	
	public static void main(String[] args) throws IOException {
		Playground playground = new Playground();
		playground.commandLoop();
	}
}
