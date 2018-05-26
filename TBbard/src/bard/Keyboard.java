package bard;

import java.awt.event.KeyEvent;

public class Keyboard {
	
	public static String IMG = "https://i.imgur.com/AwmwHOX.png";

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
	
	private int[] key = {KeyEvent.VK_Y, KeyEvent.VK_9, KeyEvent.VK_1, //C
			KeyEvent.VK_V, KeyEvent.VK_K, KeyEvent.VK_D, //C#
			KeyEvent.VK_U, KeyEvent.VK_0, KeyEvent.VK_2, //D
			KeyEvent.VK_B, KeyEvent.VK_L, KeyEvent.VK_F, //Eb
			KeyEvent.VK_I, KeyEvent.VK_Q, KeyEvent.VK_3, //E
			KeyEvent.VK_O, KeyEvent.VK_W, KeyEvent.VK_4, //F
			KeyEvent.VK_N, KeyEvent.VK_Z, KeyEvent.VK_G, //F#
			KeyEvent.VK_P, KeyEvent.VK_E, KeyEvent.VK_5, //G
			KeyEvent.VK_M, KeyEvent.VK_X, KeyEvent.VK_H, //G#
			KeyEvent.VK_A, KeyEvent.VK_R, KeyEvent.VK_6, //A
			KeyEvent.VK_COMMA, KeyEvent.VK_C, KeyEvent.VK_J, //Bb
			KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_7, //B
			KeyEvent.VK_8}; //C+2

	public int getKey(String note) {
		System.out.println("KBRD trying to find note matching: " + note);
		int index = 0;
		for(String s : notes){
			if(s.toLowerCase().equals(note)){
				return key[index];
			}
			index++;
		}
		return -1;
	}
	
	
	
}
