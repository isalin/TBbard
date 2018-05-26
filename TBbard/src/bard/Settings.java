package bard;

import java.util.prefs.Preferences;

public class Settings {
	
	static Preferences pref;
	
	public Settings() {
		try {
			pref = Preferences.userNodeForPackage(Settings.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void SaveBool(String key, boolean value){
		try {
			pref.putBoolean(key, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean LoadBool(String key){
		if(pref != null) return pref.getBoolean(key, false);
		return false;
	}
	
	public static void SaveInt(String key, int value){
		try {
			pref.putInt(key, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int LoadInt(String key){
		if(pref != null) return pref.getInt(key, -1);
		return -1;
	}
	
	public static void SaveDouble(String key, double value){
		try {
			pref.putDouble(key, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double LoadDouble(String key){
		if(pref != null) return pref.getDouble(key, -1);
		return -1;
	}
	
}
