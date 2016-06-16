package spidersolitaire.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

public class ImageEffects {
	public static void drawBrighterImage(Graphics2D g2d, BufferedImage im,
			int x, int y, float brightness) {
		if (im == null)
			return;

		if (brightness < 0.0f)
			brightness = 0.5f;

		RescaleOp brigherOp;
		if (hasAlpha(im)) {
			float[] scaleFactors = { brightness, brightness, brightness, 1.0f };
			float[] offsets = { 0.0f, 0.0f, 0.0f, 0.0f };
			brigherOp = new RescaleOp(scaleFactors, offsets, null);
		} else {
			brigherOp = new RescaleOp(brightness, 0, null);
		}

		g2d.drawImage(im, brigherOp, x, y);
	}

//	public static BufferedImage simpleCreateDropShadow(BufferedImage image) {
//		BufferedImage shadow = new BufferedImage(image.getWidth(),
//				image.getHeight(), BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2 = shadow.createGraphics();
//		g2.drawImage(image, 0, 0, null);
//		g2.setComposite(AlphaComposite.SrcIn);
//		g2.setColor(Color.BLACK);
//		g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());
//		g2.dispose();
//		return shadow;
//	}

	public static BufferedImage createDropShadow(BufferedImage image, int offset) {
		BufferedImage shadow = new BufferedImage(image.getWidth() + 4 * offset,
				image.getHeight() + 4 * offset, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = shadow.createGraphics();
		g2.drawImage(image, offset * 2, offset * 2, null);
		g2.setComposite(AlphaComposite.SrcIn);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());
		g2.dispose();
		shadow = getGaussianBlurFilter(200, true).filter(shadow, null);
		shadow = getGaussianBlurFilter(200, false).filter(shadow, null);
		return shadow;
	}

	// public static BufferedImage createDropShadow(BufferedImage image, int
	// size) {
	// BufferedImage shadow = new BufferedImage(image.getWidth() + 4 * size,
	// image.getHeight() + 4 * size, BufferedImage.TYPE_INT_ARGB);
	// Graphics2D g2 = shadow.createGraphics();
	// g2.drawImage(image, size * 2, size * 2, null);
	// g2.setComposite(AlphaComposite.SrcIn);
	// g2.setColor(Color.BLACK);
	// g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());
	// g2.dispose();
	// shadow = getGaussianBlurFilter(size, true).filter(shadow, null);
	// shadow = getGaussianBlurFilter(size, false).filter(shadow, null);
	// return shadow;
	// }

	public static ConvolveOp getGaussianBlurFilter(int radius,
			boolean horizontal) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be >= 1");
		}
		int size = radius * 2 + 1;
		float[] data = new float[size];
		float sigma = radius / 3.0f;
		float twoSigmaSquare = 2.0f * sigma * sigma;
		float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
		float total = 0.0f;
		for (int i = -radius; i <= radius; i++) {
			float distance = i * i;
			int index = i + radius;
			data[index] = (float) Math.exp(-distance / twoSigmaSquare)
					/ sigmaRoot;
			total += data[index];
		}
		for (int i = 0; i < data.length; i++) {
			data[i] /= total;
		}
		Kernel kernel = null;
		if (horizontal) {
			kernel = new Kernel(size, 1, data);
		} else {
			kernel = new Kernel(1, size, data);
		}
		return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	}

	public static boolean hasAlpha(BufferedImage im) {
		if (im == null)
			return false;

		int transparency = im.getColorModel().getTransparency();

		if ((transparency == Transparency.BITMASK)
				|| (transparency == Transparency.TRANSLUCENT))
			return true;
		else
			return false;
	}
}
