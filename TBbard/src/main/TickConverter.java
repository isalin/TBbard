package main;

import javax.sound.midi.Sequence;

public class TickConverter {
	
	Sequence sequence;
	
	long sequenceLength = 0;
	long sequenceTicks = 0;
	
	public TickConverter(Sequence sequence) {
			
			System.out.println("Sequence (MS): " + sequence.getMicrosecondLength()/1000);
			sequenceLength = sequence.getMicrosecondLength()/1000;
			System.out.println("Sequence (ticks): " + sequence.getTickLength());
			sequenceTicks = sequence.getTickLength();
	
	}
	
	public long ticksToMillis(long ticks){
		return ticks * (sequenceLength/sequenceTicks);
	}
}
