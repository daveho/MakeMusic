package io.github.daveho.makemusic;

import java.util.Scanner;

import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.EffectsChainData;
import io.github.daveho.makemusic.data.GervillData;
import io.github.daveho.makemusic.data.MetronomeData;
import io.github.daveho.makemusic.data.PlayLiveData;
import io.github.daveho.makemusic.data.TrackData;
import io.github.daveho.makemusic.playback.CompositionPlayer;

import javax.sound.midi.MidiUnavailableException;

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

	public Playground() {
	}
	
	public CompositionPlayer createPlayer() {
		// FIXME: hard-coded stuff
		CompositionData compositionData = new CompositionData();
		
		addMetronomeTrack(compositionData);
		addPlayLiveTrack(compositionData);
		
		CompositionPlayer player = new CompositionPlayer(compositionData);
		return player;
	}

	private void addMetronomeTrack(CompositionData compositionData) {
		TrackData td = new TrackData();
		
		MetronomeData md = new MetronomeData();
		
		md.setProperty("intervalMs", (double)(60*1000)/BPM);
		
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
		plmgd.setProperty("patch", 54);
		td.setMessageGeneratorData(plmgd);
		
		GervillData gd = new GervillData();
		td.setSynthData(gd);
		
		EffectsChainData ecd = new EffectsChainData();
		td.setEffectsChainData(ecd);
		
		compositionData.addTrackData(td);
	}

	public void commandLoop() {
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
			if (cmd.equals("start")) {
				if (thread == null) {
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
			}
		}
		System.out.println("cya");
	}
	
	public static void main(String[] args) throws MidiUnavailableException {
		Playground playground = new Playground();
		playground.commandLoop();
	}
}
