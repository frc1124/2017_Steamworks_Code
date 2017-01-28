package utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TableManager {

	public static void put(String string, String string2, double value) {
		NetworkTable.getTable(string).putNumber(string2, value);
	}

	public static void put(String string, String string2, boolean value) {
		NetworkTable.getTable(string).putBoolean(string2, value);
	}

}
