package bard;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notes {

	static Robot r;
	static int heldKey = -1;
	static int heldStep = -1;
	static int heldMod = -1;
	String lastNote = "null";

	double waitMultiplier = 1;

	double lastTimestamp = 0;

	long waitTime = 0;

	int slowdownConstant = 0;

	boolean running = true;
	boolean holdNotes = true;

	boolean fullKeyboard = false;
	Keyboard kbrd = new Keyboard();

	boolean octaveSleep = false;
	boolean lastNoteSteppedOutOfOctave = false;

	static int fps = 0;

	private String[] notes = {"C(-1)", "C", "C(+1)",
			"C#(-1)", "C#", "C#(+1)",
			"D(-1)", "D", "D(+1)",
			"Eb(-1)", "Eb", "Eb(+1)",
			"E(-1)", "E", "E(+1)",
			"F(-1)", "F", "F(+1)",
			"F#(-1)", "F#", "F#(+1)",
			"G(-1)", "G", "G(+1)",
			"G#(-1)", "G#", "G#(+1)",
			"A(-1)", "A", "A(+1)",
			"Bb(-1)", "Bb", "Bb(+1)",
			"B(-1)", "B", "B(+1)",
			"C(+2)"
	};

	/**
	 * Array to hold the keys corresponding to each note
	 * TODO Make this configurable by the user
	 */
	private static int[] keys = {KeyEvent.VK_1,
			KeyEvent.VK_1,
			KeyEvent.VK_2,
			KeyEvent.VK_3,
			KeyEvent.VK_3,
			KeyEvent.VK_4,
			KeyEvent.VK_4,
			KeyEvent.VK_5,
			KeyEvent.VK_5,
			KeyEvent.VK_6,
			KeyEvent.VK_7,
			KeyEvent.VK_7,
			KeyEvent.VK_8
	};

	/**
	 * Array to hold the keys corresponding to the modifiers for the different
	 * TODO Make this configurable by the user
	 */
	private static int[] octaveModifiers = {
			KeyEvent.VK_RIGHT,
			KeyEvent.VK_LEFT
	};

	private static int[] stepModifiers = {
			KeyEvent.VK_UP,
			KeyEvent.VK_DOWN
	};


	public Notes(int fps) {
		this.fps = fps;
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void play(String note, String nextNote) {


		if(running == false) return;

		trackKey(note);

		octaveSleep = false;

		if(((note.contains("b") && heldStep != stepModifiers[0]) ||
				(note.contains("#") && heldStep != stepModifiers[1])
				|| (!note.contains("b") && !note.contains("#") && heldStep != -1)
				)) {
			System.out.println("Clearing step");
			clearStep();
			if(note.contains("b")) {
				r.keyPress(stepModifiers[0]);
				heldStep = stepModifiers[0];
			}
			if(note.contains("#")) {
				r.keyPress(stepModifiers[1]);
				heldStep = stepModifiers[1];
			}
			octaveSleep = true;
		}




		note = note.toLowerCase();
		if(nextNote != null) nextNote = nextNote.toLowerCase();

		System.out.println("\n--- " + note + " ---");
		System.out.println("NextNote: " + nextNote);
		System.out.println("LastNote: " + lastNote);




		boolean hold = false;

		Pattern pattern = Pattern.compile("(r|release)");
		Matcher matcher = pattern.matcher(note.toLowerCase());
		if(matcher.matches()){
			pressButton(-1, hold, nextNote);
			System.out.println("\nReleasing key.\n");
			return;
		}

		pattern = Pattern.compile("(w|wait) ?(\\d+)");
		matcher = pattern.matcher(note.toLowerCase());
		if(matcher.matches()){
			waitTime = Long.parseLong((matcher.group(2)));

			waitTime = (long) (waitTime*waitMultiplier);
//			if(waitTime < slowdownConstant){
//				System.out.println("Playback faster than framerate. Increasing wait.");
//				waitTime = slowdownConstant;
//			}

			boolean nextNoteIsSame = false;
			if(nextNote != null && lastNote.equals(nextNote)){
				System.out.println("The next note is the same as the held note. Adjusting wait time by " + slowdownConstant*2 + " ms (your fps delay), and releasing held key.");
				waitTime -= slowdownConstant*2;
				nextNoteIsSame = true;
			}

			System.out.println("Waiting for " + (waitTime));
			try {
				if(waitTime < 0){
					System.out.println("WaitTime is less than 0. Ignoring...");
				} else {
					if(waitTime > 0 && waitTime < 1000/fps) waitTime = 1000/fps;
					Thread.sleep((long) (waitTime));
					if(nextNoteIsSame){
						releaseHeldKey(false);
						Thread.sleep((long) (slowdownConstant*2));
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			waitTime = 0;
			return;
		}


		pattern = Pattern.compile("(h|hold) ?(.b?#?[+-]?[12]?)");
		matcher = pattern.matcher(note.toLowerCase());
		if(matcher.matches()){
			if(holdNotes){
				hold = true;
				System.out.println("Hold note.");
			}
			lastNote = note;
			note = matcher.group(2);

		} else {
			if(holdNotes){
				System.out.println("Don't hold note.");
			}
		}


		pattern = Pattern.compile("(.b?#?)([+-][12])");
		matcher = pattern.matcher(note.toLowerCase());
		if(matcher.matches()){
			note = matcher.group(1) + "(" + matcher.group(2) + ")";
			lastNote = note;

		}

		System.out.println("Playing: " + note);

		int index = 0;

		if(fullKeyboard){
			releaseHeldKey(false);
			press(kbrd.getKey(note), hold);
		} else {
			for(String s : notes){
				if(s.toLowerCase().equals(note)){

					pressButton(index, hold, nextNote);
					break;
				}
				index++;
			}
		}
	}


	int currentlyHeldKeys = 0;
	private void trackKey(String note) {
		if(note.startsWith("w") && !note.equals("w0")){
			currentlyHeldKeys = 0;
		} else {
			currentlyHeldKeys++;
		}
	}

	private void clearStep() {
		if(heldStep != -1) {
			r.keyRelease(heldStep);
			heldStep = -1;
		}
	}

	private void pressButton(int i, boolean hold, String nextNote) {


		//checkWaitTime();
		releaseHeldKey(false);
		System.out.println("Pressing index: " + i);
		if(i == -1){
			//			releaseHeldKey();
			return;
		}


		/* Doing a modulo operation on the index with 3 (Because there are 3 notes, one for each octave in the note table)
		 * With this we can get the index of the octave variation of the note the index points to */
		switch(i % 3) {
		case 0:
			if(heldMod == this.octaveModifiers[0]) break;
			if(heldMod == this.octaveModifiers[1]) {
				r.keyRelease(this.octaveModifiers[1]);
				try {
					Thread.sleep((long) (1000/fps));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			r.keyPress(this.octaveModifiers[0]);
			heldMod = this.octaveModifiers[0];
			System.out.println("Going down");
			octaveSleep = true;
			break;
		case 2:
			if(heldMod == this.octaveModifiers[1]) break;
			if(heldMod == this.octaveModifiers[0]) {
				r.keyRelease(this.octaveModifiers[0]);
				try {
					Thread.sleep((long) (1000/fps));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			r.keyPress(this.octaveModifiers[1]);
			heldMod = this.octaveModifiers[1];
			System.out.println("Going Up");
			octaveSleep = true;
			break;
		}
		//r.waitForIdle();


		if(octaveSleep == true || (lastNoteSteppedOutOfOctave == true && System.currentTimeMillis() - lastTimestamp < 1000/fps)) {
			try {
				Thread.sleep((long) (1000/fps));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(lastNoteSteppedOutOfOctave) lastNoteSteppedOutOfOctave = false;
		} else {
			if(lastNoteSteppedOutOfOctave == true) lastNoteSteppedOutOfOctave = false;
		}


		/* Doing integer division with 3 (Because there are 3 notes, one for each octave in the note table we got the index from)
		 * With this we can get the index of the note of that pseudo-row the index points to */
		press(i / 3, hold);
		//r.waitForIdle();

		/* Likewise as the first switch statement here */
		System.out.println("Octave check says next note is: " + nextNote);
		if(!hold){
			switch(i % 3) {
			case 0:
				if(nextNote == null || !nextNote.contains("-1")) {
					r.keyRelease(this.octaveModifiers[0]);
					System.out.println("Releasing Down");
					lastNoteSteppedOutOfOctave = true;
					heldMod = -1;
				}
				break;
			case 2:
					if(nextNote == null || !nextNote.contains("+1")) {
						r.keyRelease(this.octaveModifiers[1]);
						System.out.println("Releasing Up");
						lastNoteSteppedOutOfOctave = true;
						heldMod = -1;
					}
				break;
			}
			//r.waitForIdle();
		}
		if(lastNoteSteppedOutOfOctave == true) {
			lastNoteSteppedOutOfOctave = false;
			try {
				Thread.sleep((long) (1000/fps));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void press(int key, boolean hold) {
		if(key == -1){
			System.out.println("Key does not exist.");
			return;
		}

		int keyToPress = 0;
		if(fullKeyboard){
			keyToPress = key;
		} else {
			keyToPress = this.keys[key];
		}

		r.keyPress(keyToPress);
		r.delay(1);
		if(hold){
			heldKey = keyToPress;
		} else {
			r.keyRelease(keyToPress);
		}

		lastTimestamp = System.currentTimeMillis();
		r.waitForIdle();
	}

	public static void releaseHeldKey(boolean all){
		System.out.println("Releasing key:" + heldKey);
		if(heldKey != -1){
			r.keyRelease(heldKey);
			heldKey = -1;
		}
		if(all && heldMod != -1){
			r.keyRelease(heldMod);
			heldMod = -1;
			try {
				Thread.sleep((long) (1000/fps));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			r.waitForIdle();
		}

		if(all){
			for(int i : octaveModifiers){
				r.keyRelease(i);
			}
			for(int i : stepModifiers){
				r.keyRelease(i);
			}
			for(int i : keys){
				r.keyRelease(i);
			}
		}

		//r.delay(1);
		//r.waitForIdle();
	}

	private void checkWaitTime(){

		waitTime = (long) (waitTime*waitMultiplier);

		try {
			Thread.sleep((long) (waitTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		waitTime = 0;
	}
}
