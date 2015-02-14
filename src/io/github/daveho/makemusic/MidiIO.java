package io.github.daveho.makemusic;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class MidiIO {
	public static void save(Writer writer, List<MidiMessageAndTimeStamp> messages) throws IOException {
		for (MidiMessageAndTimeStamp msgAndTs : messages) {
			writer.write(asString(msgAndTs) + "\n");
		}
	}

	public static List<MidiMessageAndTimeStamp> load(Reader reader) throws IOException, InvalidMidiDataException {
		List<MidiMessageAndTimeStamp> result = new ArrayList<MidiMessageAndTimeStamp>();
		BufferedReader rdr = new BufferedReader(reader);
		
		while (true) {
			String line = rdr.readLine();
			if (line == null) {
				break;
			}
			if (line.trim().equals("")) {
				continue;
			}
			result.add(fromString(line));
		}
		
		return result;
	}
	
	private static String asString(MidiMessageAndTimeStamp msgAndTs) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(msgAndTs.timeStamp);
		buf.append(" ");
		buf.append(msgAndTs.msg.getClass().getSimpleName());
		for (byte b : msgAndTs.msg.getMessage()) {
			buf.append(" ");
			buf.append(b);
		}
		
		return buf.toString();
	}
	
	private static MidiMessageAndTimeStamp fromString(String s) throws InvalidMidiDataException {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(new StringReader(s));
		long timeStamp = in.nextLong();
		String messageType = in.next();
		ArrayList<Integer> data = new ArrayList<Integer>();
		while (in.hasNextInt()) {
			data.add(in.nextInt());
		}
		
		MidiMessage msg;
		if (messageType.equals("ShortMessage")) {
			switch (data.size()) {
			case 1:
				msg = new ShortMessage(data.get(0)); break;
			case 2:
				msg = new ShortMessage(data.get(0), data.get(1), 0); break;
			case 3:
				msg = new ShortMessage(data.get(0), data.get(1), data.get(2)); break;
			case 4:
				msg = new ShortMessage(data.get(0), data.get(1), data.get(2), data.get(3)); break;
			default:
				throw new IllegalArgumentException("Short message with " + data.size() + " bytes?");
			}
		} else if (messageType.equals("MetaMessage")) {
			throw new IllegalArgumentException("MetaMessages aren't handled yet");
		} else if (messageType.equals("SysexMessage")) {
			throw new IllegalArgumentException("SysexMessages aren't handled yet");
		} else {
			throw new IllegalArgumentException("Unknown MidiMessage type " + messageType);
		}
		
		return new MidiMessageAndTimeStamp(msg, timeStamp);
	}
}
