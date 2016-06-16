package spidersolitaire.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtils {

	public static void writeObj(Object obj, String fileLabel)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileLabel);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	public static Object readeObj(String fileLabel) throws IOException {
		try {
			return new ObjectInputStream(new FileInputStream(fileLabel))
					.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
