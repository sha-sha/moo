package shaul.games.moo.setup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shaul on 2/28/2015.
 */
public class StarResources {
    public static final String NORMAL = "Normal";
    public static final String ULTRA_POOR = "Ultra Poor";
    public static final String POOR = "Poor";
    public static final String RICH = "Rich";
    public static final String ULTRA_RICH = "Ultra Rich";
    public static final String ARTIFACTS = "Artifacts";
    public static final String ORION = "Orion";

    private static final Map<String, String> DESCRIPTIONS = new HashMap<String, String>() {{{
        put(NORMAL, "Star has average mineral resources and no bonuses to research");
        put(ULTRA_POOR, "Star has very few minerals available for production. The production on " +
                        "this planet is third (1/3) of what it would be in a normal mineral environment.");
        put(POOR, "Star has few minerals available for production. The production on " +
                "this planet is half (1/2) of what it would be in a normal mineral environment.");
        put(RICH, "Star has more minerals available for production. The production on " +
                "this planet is double of what it would be in a normal mineral environment.");
        put(ULTRA_RICH, "Star has lots of minerals available for production. The production on " +
                "this planet is triple of what it would be in a normal mineral environment.");
        put(ARTIFACTS, "Star has an ancient artifact on the planet which doubles the research rate. " +
                "The first civilization that scout the planet will instantly gain a useful piece of " +
                "technology");
        put(ORION, "This star is the former home world of the Orion civilization and the first " +
                "civilization to scout this planet gain high level technology. Research on this " +
                "planet occurs at four times the normal rate and production is triple of what it " +
                "would be in a normal mineral environment.");
    }}};

    private static final Map<String, Float> PRODUCTION_MULTI = new HashMap<String, Float>() {{{
            put(NORMAL, 1.0f);
            put(ULTRA_POOR, 0.3333f);
            put(POOR, 0.5f);
            put(RICH, 2f);
            put(ULTRA_RICH, 3f);
            put(ARTIFACTS, 1f);
            put(ORION, 3f);
        }}};

    private static final Map<String, Float> RESEARCH_MULTI = new HashMap<String, Float>() {{{
        put(NORMAL, 1f);
        put(ULTRA_POOR, 1f);
        put(POOR, 1f);
        put(RICH, 1f);
        put(ULTRA_RICH, 1f);
        put(ARTIFACTS, 2f);
        put(ORION, 4f);
    }}};

    public static int applyProductionModifier(String resourceLevel, int currentProduction) {
        return (int) Math.floor(PRODUCTION_MULTI.get(resourceLevel) * currentProduction);
    }

    public static int applyResearchModifier(String resourceLevel, int currentResearch) {
        return (int) Math.floor(RESEARCH_MULTI.get(resourceLevel) * currentResearch);
    }

    public static String getFirstScoutResearch(String resourceLevel) {
        return null; //?????????
    }

    public static String getDescription(String resourceLevel) {
        return DESCRIPTIONS.get(resourceLevel);
    }
}
