package org.bombercraft2.game.lights;

import org.bombercraft2.core.Render;
import org.bombercraft2.game.GameAble;
import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LightsManager implements InteractAbleG2 {
    private final BufferedImage    lightMapOrigin;
    private final ArrayList<Light> lights   = new ArrayList<>();
    private final GameAble         parent;
    private       BufferedImage    lightMap = null;

    public LightsManager(GameAble parent) {
        this.parent = parent;

        lightMapOrigin = new BufferedImage(parent.getCanvas().getWidth(),
                                           parent.getCanvas().getHeight(),
                                           BufferedImage.TYPE_INT_ARGB);


    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (!parent.getVisibleOption(Render.LIGHTS)) {
            return;
        }

        renderLightMap();
        if (parent.getVisibleOption(Render.ONLY_SHADOW_MAP)) {
            g2.setColor(Color.white);
            g2.fillRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        }
        //		drawLightMap();
        //		drawDynamicLightMap();
        g2.drawImage(lightMapOrigin, 0, 0, null);
    }

    private void renderLightMap() {

        Graphics2D tg2 = (Graphics2D) lightMapOrigin.getGraphics();
        tg2.setColor(Color.BLACK);
        tg2.fillRect(0, 0, lightMapOrigin.getWidth(), lightMapOrigin.getHeight());

        tg2.setComposite(AlphaComposite.DstOut);


        lights.stream()
              .filter(parent::isVisible)
//			  .filter(a->a.isStatic())
              .forEach(a -> a.render(tg2));
    }

    public void addLight(Light light) {
        lights.add(light);
    }
}
