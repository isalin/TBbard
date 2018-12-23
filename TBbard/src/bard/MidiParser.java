package bard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import org.omg.CosNaming.IstringHelper;

public class MidiParser {

	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	public static final String[] NOTE_NAMES = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B"};

	public static final String []octaves = {"-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

	String[] instruments;
	List<String> shownInstruments = new ArrayList<String>();
	String[][] sheets = new String[16][11];
	Integer[][] quality = new Integer[16][11];
	Integer[] totalNotes = new Integer[16];

	String filePath;

	String lastNote = "";
	int lastOctave;
	
	boolean trueTimings = false;
	
	boolean allNull = true;


	public MidiParser(String fileName, boolean trueTimings) {
		filePath = fileName;
		this.trueTimings = trueTimings;
	}

	public void parseFile(String filePath){

	}

	public void getNotes(String filePath, int instrumentIndex, int octaveTarget, boolean includeHoldRelease) throws Exception{

		includeHoldRelease = true;

		for(int ins = 0; ins < 16; ins++){
			totalNotes[ins] = 0;
			for(int oct = 0; oct < 11; oct++){
				sheets[ins][oct] = "";
				quality[ins][oct] = 0;
			}
		}


		Sequence sequence = MidiSystem.getSequence(new File(filePath));
		TickConverter converter = new TickConverter(sequence);

		System.out.println("Sequence (MS): " + sequence.getMicrosecondLength()/1000);
		System.out.println("Sequence (ticks): " + sequence.getTickLength());
		System.out.println("Target octave: " + octaveTarget);	

		for(String i : instruments){
			System.out.println("About to get notes, found instrument: " + i);
		}

		//if(true) return "";




		for (Track track :  sequence.getTracks()) {

			//System.out.println("Track " + trackNumber + ": size = " + track.size());



			long prevTick = 0;
			boolean firstLineDone = false;
			ShortMessage sm = null;
			for (int i=0; i < track.size(); i++) { 
				MidiEvent event = track.get(i);


				if(firstLineDone && (i+1 < track.size()) && (track.get(i+1).getMessage() instanceof ShortMessage)&& (track.get(i).getMessage() instanceof ShortMessage) && (track.get(i+1).getTick() - event.getTick()) < 2 && ((ShortMessage)track.get(i+1).getMessage()).getCommand() == NOTE_ON && ((ShortMessage)track.get(i).getMessage()).getCommand() == NOTE_ON) continue;



				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					sm = (ShortMessage) message;
					//System.out.println("Channel: " + sm.getChannel());





					if ((sm.getCommand() == NOTE_ON) && (sm.getData2() > 0)) {
						//trackNumber = track.size();
						totalNotes[sm.getChannel()]++;
						//int tick = Math.round((event.getTick() - prevTick)*sequence.getTickLength()/(sequence.getMicrosecondLength()/1000));
						String line = "";

						if(firstLineDone || trueTimings)line += "w" + converter.ticksToMillis(event.getTick() - prevTick) + "\n";
						prevTick = event.getTick();

						int key = sm.getData1();
						//System.out.println("DATA2: " + sm.getData2());
						int octave = (key / 12)-1;
						int note = key % 12;
						String noteName = NOTE_NAMES[note];
						//int velocity = sm.getData2();
						lastNote = noteName;
						lastOctave =  octave;
						//System.out.println("Tick: " + event.getTick() + " Note on, " + noteName + octave + " key=" + key);
						//System.out.println(noteName);
						if(includeHoldRelease) line += "h";
						line += noteName;

						//						if(octave < octaveTarget) sheets[sm.getChannel()] += "-1";
						//						if(octave > octaveTarget) sheets[sm.getChannel()] += "+1";

						firstLineDone = true;


						for(int o = 0; o < 11; o++){
							sheets[sm.getChannel()][o] += line;
							if(noteName.equals("C") && octave > o+1){
								sheets[sm.getChannel()][o] += "+2";
								quality[sm.getChannel()][o]++;
							} else {
								if(octave < o) {
									sheets[sm.getChannel()][o] += "-1";
									if(octave == o-1) quality[sm.getChannel()][o]++;
								}
								if(octave > o) {
									sheets[sm.getChannel()][o] += "+1";
									if(octave == o+1) quality[sm.getChannel()][o]++;
								}
								if(octave == o) quality[sm.getChannel()][o]++;
							}	
						}
						checkIfChannelIsIndexed(sm.getChannel());
					} else 

						if (includeHoldRelease && (sm.getCommand() == NOTE_OFF || (sm.getData2() == 0))) {

							if(i+1 <= track.size() && track.get(i+1).getMessage() instanceof ShortMessage && ((ShortMessage)track.get(i+1).getMessage()).getCommand() == NOTE_ON && (track.get(i+1).getTick() - event.getTick()) < 2){
								continue;
							}

							int key = sm.getData1();
							int octave = (key / 12)-1;
							int note = key % 12;
							String noteName = NOTE_NAMES[note];
							//System.out.println("Tick: " + event.getTick() + " Note off, " + noteName + octave + " key=" + key);
							//System.out.println("Previous note was: " + lastNote);
							if(lastNote.equals(noteName) && lastOctave == octave){
								//System.out.println("MATCH! Adding hold and release.");
								//addPrefixToPrevLine(sm.getChannel(), "h");
								String line = "";
								line += "w" + converter.ticksToMillis(event.getTick() - prevTick) + "\n";
								prevTick = event.getTick();
								for(int o = 0; o < 11; o++){
									sheets[sm.getChannel()][o] += "\n" + line;
								}
								addStringToAllSheets(sm.getChannel(), "release");
							}
							lastNote = "";
							lastOctave = -10;
						}

					if (sm.getCommand() == NOTE_ON || (includeHoldRelease && sm.getCommand() == NOTE_OFF)) {
						//prevTick = event.getTick();
						addStringToAllSheets(sm.getChannel(), "\n");
					}

				} 

			}

			//System.out.println();

		}
		
		shownInstruments = new ArrayList<String>();
		for(String ins : instruments) {
			System.out.println("PROCESSING INSTRUMENT: '" + ins + "'");

			if(ins != null) {
				shownInstruments.add(ins);
				allNull = false;
			}
		}

		for(int ins = 0; ins < 16; ins++){
			estimateOctaveQuality(ins);
			for(int oct = 0; oct < 11; oct++){
				sheets[ins][oct] = sheets[ins][oct].replaceAll("release\nw0\n", "");
				sheets[ins][oct] = sheets[ins][oct].replaceAll("\n+", "\n");
			}
		}



		return;

	}
	
	public int convertShownInstrument(String shown) {
		int index = 0;
		for(String instrument : instruments) {
			if(instrument != null && instrument.equals(shown)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public String[] getOctaveQuality(String shownInstrument){
		int instrument = convertShownInstrument(shownInstrument);
		
		for(int q = 0; q < 11; q++){
			System.out.println("Instrument: " + (instrument) + ", q: " + q);
			if (instrument < 0) octaves[q] = "What just happened?";
			else {
				octaves[q] = (q-1) + " (quality: " + (quality[instrument][q]) + "%)";
				System.out.println("Octave quality " + (q-1) + ": " + quality[instrument][q]);
			}
		} 
		return octaves;
	}

	private void estimateOctaveQuality(int instrument){
		if(sheets[instrument][0].equals("")) return;
		System.out.println("Processing quality of instrument " + instrument);
				
		boolean ignorePrint = true;
		for(int q = 0; q < 11; q++){
			if(quality[instrument][q] != 0) ignorePrint = false;
		}

		for(int q = 0; q < 11; q++){
			int qualityPercentage = 0;
			if(quality[instrument][q] != 0) qualityPercentage = (int)(((float)quality[instrument][q]/(float)totalNotes[instrument])*100);
			quality[instrument][q] = qualityPercentage;
			octaves[q] = (q-1) + " (quality: " + (qualityPercentage) + "%)";
			if(!ignorePrint) {
				System.out.println("Octave quality " + (q-1) + ": " + qualityPercentage);
			}
		}
		//System.out.println("Highest quality is octave " + (getHighestQualityOctave()-1) + " (index=" + getHighestQualityOctave() + ")");
	}

	private void checkIfChannelIsIndexed(int instrument) {
		if(instruments[instrument] != null) return;
		System.out.println("Instrument in channel " + instrument + " does not appear to be indexed. Setting it to unspecified.");
		try {
			
			if(true){
				if(instruments[instrument] == null) instruments[instrument] = (instrument + 1) + ". Unspecified";
				
			}



			//System.out.print(((ShortMessage) message).getData1());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(true)return;
		
		System.out.println("Checking if instrument is indexed...");
		if(shownInstruments.size() <= instrument || shownInstruments.get(instrument) == null) {
			System.out.println("This instrument doesn't seem to be indexed. Setting it to default piano...");
			shownInstruments.add((instrument+1) + ". " +"Acoustic Grand Piano");
		} else {
			System.out.println("This instrument appears to be properly indexed. All is good.");
		}
		
		
	}

	public int getHighestQualityOctave(String shownInstrument){
		int instrument = convertShownInstrument(shownInstrument);
		int highest = 6; //Octave 5 is the default
		System.out.println(quality.toString());
		for(int q = 0; q < 11; q++){
			if(quality[instrument][q] > quality[instrument][highest]) highest = q;
		}
		return highest;
	}

	private void addPrefixToPrevLine(int instrument, String string) {

		for(int o = 0; o < 11; o++){
			String lastNoteConverted = "";
			lastNoteConverted += lastNote;
			if(lastOctave < o) lastNoteConverted += "-1";
			if(lastOctave > o) lastNoteConverted += "+1";

			//System.out.println("\n\n--- CURRENT STATE ---\n" + sheets[instrument][o]);
			System.out.println("Note converted to: " + lastNoteConverted);
			sheets[instrument][o] = sheets[instrument][o].substring(0, sheets[instrument][o].lastIndexOf(lastNoteConverted)) + 
					string + 
					lastNoteConverted;
		}
	}

	private void addStringToAllSheets(int instrument, String s){
		for(int o = 0; o < 11; o++){
			sheets[instrument][o] += s;
		}
	}

	private String convertLastNote(int instrument){
		String out = "";
		for(int o = 0; o < 11; o++){
			out += lastNote;
			if(lastOctave < o) out += "-1";
			if(lastOctave > o) out += "+1";

		}
		System.out.println("Last note was converted to: " + out);
		return out;
	}

	public void getInstruments(String filePath) throws Exception {

		Sequence sequence = MidiSystem.getSequence(new File(filePath));


		instruments = new String[16];

		for (Track track :  sequence.getTracks()) {

			for (int i=0; i < track.size(); i++) { 

				MidiEvent event = track.get(i);



				MidiMessage message = event.getMessage();
				
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {

						try {
							Synthesizer syn;

							syn = MidiSystem.getSynthesizer();
							//System.out.print("Channel: " + sm.getChannel() + " ");



							syn.open(); 
							Instrument[] instr = syn.getDefaultSoundbank().getInstruments();

							//System.out.println();

							Pattern pattern = Pattern.compile("Instrument: (.*?)  +bank.*");
							Matcher matcher = pattern.matcher(instr[sm.getData1()].toString());
							
							if(matcher.matches()){
								if(instruments[sm.getChannel()] == null) instruments[sm.getChannel()] = (sm.getChannel() + 1) + ". " + matcher.group(1);
								else {
									if(!instruments[sm.getChannel()].contains(matcher.group(1))) {
										instruments[sm.getChannel()] += ", " + matcher.group(1);
									}
								}

							}



							//System.out.print(((ShortMessage) message).getData1());
						} catch (MidiUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}


			}
		}
		
		for(String instrument : instruments) {
			System.out.println("PROCESSING INSTRUMENT: '" + instrument + "'");

			if(instrument != null) {
				shownInstruments.add(instrument);
				allNull = false;
			}
		}
		
	}
	
	public void refreshInstruments() {
		
	}

	public String getSheet(String shownInstrument, int octave){
		int instrument = convertShownInstrument(shownInstrument);
		if(instrument == -1 || sheets[instrument][octave] == null) return "";
		else return sheets[instrument][octave];
	}


}
