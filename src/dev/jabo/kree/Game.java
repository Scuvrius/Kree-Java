package dev.jabo.kree;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {	
	
	private Thread thread;
	private boolean gameRunning;
	
	private Graphics g;
	private BufferStrategy bs;
	
	private Window window;
	
	public static Input INPUT = new Input();
	
	public Game(Window window) {
		this.window = window;
	}

	public void Initialize() {
		window.getWindow().addKeyListener(INPUT);
		window.getCanvas().addMouseListener(INPUT);
		window.getCanvas().addMouseMotionListener(INPUT);
	}
	
	public void Update() {
		
		SceneManager.Update();
		
	}
	
	public void Render() {
		
		bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, window.getWindowWidth(), window.getWindowHeight());
		// DRAW HERE
		
		SceneManager.Render(g);
		
		// END
		bs.show();
		g.dispose();
		
	}
	
	public synchronized void start() {
		if(gameRunning)
			return;
		gameRunning = true;
		thread = new Thread(this);
		thread.run();
	}
	
	public synchronized void stop() {
		if(!gameRunning)
			return;
		gameRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	@Override
	public void run() {
		
		Initialize();
		
		// Handles the FPS
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
			
		while(gameRunning) {
			
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				Update();
				Render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}
			
		}
		
		stop();
		
	}
	
	
}
