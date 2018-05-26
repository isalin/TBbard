package bard;

import javax.sound.midi.Sequence;

public class TickConverter {
	
	Sequence sequence;
	
	float sequenceLength = 0;
	float sequenceTicks = 0;
	
	public TickConverter(Sequence sequence) {
			
			System.out.println("Sequence (MS): " + sequence.getMicrosecondLength()/1000);
			sequenceLength = sequence.getMicrosecondLength()/1000;
			System.out.println("Sequence (ticks): " + sequence.getTickLength());
			sequenceTicks = sequence.getTickLength();
	
	}
	
	public long ticksToMillis(long ticks){
		//System.out.println("Converting: " + ticks);
		//System.out.println("Output: " + (sequenceLength/sequenceTicks));
		return (long)(ticks * (sequenceLength/sequenceTicks));
	}
}
