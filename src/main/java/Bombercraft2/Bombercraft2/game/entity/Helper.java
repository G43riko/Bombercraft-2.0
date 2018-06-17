package Bombercraft2.Bombercraft2.game.entity;

import Bombercraft2.Bombercraft2.StaticConfig;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import org.jetbrains.annotations.NotNull;
import utils.Utils;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;

public abstract class Helper extends Entity<GameAble> {
    private final Helper.Type type;

    public enum Type implements Iconable {
        TOWER_LASER("icon_laser_tower", ""),
        TOWER_MACHINE_GUN("icon_machine_gun_tower", "machineGun"),
        SHOVEL("icon_shovel", ""),
        BOMB_NORMAL("icon_bomb", ""),
        WEAPON_LASER("icon_weapon_laser", ""),
        WEAPON_BOW("icon_weapon_bow", ""),
        WEAPON_LIGHTNING("icon_light", ""),
        WEAPON_STICK("icon_stick", ""),
        WEAPON_BOOMERANG("icon_bumerang", ""),
        OTHER_RESPAWNER("respawner", ""),
        OTHER_ADDUCTOR("adductor", "");
        private final Image  image;
        private final String name;

        Type(String imageName, String name) {
            this.name = name;
            image = ResourceLoader.loadTexture(imageName + StaticConfig.EXTENSION_IMAGE);
        }

        public String getName() {return name;}

        @NotNull
        public Image getImage() {return image;}
    }

    public final static String TOWER_ROCKET        = "towerRocket";
    public final static String TOWER_FLAME_THROWER = "towerFlameThrower";
    public final static String TOWER_SNIPER        = "towerSniper";

    public final static String BOMB_ATOM   = "bombAtom";
    public final static String BOMB_NANO   = "bombNano";
    public final static String BOMB_FIRE   = "bombFire";
    public final static String BOMB_FREEZE = "bombFreeze";
    public final static String BOMB_SLIME  = "bombSlime";
    public final static String BOMB_MINE   = "bombMine";

    public final static String WEAPON_BASIC   = "weaponBasic";
    public final static String WEAPON_GUN     = "weaponGun";
    public final static String WEAPON_ROCKET  = "weaponRocket";
    public final static String WEAPON_GRANADE = "weaponGranade";
    public final static String WEAPON_SHOTGUN = "weaponShotgun";

    protected Helper(GVector2f position, GameAble parent, Type type) {
        super(position, parent);
        this.type = type;
    }

    public final Type getType() {
        return type;
    }

    public static boolean isBomb(Type type) {
        return Utils.isIn(type, Helper.Type.BOMB_NORMAL);
    }

}
