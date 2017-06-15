package Bombercraft2.Bombercraft2;

import java.awt.Color;

import utils.math.GVector2f;

public class Config {
	public final static String 		WINDOW_DEFAULT_TITLE 		= "Bombercraft 2.0";
	public final static int 		WINDOW_DEFAULT_WIDTH 		= 800;
	public final static int 		WINDOW_DEFAULT_HEIGHT 		= 600;
	public final static int			WINDOW_DEFAULT_FPS			= 60;
	public final static int			WINDOW_DEFAULT_UPS			= 60;
	public final static boolean		WINDOW_DEFAULT_RENDER_TEXT	= false;
	

	public final static int		LOG_TEXT_SIZE		= 16;
	public final static Color	LOG_TEXT_COLOR		= Color.white;
	public final static Color	LOG_BG_COLOR		= new Color(0, 0, 0, 200);
	public final static Color	LOG_BORDER_COLOR	= Color.white;
	public final static int		LOG_BORDER_WIDTH	= 2;

	public final static int 	HEATLBAR_VERTICAL_OFFSET 	= 0;
	public final static int 	HEATLBAR_HORIZONTAL_OFFSET 	= 0;
	public final static int 	HEATLBAR_HORIZONTAL_HEIGHT 	= 10;
	public final static int 	HEATLBAR_BORDER_WIDTH 		= 1;
	public static final Color 	HEATLBAR_BORDER_COLOR 		= Color.WHITE;
	public static final Color 	HEATLBAR_FILL_COLOR 		= Color.GREEN;
	
	
	public final static Color	BAR_BACKGROUND_COLOR	= Color.WHITE;
	public final static Color	BAR_BORDER_COLOR		= Color.DARK_GRAY;
	public final static int		BAR_BORDER_WIDTH		= 3;
	
	public final static GVector2f	NAVBAR_SIZE					= new GVector2f(40, 40);
	public final static int			NAVBAR_BOTTOM_OFFSET		= 20;
	public final static int			NAVBAR_NUMBER_OF_BLOCKS		= 19;
	public final static Color		NAVBAR_BACKGROUND_COLOR		= Color.WHITE;
	public final static Color		NAVBAR_BORDER_COLOR			= Color.DARK_GRAY;
	public final static Color		NAVBAR_SELECT_BORDER_COLOR	= new Color(255, 0, 0, 200);
	public final static int			NAVBAR_BORDER_WIDTH			= 3;

	public final static Color	SIDEBAR_BACKGROUND_COLOR	= new Color(155, 155, 155, 100);
	public final static Color	SIDEBAR_BORDER_COLOR		= new Color(105, 105, 105, 100);
	public final static int		SIDEBAR_OFFSET				= 10;
	public final static int		SIDEBAR_BORDER_WIDTH		= 3;
	
	public final static int		WALL_OFFSET			= 5;
	public final static Color	WALL_DARKER_COLOR	= Color.DARK_GRAY;
	public final static Color	WALL_LIGHTER_COLOR	= Color.LIGHT_GRAY;
	
	public final static boolean	ZOOM_ALLOWED			= true;
	public final static float	ZOOM_MAXIMUM			= 5.0f;
	
	public final static String	PROFILE_DEFAULT_NAME	= "Anonymous";
	public final static String	PROFILE_DEFAULT_AVATAR	= "player1.png";

	public final static int		DEFAULT_BLOCK_WIDTH		= 64;
	public final static int		DEFAULT_BLOCK_HEIGHT	= 64;
	public final static int 	DEFAULT_ROUND			= 20;
	public final static String 	DEFAULT_LANGUAGE		= "sk";
	public final static float	DEFAULT_ZOOM			= 1.0f;

	public static final int 	PLAYER_MAX_HEALT 		= 10;
	public final static Color	PLAYER_SELECTOR_COLOR	= Color.MAGENTA;
	public final static int		PLAYER_SELECTOR_WIDTH	= 2;
	

	public final static Color	LOADING_DEFAULT_BACKGROUND_COLOR	= Color.green;
	public final static int		LOADING_DEFAULT_FONT				= 40;
	public final static Color	LOADING_DEFAULT_TEXT_COLOR			= Color.black;
	
	public final static String	FOLDER_PROFILE	= "/gameData/profiles/";
	
	public final static String	EXTENSION_PROFILE	= ".json";
	public final static String	EXTENSION_IMAGE		= ".png";
	
	public final static String	FILE_GUI_TEXTS			= "/gameData/guiTexts.json";
	public final static String	FILE_VISIBLE_OPTIONS	= "/gameData/visibleOptions.json";
}
