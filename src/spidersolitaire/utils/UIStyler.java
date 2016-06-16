package spidersolitaire.utils;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UIStyler {

	public static void setWindowStyleUI() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
