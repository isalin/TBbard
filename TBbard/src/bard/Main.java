package bard;

public class Main {

	public static void main(String[] args) {
		//debug();
		new Settings();
		new GUI();
	}

	public static void debug(){
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Notes n = new Notes(0);
		//n.play("G(-1)");
		//n.play("G(+1)");
		n.play("A", null);
		//n.play("C");
	}
}
