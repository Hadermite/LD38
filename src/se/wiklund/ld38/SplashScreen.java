package se.wiklund.ld38;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.ld38.game.Game;

public class SplashScreen extends State implements LineListener {
	
	private Engine engine;
	private View view;
	private boolean done = false;
	private float timer = 0;
	
	public SplashScreen(Engine engine) {
		this.engine = engine;
		view = new View(new Texture("/textures/splash_screen.png"), new Transform(0, 0, 1920, 1080));
		addSubview(view);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				playIntroSound();
			}
		}).start();
	}
	
	@Override
	public void update(float delta) {
		timer += delta;
		if (done) {
			done = false;
			view.setTexture(new Texture("/textures/splash_screen_info.png"));
			timer = 0;
		}
		
		if (timer >= 15) {
			engine.setState(new Game(engine));
		}
	}
	
	private void playIntroSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Main.class.getResource("/sound/intro.wav"));
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(this);
			clip.open(audioInputStream);
			clip.start();
			waitUntilDone();
			
			clip.close();
			audioInputStream.close();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void update(LineEvent event) {
		Type eventType = event.getType();
		if (eventType == Type.STOP || eventType == Type.CLOSE) {
			done = true;
			notifyAll();
		}
	}

	public synchronized void waitUntilDone() throws InterruptedException {
		while (!done) {
			wait();
		}
	}
}
