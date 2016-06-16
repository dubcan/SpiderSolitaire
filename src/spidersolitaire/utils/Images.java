package spidersolitaire.utils;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {

	/**
	 * Загрузка изображения по названию. Изображение загружается через поток
	 * ресурсов. Далее создается и в итоге возвращается новое пустое изображение
	 * в которое рисуется загруженное изображение.
	 * 
	 * @param imageName
	 *            имя картинки
	 * @return буфферед имаге с картинкой
	 */
	public static BufferedImage load(String imageName) {
		BufferedImage image;

		try {
			image = ImageIO.read(Images.class.getResourceAsStream(imageName));
			BufferedImage img = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			img.getGraphics().drawImage(image, 0, 0, null);
			img.getGraphics().dispose();
			image = img;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return image;
	}

	/**
	 * Режет изображение на кусочки и загоняет эти кусочки в массив.
	 * 
	 * @param imageName
	 *            название загружаемого изображения
	 * @param sliceWidth
	 *            ширина вырезанного кусочка
	 * @param sliceHeight
	 *            высота вырезанного кусочка
	 * @return двумерный массив с вырезанными кусочками
	 */
	public static BufferedImage[][] cut(String imageName, int sliceWidth,
			int sliceHeight) {
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(Images.class.getResourceAsStream(imageName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		int w = sheet.getWidth();
		int h = sheet.getHeight();

		int xSlices = w / sliceWidth;
		int ySlices = h / sliceHeight;

		BufferedImage[][] images = new BufferedImage[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++)
			for (int y = 0; y < ySlices; y++) {
				BufferedImage img = new BufferedImage(sliceWidth, sliceHeight,
						BufferedImage.TYPE_INT_ARGB);
				img.getGraphics().drawImage(sheet, -x * sliceWidth, -y * sliceHeight, null);
				img.getGraphics().dispose();
				images[x][y] = img;
			}

		return images;
	}
}