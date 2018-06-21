package org.engine;

import org.bombercraft2.Bombercraft;
import org.bombercraft2.StaticConfig;
import org.bombercraft2.gui2.GuiTester;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.utils.Utils;
import utils.math.GVector2f;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.HashMap;

public abstract class CoreEngine {
    private static boolean    renderTime = false;
    private static int        fps        = 60;
    private static int        ups        = 60;
    @NotNull
    private final  Input      input      = new Input();
    @NotNull
    private final  Canvas     canvas     = new Canvas();
    private        boolean    running    = false;
    private        float      actFPS     = fps;
    private        float      actUPS     = ups;
    private        float      actLoops   = 0;
    private        Window     window     = null;
    private        Graphics2D g2         = null;

    /**
     * @param fps        - Požadovaný počet vykreslení za sekundu
     * @param ups        - Požadovaný počet updatov za sekundu
     * @param renderTime - či sa majú do konzoly vypysovať počty vykreslení a updatov
     */
    protected CoreEngine(int fps, int ups, boolean renderTime) {
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

    protected void stop() {
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
                            StaticConfig.WINDOW_DEFAULT_TITLE,
                            StaticConfig.WINDOW_DEFAULT_WIDTH,
                            StaticConfig.WINDOW_DEFAULT_HEIGHT);
        window.add(canvas);

        canvas.addMouseListener(input);
        canvas.addKeyListener(input);
        canvas.addMouseWheelListener(input);
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
            GuiTester.manager.createMainPanel(canvas);
            GuiTester.init();
            canvas.createBufferStrategy(3);
            return;
        }
        g2 = (Graphics2D) buffer.getDrawGraphics();
        RenderingHints rh = new RenderingHints(new HashMap<>());
        boolean good = false;
        rh.put(RenderingHints.KEY_ANTIALIASING,
               good ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        rh.put(RenderingHints.KEY_COLOR_RENDERING,
               good ? RenderingHints.VALUE_COLOR_RENDER_QUALITY : RenderingHints.VALUE_COLOR_RENDER_SPEED);
        rh.put(RenderingHints.KEY_INTERPOLATION,
               good ? RenderingHints.VALUE_INTERPOLATION_BICUBIC : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING,
               good ? RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
               good ? RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY : RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        rh.put(RenderingHints.KEY_FRACTIONALMETRICS,
               good ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        rh.put(RenderingHints.KEY_DITHERING,
               good ? RenderingHints.VALUE_DITHER_ENABLE : RenderingHints.VALUE_DITHER_DISABLE);
        rh.put(RenderingHints.KEY_RENDERING,
               good ? RenderingHints.VALUE_RENDER_QUALITY : RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHints(rh);
        render(g2);


        // GuiTester.manager.render(g2);
        // GuiTester.manager.update();
        // canvas.setCursor(GuiTester.manager.getCursor());
        /*
        final int maxEarthQuake = 6;
        int earthQuakeX = (int) (Math.random() * maxEarthQuake);
        int earthQuakeY = (int) (Math.random() * maxEarthQuake);

        BufferedImage image = new BufferedImage(canvas.getWidth() + maxEarthQuake * 2,
                                                canvas.getHeight() + maxEarthQuake * 2,
                                                BufferedImage.TYPE_INT_RGB);
        Graphics2D tmpG2 = (Graphics2D) image.getGraphics();
        render(tmpG2);
        g2.drawImage(image,
                     0,
                     0,
                     canvas.getWidth(),
                     canvas.getHeight(),
                     earthQuakeX,
                     earthQuakeY,
                     canvas.getWidth(),
                     canvas.getHeight(),
                     null);
        */

        g2.dispose();
        buffer.show();
    }

    //MAIN METHODS

    private void init() { }

    protected void input() { }

    protected void update(float delta) { }

    protected void render(@NotNull Graphics2D g2) { }


    @NotNull
    @Contract(pure = true)
    protected Window getWindow() {
        return window;
    }

    @NotNull
    @Contract(pure = true)
    public Canvas getCanvas() {
        return canvas;
    }

    @NotNull
    @Contract(pure = true)
    protected Graphics2D getG2() {
        return g2;
    }

    @NotNull
    @Contract(pure = true)
    public Input getInput() {
        return input;
    }

    @Contract(pure = true)
    public float getLoops() {
        return actLoops;
    }

    @Contract(pure = true)
    public float getFPS() {
        return actFPS;
    }

    @Contract(pure = true)
    public float getUPS() {
        return actUPS;
    }

    public abstract void onResize();

    public abstract void onExit();

    public void onFocus() { }

    public void onBlur() { }

}

