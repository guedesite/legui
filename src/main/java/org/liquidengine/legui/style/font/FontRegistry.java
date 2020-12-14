package org.liquidengine.legui.style.font;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ShchAlexander on 1/26/2017.
 */
public class FontRegistry {

    /**
     * Default entypo font.
     */
    public static final String ENTYPO = "entypo";
    /**
     * Default roboto-bold font.
     */
    public static final String ROBOTO_BOLD = "roboto-bold";
    /**
     * Default roboto-light font.
     */
    public static final String ROBOTO_LIGHT = "roboto-light";
    /**
     * Default roboto-regular font.
     */
    public static final String ROBOTO_REGULAR = "roboto-regular";
    /**
     * Default material-icons-regular font.
     */
    public static final String MATERIAL_ICONS_REGULAR = "MaterialIcons-Regular";
    /**
     * Default font-awesome-icons font.
     */
    public static final String FONT_AWESOME_ICONS = "FontAwesomeIcons";
    /**
     * Default material-design-icons font.
     */
    public static final String MATERIAL_DESIGN_ICONS = "materialdesignicons";
    
    public static final String NEO_COMMUN_BOLD = "NEOCOMMUNBOLD";
    public static final String NEO_COMMUN_HEAVY = "NEOCOMMUNHEAVY";
    public static final String NEO_COMMUN_REGULAR = "NEOCOMMUNREGULAR";
    
    public static final String NEO_BRUTE_REGULAR = "NEOBRUTEREGULAR";
    /**
     * Font used by default. {@link #ROBOTO_BOLD}.
     */
    public static final String DEFAULT = ROBOTO_LIGHT;
    /**
     * Font register.
     */
    private static final Map<String, Font> fontRegister = new ConcurrentHashMap<>();

    static {
        registerFont(ENTYPO, GetFilePath()+"assets/launcher/font/entypo.ttf");
        registerFont(ROBOTO_BOLD, GetFilePath()+"assets/launcher/font/Roboto-Bold.ttf");
        registerFont(ROBOTO_LIGHT, GetFilePath()+"assets/launcher/font/Roboto-Light.ttf");
        registerFont(ROBOTO_REGULAR, GetFilePath()+"assets/launcher/font/Roboto-Regular.ttf");
        registerFont(MATERIAL_ICONS_REGULAR, GetFilePath()+"assets/launcher/font/MaterialIcons-Regular.ttf");
        registerFont(FONT_AWESOME_ICONS, GetFilePath()+"assets/launcher/font/FontAwesome.otf");
        registerFont(MATERIAL_DESIGN_ICONS, GetFilePath()+"assets/launcher/font/materialdesignicons.ttf"); 
        registerFont(NEO_COMMUN_BOLD, GetFilePath()+"assets/launcher/font/Oswald-Bold.ttf");
        registerFont(NEO_COMMUN_HEAVY, GetFilePath()+"assets/launcher/font/Oswald-Heavy.ttf");
        registerFont(NEO_COMMUN_REGULAR,GetFilePath()+ "assets/launcher/font/Oswald-Regular.ttf");
        registerFont(NEO_BRUTE_REGULAR, GetFilePath()+"assets/launcher/font/Bebas-Regular.otf"); 
    }

    /**
     * Private constructor to avoid creation instances of utility class.
     */
    private FontRegistry() {
    }

    
public static String path;
	
	public static String GetFilePath() {
		if(path == null)
		{
		    String os = System.getProperty("os.name").toLowerCase();
		    if (os.contains("win"))
		    {
		    	path =  String.valueOf(System.getProperty("user.home")) + "\\AppData\\Roaming\\.FeuArdent\\"; 
		    }
		    else if (os.contains("mac")) {
		    	path =  String.valueOf(System.getProperty("user.home")) + "/Library/Application Support/FeuArdent/";
		    }
		    else {
		    	path = String.valueOf(System.getProperty("user.home")) + "/.FeuArdent/";
		    }
		} 
		return path;
	  }
    /**
     * Used to register fonts.
     *
     * @param name font name.
     * @param path font path.
     */
    public static void registerFont(final String name, final String path) {
        try {
            Font font = new Font(path);
            fontRegister.put(name, font);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns map of fonts where key is font name and value is font.
     *
     * @return map of fonts where key is font name and value is font.
     */
    public static Map<String, Font> getFontRegister() {
        return new HashMap<>(fontRegister);
    }

    /**
     * Used to retrieve font by name
     *
     * @param name font name.
     *
     * @return font or null.
     */
    public static Font getFont(String name) {
        return fontRegister.get(name);
    }
}
