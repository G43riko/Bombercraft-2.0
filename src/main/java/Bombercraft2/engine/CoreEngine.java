package Bombercraft2.engine;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import utils.Utils;
import utils.math.GVector2f;

import java.awt.*;
import java.awt.image.BufferStrategy;

public abstract class CoreEngine {
    private static boolean    renderTime = false;
    private static int        fps        = 60;
    private static int        ups        = 60;
    private        boolean    running    = false;
    private        float      actFPS     = fps;
    private        float      actUPS     = ups;
    private        float      actLoops   = 0;
    private        Window     window     = null;
    private final  Input      input      = new Input();
    private final  Canvas     canvas     = new Canvas();
    private        Graphics2D g2         = null;


    public CoreEngine(int fps, int ups, boolean renderTime) {
        defaultInit();
        CoreEngine.renderTime = renderTime;
        CoreEngine.fps = fps;
        CoreEngine.ups = ups;
    }

    public void run() {
        running = true;
        mainLoop();
    }

    protected void cleanUp() {
        window.dispose();
        window.removeAll();
        Input.cleanUp();
    }

    public void stop() {
        running = false;
    }

    private void mainLoop() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / ups;
        final double timeF = 1000000000 / fps;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0, loops = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            loops++;

            if (deltaU >= 1) {
                defaultInput();
                defaultUpdate((float) Math.min(deltaU, 2));
                ticks++;
                deltaU--;
                Utils.sleep(1);
            }

            if (deltaF >= 1) {
                defaultRender();
                frames++;
                deltaF--;
                Utils.sleep(1);
            }

            if (System.currentTimeMillis() - timer > 1000) {
                if (renderTime) {
                    System.out.println(String.format("ups: %s, fps: %s, LOOPS: %s", ticks, frames, loops));
                }

                Bombercraft.totalMessages = new GVector2f(Bombercraft.sendMessages, Bombercraft.receiveMessages);
                Bombercraft.sendMessages = 0;
                Bombercraft.receiveMessages = 0;
                actFPS = frames;
                actUPS = ticks;
                actLoops = loops;
                frames = 0;
                ticks = 0;
                loops = 0;
                timer += 1000;
            }

        }
    }

    //DEFAULT MAIN METHODS

    private void defaultInit() {
        window = new Window(this,
                            Config.WINDOW_DEFAULT_TITLE,
                            Config.WINDOW_DEFAULT_WIDTH,
                            Config.WINDOW_DEFAULT_HEIGHT);
        window.add(canvas);

        canvas.addMouseListener(input);
        canvas.addKeyListener(input);
        canvas.addMouseMotionListener(input);
        init();
    }

    private void defaultInput() {
        input();
    }

    private void defaultUpdate(float delta) {
        Input.update();
        update(delta);
    }

    private void defaultRender() {
        BufferStrategy buffer = canvas.getBufferStrategy();
        if (buffer == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g2 = (Graphics2D) buffer.getDrawGraphics();
        render(g2);

//		BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
//		render((Graphics2D)image.getGraphics());
//		g2.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);

        g2.dispose();
        buffer.show();
    }

    //MAIN METHODS

    protected void init() { }

    protected void input() { }

    protected void update(float delta) { }

    protected void render(Graphics2D g2) { }

    //GETTERS

    public Window getWindow() {
        return window;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    protected Graphics2D getG2() {
        return g2;
    }

    public Input getInput() {
        return input;
    }

    public float getLoops() {
        return actLoops;
    }

    public float getFPS() {
        return actFPS;
    }

    public float getUPS() {
        return actUPS;
    }

    public abstract void onResize();

    public abstract void onExit();

    public void onFocus() { }

    public void onBlur() { }
}

