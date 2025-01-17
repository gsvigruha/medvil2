package com.medville2.view.textures;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class WaterTexture {

	public static Texture createSineWaveTexture(float phase) {
		Pixmap pixmap = new Pixmap(256, 256, Pixmap.Format.RGBA8888);

		pixmap.setColor(0, 0, 0, 1);
		pixmap.fill();

		for (int x = 0; x < 256; x++) {
			for (int y = 0; y < 256; y++) {
				float h = (float) Math.sin(phase + x / 256f) / 10f; // Adjust wave frequency and amplitude
				pixmap.setColor(h, h, h, 1);
				pixmap.drawPixel(x, (int) y);
			}
		}

		Texture texture = new Texture(pixmap);
		pixmap.dispose();

		return texture;
	}
}
