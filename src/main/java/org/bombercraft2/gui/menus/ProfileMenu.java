package org.bombercraft2.gui.menus;

import org.bombercraft2.Profile;
import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.GameStateType;
import org.bombercraft2.core.MenuAble;
import org.bombercraft2.core.Texts;
import org.bombercraft2.gui.components.Button;
import org.bombercraft2.gui.components.GuiComponent;
import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ProfileMenu extends Menu {
    private final ArrayList<String> availableProfiles = loadProfiles();
    private final MenuAble          parent;

    public ProfileMenu(MenuAble parent) {
        super(parent, GameStateType.ProfileMenu);
        this.parent = parent;
        position.setY(100);
        init();
    }

    @Override
    public void doAct(BVector2f click) {
        availableProfiles.forEach(a -> {
            if (components.get(a).isClickIn(click)) {
                selectProfile(a);
            }
        });
        if (components.get(Texts.PLAY_AS_GUEST).isClickIn(click)) {
            selectGuestProfile();
        }
        if (components.get(Texts.CREATE_PROFILE).isClickIn(click)) {
            createProfile();
        }
        if (components.get(Texts.EXIT_GAME).isClickIn(click)) {
            parent.exitGame();
        }
    }

    @Override
    public void onResize() {
        for (GuiComponent component : components.values()) {
            component.calcPosAndSize();
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, size.getXi(), size.getYi());
        super.render(g2);
    }

    @Override
    public void calcPosition() {

    }

    private void init() {
        setItem(Texts.PLAY_AS_GUEST);
        availableProfiles.forEach(a -> addComponent(a, new Button(this, a)));

        setItem(Texts.CREATE_PROFILE);
        components.get(Texts.CREATE_PROFILE).setDisable(true);

        setItem(Texts.EXIT_GAME);
    }

    private void selectGuestProfile() {
        parent.setProfile(Profile.GUEST);
        parent.showMainMenu();
    }

    private void selectProfile(String profile) {
        parent.setProfile(profile);
        parent.showMainMenu();
    }

    private void createProfile() {
        System.out.println("create profile: ");
    }

    private ArrayList<String> loadProfiles() {
        ArrayList<String> result = new ArrayList<>();
        URL url = ResourceLoader.getURL(StaticConfig.FOLDER_PROFILE);

        if (url != null) {
            try {
                File dir = new File(url.toURI());

                for (File nextFile : dir.listFiles()) {
                    result.add(nextFile.getName().replace(StaticConfig.EXTENSION_PROFILE, ""));
                }
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
