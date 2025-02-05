package com.medville2;

import java.nio.IntBuffer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.medville2.model.Game;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;
import com.medville2.view.ControlPanel;
import com.medville2.view.Renderer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter implements InputProcessor {
	private static final int HUD_WIDTH = 320;
	private static final int MAP_SIZE = 256;

	private SpriteBatch batch;

	private FitViewport viewport;
	private OrthographicCamera camera;
	private Vector2 mousePos;
	private BitmapFont font;

	private Renderer renderer;
	private ControlPanel controlPanel;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private Game game;

    private static final int maxV = 20;
    private int scrollVX;
    private int scrollVY;

    private int monitorWidth;

	@Override
    public void create() {
    	batch = new SpriteBatch();
    	DisplayMode displayMode = Gdx.graphics.getDisplayMode();
    	monitorWidth = displayMode.width;

		camera = new OrthographicCamera();
		viewport = new FitViewport(1920 - HUD_WIDTH, 1080, camera);
		mousePos = new Vector2();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);

	    hudCamera = new OrthographicCamera();
	    hudViewport = new FitViewport(HUD_WIDTH, 1080, hudCamera); // HUD viewport size
	    TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("medville_textures.atlas"));
		controlPanel = new ControlPanel(hudViewport, textureAtlas);
		controlPanel.foundTown();
		renderer = new Renderer(controlPanel, textureAtlas, MAP_SIZE);
		newGame(0.5, -2.0);

		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(this);
		im.addProcessor(controlPanel.getStage());

		Gdx.input.setInputProcessor(im);
        Gdx.app.setLogLevel(Application.LOG_INFO);

        IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
    	Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);
    	System.out.println("Mar buffer size: " + intBuffer.get());
    }

	private void newGame(double roughness, double waterThreshold) {
        Terrain terrain = new Terrain(MAP_SIZE, 32, roughness, waterThreshold);
        Calendar calendar = new Calendar();
        game = new Game(calendar, terrain);
        renderer.setGame(game);
	}

	@Override
	public void render() {
		input();
		logic();
		draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
		renderer.dispose();
		controlPanel.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width - HUD_WIDTH, height, true); // true centers the camera
		viewport.setScreenPosition(HUD_WIDTH * monitorWidth / 1920 + 3, 0);
        hudViewport.update(HUD_WIDTH, height, true); // Update HUD viewport as well
        hudViewport.setScreenPosition(0, 0);
	}

	private void logic() {
		game.tick();
	}

	private void draw() {
		ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
		viewport.apply();
		batch.setProjectionMatrix(viewport.getCamera().combined.scale(1f, 1f, 1f));
		batch.begin();

		final int zoomLevel;
		if (camera.zoom > 20) {
			zoomLevel = Renderer.ZOOM_LEVEL_BIRD_EYE;
		} else {
			zoomLevel = Renderer.ZOOM_LEVEL_CLOSE;
		}
		renderer.render(batch, zoomLevel);
		//font.draw(batch, "Upper left, FPS=" + Gdx.graphics.getFramesPerSecond(), 0, viewport.getWorldHeight());

		batch.end();

		hudViewport.apply();
        hudCamera.update();

        controlPanel.render(game.getCalendar());
	}

	private void input() {
		float speed = 1000f;
		float delta = Gdx.graphics.getDeltaTime();
		boolean moveKeyPressed = false;

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (scrollVX < maxV) {
				scrollVX += speed * delta;
			}
			moveKeyPressed = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (scrollVX > -maxV) {
				scrollVX -= speed * delta;
			}
			moveKeyPressed = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (scrollVY < maxV) {
				scrollVY += speed * delta;
			}
			moveKeyPressed = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (scrollVY > -maxV) {
				scrollVY -= speed * delta;
			}
			moveKeyPressed = true;
		}

		if (!moveKeyPressed) {
			if (scrollVX < 0) {
				scrollVX += speed * delta / maxV;
			}
			if (scrollVX > 0) {
				scrollVX -= speed * delta / maxV;
			}
			if (scrollVY < 0) {
				scrollVY += speed * delta / maxV;
			}
			if (scrollVY > 0) {
				scrollVY -= speed * delta / maxV;
			}
		}

		camera.position.add(scrollVX * camera.zoom, scrollVY * camera.zoom, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			dispose();
			System.exit(0);
		}
		camera.update();
		setProjectedViewport();

		if (Gdx.input.isTouched()) {
			mousePos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
		}
		Vector2 worldPos = mousePos.cpy();
		viewport.unproject(worldPos);
		renderer.setWorldPos(worldPos); // Convert the units to the world units of the viewport

		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			controlPanel.setCheckAllFields(!controlPanel.getCheckAllFields());
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			controlPanel.setShowAllMinerals(!controlPanel.getShowAllMinerals());
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			game.save("game.save");
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			game = Game.load("game.save");
			renderer.setGame(game);
			controlPanel.setActiveTown(game.getPlayer().firstTown());
			controlPanel.select();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			newGame(0.5, -2.0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
			newGame(1.0, 0.0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
			newGame(0.8, 2.0);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (screenX < HUD_WIDTH) {
			return false;
		}
		controlPanel.click(renderer.getActiveField(), game);
		return true;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mousePos.set(screenX, screenY);
		
		Vector2 worldPos = mousePos.cpy();
		viewport.unproject(worldPos);
		renderer.setWorldPos(worldPos);
		return true;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {		
		camera.zoom += (amountY / 100) * (Math.sqrt(camera.zoom) + 1);
		if (camera.zoom < 1) {
			camera.zoom = 1f;
		} else if (camera.zoom > 50) {
			camera.zoom = 50f;
		}
		setProjectedViewport();
		return true;
	}

	public void setProjectedViewport() {
		Vector2 v1 = new Vector2(0, 0);
		viewport.unproject(v1);
		Vector2 v2 = new Vector2(viewport.getWorldWidth(), viewport.getWorldHeight());
		viewport.unproject(v2);

		renderer.setProjectedViewport(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.abs(v2.x - v1.x), Math.abs(v2.y - v1.y));
	}
}
