package main;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notes {

	Robot r;

	int horizontalButtonSpacing = 240;
	int vertialButtonSpacing = 55;

	boolean inUpper = true; 

	int originalMouseX = 0;
	int originalMouseY = 0;

	int scrollWaitConstant = 17; //1 frame

	static double cd = 200;
	static double waitMultiplier = 1;
	
	double lastTimestamp = 0;

	long waitTime = 0;

	int slowdownConstant = 0;
	
	int newXPos = 0;
	int newYPos =  0;
	
	boolean running = true;

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


	public Notes() {
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		originalMouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
		originalMouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
		System.out.println("Orig. X: " + originalMouseX + "\nOrig. Y: " + originalMouseY);
	}

	public Notes(int value) {
		cd = value;
	}

	public void play(String note){
		
		if(running == false) return;

		note = note.toLowerCase();

		Pattern pattern = Pattern.compile("(w|wait) ?(\\d+)");
		Matcher matcher = pattern.matcher(note.toLowerCase());
		if(matcher.matches()){
			waitTime = Long.parseLong((matcher.group(2)));
			System.out.println("Waiting for " + (waitTime + slowdownConstant));
			return;
		}

		pattern = Pattern.compile("(.b?#?)([+-][12])");
		matcher = pattern.matcher(note.toLowerCase());
		if(matcher.matches()){
			note = matcher.group(1) + "(" + matcher.group(2) + ")";
		}
		int index = 0;

		System.out.println("\n\nPlaying: " + note);

		for(String s : notes){
			if(s.toLowerCase().equals(note)){
				pressButton(index);
				break;
			}
			index++;

		}


	}

	private void pressButton(int i){
		i++; //For math 
		System.out.println("Pressing index: " + i);
		if((inUpper && i > 24) || (!inUpper && i <= 24)){
			try {

				if(inUpper) goToLower();
				if(!inUpper) goToUpper();
				inUpper = !inUpper;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("inUpper: " + inUpper);
		}

		System.out.println("Right: " + (i % 3) +
				"\nDown: " + (Math.ceil((float) i / 3)));

		if(i > 24) i = i - 24 + 12;

		newXPos = originalMouseX + getIndexX(i) * horizontalButtonSpacing;
		newYPos =  (originalMouseY + getIndexY(i) * vertialButtonSpacing);

		r.mouseMove(newXPos, newYPos);

		rightClick();
	}

	private int getIndexX(int i){
		int index = ((i % 3) - 1);
		if(index < 0) index = 2;
		return index;
	}

	private int getIndexY(int i){
		int index = (int) (Math.ceil((float) i / 3) - 1);
		if(index < 0) index = 0;
		return index;
	}

	private void rightClick() {

		checkWaitTime();

		checkInterrupt();

		r.mousePress(InputEvent.BUTTON3_MASK);
		r.delay(1);
		r.mouseRelease(InputEvent.BUTTON3_MASK);

		lastTimestamp = System.currentTimeMillis();

	}

	private void checkInterrupt() {
		if(!((newXPos-5) < (int)MouseInfo.getPointerInfo().getLocation().getX() && (int)MouseInfo.getPointerInfo().getLocation().getX() < (newXPos+5))) {
			running = false;
		}
		
		if(!((newYPos-5) < (int)MouseInfo.getPointerInfo().getLocation().getY() && (int)MouseInfo.getPointerInfo().getLocation().getY() < (newYPos+5))) {
			running = false;
		}
		
	}

	private void checkWaitTime(){

		waitTime = (long) (waitTime*waitMultiplier + slowdownConstant);
		
		

		if(cd > waitTime){
			if(lastTimestamp + cd > System.currentTimeMillis())
				
				
				
				try {
					Thread.sleep((long) (lastTimestamp + cd - System.currentTimeMillis()));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		if(cd < waitTime){
			if(lastTimestamp + waitTime > System.currentTimeMillis())
				try {
					Thread.sleep((long) (lastTimestamp + waitTime - System.currentTimeMillis()));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}




		waitTime = 0;
	}


	private void goToUpper() throws Exception{
		r.setAutoDelay(scrollWaitConstant); //UI requires a frame...
		r.mouseWheel(-50);
		r.mouseWheel(-50);
		r.mouseWheel(-50);
		r.mouseWheel(-50);
		r.setAutoDelay(0);

	}

	private void goToLower() throws Exception{
		r.setAutoDelay(scrollWaitConstant);
		r.mouseWheel(50);
		r.mouseWheel(50);
		r.mouseWheel(50);
		r.mouseWheel(50);
		r.setAutoDelay(0);


	}

	public void parseSlowdownConstant(String allNotes){
		slowdownConstant = 0;

		for (String line : allNotes.split("\\n")){
			for (String l : line.split(" ")){
				Pattern pattern = Pattern.compile("(w|wait) ?(\\d+)");
				Matcher matcher = pattern.matcher(l.toLowerCase());
				if(matcher.matches()){
					waitTime = (long) (Long.parseLong((matcher.group(2)))*waitMultiplier);

					if(waitTime < scrollWaitConstant*4){
						slowdownConstant = (int) (scrollWaitConstant*4 - waitTime);
					}

				}

			}
		};

		System.out.println("SlowdownConstant: " + slowdownConstant);
	}
}
