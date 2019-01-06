package org.bombercraft2;

import org.glib2.math.vectors.GVector2f;

import java.awt.*;

public class StaticConfig {
    public final static String  WINDOW_DEFAULT_TITLE       = "Bombercraft 2.0";
    public final static int     WINDOW_DEFAULT_WIDTH       = 800;
    public final static int     WINDOW_DEFAULT_HEIGHT      = 600;
    public final static int     WINDOW_DEFAULT_FPS         = 60;
    public final static int     WINDOW_DEFAULT_UPS         = 60;
    public final static boolean WINDOW_DEFAULT_RENDER_TEXT = false;

    public final static int       BOMB_WIDTH      = 64;
    public final static int       BOMB_HEIGHT     = 64;
    public final static GVector2f BOMB_SIZE       = new GVector2f(StaticConfig.BOMB_WIDTH, StaticConfig.BOMB_HEIGHT);
    public final static Color     BOMB_AREA_COLOR = new Color(255, 255, 0, 100);
    public final static int       BOMB_AREA_ROUND = 10;

    public final static int       SUBMENU_WIDTH                  = 250;
    public final static int       SUBMENU_LINE_HEIGHT            = 30;
    public final static GVector2f SUBMENU_DEFAULT_POSITION       = new GVector2f(5, 5);
    public final static Color     SUBMENU_BORDER_COLOR           = Color.WHITE;
    public final static int       SUBMENU_BORDER_WIDTH           = 1;
    public final static Color     SUBMENU_FILL_COLOR             = Color.LIGHT_GRAY;
    public final static Color     SUBMENU_ACTIVE_ITEM_FILL_COLOR = Color.DARK_GRAY;
    public final static Color     SUBMENU_FONT_COLOR             = Color.WHITE;
    public final static int       SUBMENU_FONT_HORIZONTAL_OFFSET = 5;
    public final static int       SUBMENU_FONT_SIZE              = 20;
    public final static int       SUBMENU_FONT_VERTICAL_OFFSET   = 5;
    public final static int       SUBMENU_ICON_OFFSET            = 3;
    public final static Color     SUBMENU_ICON_BORDER_COLOR      = Color.BLACK;
    public final static int       SUBMENU_ICON_BORDER_WIDTH      = 1;
    public final static int       SUBMENU_ROUND                  = 10;
    public final static int       SUBMENU_LINE_ROUND             = 5;

    public final static int   LOG_TEXT_SIZE                = 16;
    public final static Color LOG_TEXT_COLOR               = Color.white;
    public final static Color LOG_BG_COLOR                 = new Color(0, 0, 0, 200);
    public final static Color LOG_BORDER_COLOR             = Color.white;
    public final static int   LOG_BORDER_WIDTH             = 2;
    public final static float WATER_TILE_SPEED_COEFFICIENT = 0.7f;

    public final static int   HEALTH_BAR_VERTICAL_OFFSET   = 0;
    public final static int   HEALTH_BAR_HORIZONTAL_OFFSET = 1;
    public final static int   HEALTH_BAR_HORIZONTAL_HEIGHT = 3;
    public final static int   HEALTH_BAR_BORDER_WIDTH      = 1;
    public static final Color HEALTH_BAR_BORDER_COLOR      = Color.WHITE;
    public static final Color HEALTH_BAR_FILL_COLOR        = Color.GREEN;

    public static final Color PATH_BORDER_COLOR = Color.WHITE;
    public static final Color PATH_FILL_COLOR   = Color.WHITE;
    public static final float PATH_NORMAL_WIDTH = 1.0f;
    public static final float PATH_BOLD_WIDTH   = 6.0f;
    public static final float PATH_DASH_GAP     = 90.0f;
    public final static float PATH_SPEED        = 3.0f;


    public final static Color BAR_BACKGROUND_COLOR = Color.WHITE;
    public final static Color BAR_BORDER_COLOR     = Color.DARK_GRAY;
    public final static int   BAR_BORDER_WIDTH     = 3;

    public final static GVector2f NAV_BAR_SIZE                = new GVector2f(40, 40);
    public final static int       NAV_BAR_BOTTOM_OFFSET       = 20;
    public final static int       NAV_BAR_NUMBER_OF_BLOCKS    = 19;
    public final static Color     NAV_BAR_BACKGROUND_COLOR    = Color.WHITE;
    public final static Color     NAV_BAR_BORDER_COLOR        = Color.DARK_GRAY;
    public final static Color     NAV_BAR_SELECT_BORDER_COLOR = new Color(255, 0, 0, 200);
    public final static int       NAV_BAR_BORDER_WIDTH        = 3;

    public final static Color SIDEBAR_BACKGROUND_COLOR = new Color(155, 155, 155, 100);
    public final static Color SIDEBAR_BORDER_COLOR     = new Color(105, 105, 105, 100);
    public final static int   SIDEBAR_OFFSET           = 10;
    public final static int   SIDEBAR_BORDER_WIDTH     = 3;

    public final static int   WALL_OFFSET        = 5;
    public final static Color WALL_DARKER_COLOR  = Color.DARK_GRAY;
    public final static Color WALL_LIGHTER_COLOR = Color.LIGHT_GRAY;

    public final static boolean ZOOM_ALLOWED = true;
    public final static float   ZOOM_MAXIMUM = 5.0f;

    public final static String PROFILE_DEFAULT_NAME   = "Anonymous";
    public final static String PROFILE_DEFAULT_AVATAR = "player1.png";

    public final static int    DEFAULT_BLOCK_WIDTH  = 64;
    public final static int    DEFAULT_BLOCK_HEIGHT = 64;
    public final static int    DEFAULT_ROUND        = 20;
    public final static String DEFAULT_LANGUAGE     = "sk";
    public final static float  DEFAULT_ZOOM         = 1.0f;

    public static final int   PLAYER_MAX_HEALTH     = 10;
    public final static Color PLAYER_SELECTOR_COLOR = Color.MAGENTA;
    public final static int   PLAYER_SELECTOR_WIDTH = 2;


    public final static Color LOADING_DEFAULT_BACKGROUND_COLOR = Color.green;
    public final static int   LOADING_DEFAULT_FONT             = 40;
    public final static Color LOADING_DEFAULT_TEXT_COLOR       = Color.black;

    public final static String FOLDER_PROFILE = "/gameData/profiles/";

    public final static String EXTENSION_PROFILE = ".json";
    public final static String EXTENSION_IMAGE   = ".png";

    public final static String FILE_GUI_TEXTS       = "/gameData/guiTexts.json";
    public final static String FILE_PARTICLES       = "/gameData/particles.json";
    public final static String FILE_VISIBLE_OPTIONS = "/gameData/visibleOptions.json";
    public static final String FILE_GAME_CONFIG     = "/gameData/GameConfig.json";

    public static final int    SERVER_PORT           = 4321;
    public static final int    ALERT_VERTICAL_OFFSET = 10;
    public static final int    PING_REFRESH_TIME     = 1000;
    public static final int    SCANNING_TIMEOUT      = 100;
    public static final int    SCANNING_RANGE        = 20;
    public static final String TEXT_ARG_PLACEHOLDER  = "#####";

    public static final float     ENEMY_DEFAULT_ROUND  = 4;
    public static final float     ENEMY_DEFAULT_OFFSET = 5;
    public static final String    DEFAULT_FONT         = "Garamond";
    public static final int       DEFAULT_FONT_SIZE    = 20;
    public static final GVector2f CHUNK_SIZE           = new GVector2f(32, 32);
    public final static GVector2f BLOCK_SIZE           = new GVector2f(StaticConfig.DEFAULT_BLOCK_WIDTH,
                                                                       StaticConfig.DEFAULT_BLOCK_HEIGHT);
    public final static GVector2f BLOCK_SIZE_HALF      = BLOCK_SIZE.getDiv(2);
    public static final boolean   SHOW_CHUNK_BORDERS   = false;
    public static final float     MIN_ZOOM             = 0.2f;

}
