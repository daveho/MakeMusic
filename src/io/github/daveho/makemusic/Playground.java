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
	
	private static final int BPM = 100;
	
	private Thread thread;
	private PlayerTask task;
	private CompositionData compositionData;

	public Playground() {
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
		boolean done = false;
		while (!done) {
			System.out.print("> ");
			System.out.flush();
			
			String cmd = in.nextLine();
			if (cmd == null) {
				break;
			}
			
			cmd = cmd.trim();
			if (cmd.equals("demo")) {
				// Load demo CompositionData
				compositionData = createDemoCompositionData();
			} else if (cmd.startsWith("read ")) {
				String dirName = cmd.substring("read ".length()).trim();
				DirectoryCompositionDataSource source = new DirectoryCompositionDataSource(dirName);
				CompositionDataReader cdr = new CompositionDataReader();
				compositionData = cdr.read(source);
				System.out.println("Read composition data from " + dirName);
			} else if (cmd.startsWith("write ")) {
				if (compositionData == null) {
					System.out.println("No composition data to write");
				} else {
					String dirName = cmd.substring("write ".length()).trim();
					DirectoryCompositionDataSink sink = new DirectoryCompositionDataSink(dirName);
					CompositionDataWriter cdw = new CompositionDataWriter();
					cdw.write(compositionData, sink);
					System.out.println("Wrote composition data to " + dirName);
				}
			} else if (cmd.equals("start")) {
				if (compositionData == null) {
					System.out.println("No composition data to play");
				} else if (thread == null) {
					task = new PlayerTask();
					thread = new Thread(task);
					thread.start();
					System.out.println("Started player thread");
				}
			} else if (cmd.equals("stop")) {
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
			} else if (cmd.equals("quit")) {
				if (thread != null) {
					System.out.println("Player thread is running");
				} else {
					done = true;
				}
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
