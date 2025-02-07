package com.medville2.lwjgl3;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;


public class TextureHelper {

	public static HashMap<String, String> images = new HashMap<>();
	static {		
		images.put("grass", "grass.png");
		images.put("grass_cube", "grass_cube.png");
		images.put("river", "river_2.png");
		images.put("river_cube", "river_cube.png");
		images.put("water", "water.png");
		images.put("water_cube", "water_cube.png");
		images.put("hill", "hill.png");
		images.put("mountain", "mountain.png");
		images.put("rock", "rock.png");
		images.put("rock_cube", "rock_cube.png");
		images.put("tree", "tree.png");
		images.put("tree_cherry", "tree_cherry.png");
		images.put("tree_small", "tree_small.png");
		images.put("road", "cobble_road5.png");
		images.put("farm", "farm2.png");
		images.put("mine", "mine.png");
		images.put("bridge", "bridge.png");
		images.put("tower", "tower.png");
		images.put("townsquare", "townsquare.png");
		images.put("mill", "mill_2.png");
		images.put("workshop", "workshop_3.png");
		images.put("blacksmith", "blacksmith.png");
		images.put("wall", "wall_4.png");
		images.put("grain", "grain.png");
		images.put("fishnet", "fishnet.png");
		images.put("sheep", "sheep.png");

		images.put("water_sparkle_0", "water_sparkle_0.png");
		images.put("water_sparkle_1", "water_sparkle_1.png");
		images.put("water_sparkle_2", "water_sparkle_2.png");
		images.put("water_sparkle_3", "water_sparkle_3.png");
		images.put("water_sparkle_4", "water_sparkle_4.png");
		images.put("water_sparkle_5", "water_sparkle_5.png");
		images.put("water_sparkle_6", "water_sparkle_6.png");
		images.put("water_sparkle_7", "water_sparkle_7.png");

		images.put("selection_red", "selection_red.png");
		images.put("selection_green", "selection_green.png");
		images.put("arrow_small", "arrow_small.png");
		images.put("cancel", "cancel.png");

		images.put("storage", "storage.png");

		images.put("coin", "coin.png");
		images.put("head", "head.png");
		images.put("peasant", "peasant.png");

		images.put("artifact_sheep", "artifact_sheep.png");
		images.put("artifact_logs", "artifact_logs.png");
		images.put("artifact_fish", "artifact_fish.png");
		images.put("artifact_grain", "artifact_grain.png");
		images.put("artifact_stone", "artifact_stone.png");
		images.put("artifact_iron", "artifact_iron.png");
		images.put("artifact_gold", "artifact_gold.png");
		images.put("artifact_food", "artifact_food.png");
		images.put("artifact_planks", "artifact_planks.png");
		images.put("artifact_bricks", "artifact_bricks.png");
		images.put("artifact_wool", "artifact_wool.png");
		images.put("artifact_textile", "artifact_textile.png");
		images.put("artifact_pottery", "artifact_pottery.png");
		images.put("artifact_flour", "artifact_flour.png");
		images.put("artifact_leather", "artifact_leather.png");
		images.put("artifact_clay", "artifact_clay.png");
	}

	private static Map<String, Rectangle> regions = new HashMap<>();

	public static void combineTextures() throws IOException {
		Map<String, BufferedImage> images = new HashMap<>();
		for (Map.Entry<String, String> entry : TextureHelper.images.entrySet()) {
			//FileHandle fileHandle = Gdx.files.internal(entry.getValue());
			InputStream is = TextureHelper.class.getClassLoader().getResourceAsStream(entry.getValue());
			BufferedImage image = ImageIO.read(is);
			images.put(entry.getKey(), image);
		}

		int area = images.values().stream().mapToInt(t -> t.getWidth() * t.getHeight()).sum();
        int width = (int) Math.sqrt(area) * 2;
        int height = (int) Math.sqrt(area) * 2;

        // Create a FrameBuffer
        BufferedImage atlasImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = atlasImage.createGraphics();
        System.out.println(atlasImage.getWidth() + " " + atlasImage.getHeight());

        int x = 0;
        int y = 0;
        int maxH = 0;
        for (Entry<String, BufferedImage> entry : images.entrySet().stream().sorted((e1, e2) -> {
        	int w = Integer.compare(e1.getValue().getWidth(), e2.getValue().getWidth());
        	if (w == 0) {
        		return e1.getKey().compareTo(e2.getKey());
        	} else {
        		return w;
        	}
        }).toList()) {
        	BufferedImage t = entry.getValue();
        	// Render the textures
        	if (x + t.getWidth() >= width) {
        		x = 0;
        		y = y + maxH;
        		maxH = 0;
        	}
        	g2d.drawImage(t, x, y, null);
        	regions.put(entry.getKey(), new Rectangle(x, y, t.getWidth(), t.getHeight()));

        	x = x + t.getWidth() + 1;
        	maxH = Math.max(maxH, t.getHeight());
        }

        File pngOutput = new File("../assets/medville_textures.png");
        ImageIO.write(atlasImage, "png", pngOutput);
        System.out.println("Atlas image saved to " + pngOutput.getAbsolutePath());
        
        File atlasOutput = new File("../assets/medville_textures.atlas");
        atlasOutput.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(atlasOutput));
        
        writer.write("medville_textures.png");
        writer.newLine();
        writer.write("size: " + width + ", " + height);
        writer.newLine();
        writer.write("format: RGBA8888");
        writer.newLine();
        writer.write("filter: Linear,Linear");
        writer.newLine();
        writer.write("repeat: none");
        writer.newLine();
        
        for (String name : regions.keySet()) {
        	Rectangle r = regions.get(name);
        	writer.write(name);
            writer.newLine();
            writer.write("  rotate: false");
            writer.newLine();
            writer.write("  xy: " + r.x + ", " + r.y);
            writer.newLine();
            writer.write("  size: " + r.width + ", " + r.height);
            writer.newLine();
            writer.write("  orig: " + r.width + ", " + r.height);
            writer.newLine();
            writer.write("  offset: 0, 0");
            writer.newLine();
            writer.write("  index: -1");
            writer.newLine();
        }
        
        writer.close();
        g2d.dispose();
        
    }

	public static void main(String[] args) throws IOException {
		TextureHelper.combineTextures();
	}
}
