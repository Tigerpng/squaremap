package net.pl3x.map.configuration;

import net.minecraft.server.v1_16_R3.MathHelper;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class WorldConfig {
    private static final Map<UUID, WorldConfig> configs = new HashMap<>();

    public static void reload() {
        configs.clear();
        Bukkit.getWorlds().forEach(world -> {
            final WorldConfig config = new WorldConfig(world);
            configs.put(world.getUID(), config);
        });
    }

    public static WorldConfig get(World world) {
        return configs.get(world.getUID());
    }

    private final String worldName;

    public WorldConfig(World world) {
        this.worldName = world.getName();
        init();
    }

    public void init() {
        Config.readConfig(WorldConfig.class, this);
    }

    private void set(String path, Object val) {
        Config.CONFIG.addDefault("world-settings.default." + path, val);
        Config.CONFIG.set("world-settings.default." + path, val);
        if (Config.CONFIG.get("world-settings." + worldName + "." + path) != null) {
            Config.CONFIG.addDefault("world-settings." + worldName + "." + path, val);
            Config.CONFIG.set("world-settings." + worldName + "." + path, val);
        }
    }

    private boolean getBoolean(String path, boolean def) {
        Config.CONFIG.addDefault("world-settings.default." + path, def);
        return Config.CONFIG.getBoolean("world-settings." + worldName + "." + path, Config.CONFIG.getBoolean("world-settings.default." + path));
    }

    private int getInt(String path, int def) {
        Config.CONFIG.addDefault("world-settings.default." + path, def);
        return Config.CONFIG.getInt("world-settings." + worldName + "." + path, Config.CONFIG.getInt("world-settings.default." + path));
    }

    private String getString(String path, String def) {
        Config.CONFIG.addDefault("world-settings.default." + path, def);
        return Config.CONFIG.getString("world-settings." + worldName + "." + path, Config.CONFIG.getString("world-settings.default." + path));
    }

    public boolean MAP_ENABLED = true;

    public boolean MAP_BIOMES = true;
    public int MAP_BIOMES_BLEND = 4;

    public boolean MAP_WATER_BIOMES = true;
    public int MAP_WATER_BIOMES_BLEND = 4;
    public boolean MAP_WATER_CLEAR = true;
    public boolean MAP_WATER_CHECKERBOARD = false;

    public boolean MAP_LAVA_CHECKERBOARD = true;

    private void worldSettings() {
        MAP_ENABLED = getBoolean("map.enabled", MAP_ENABLED);
    }

    private void biomeSettings() {
        MAP_BIOMES = getBoolean("map.biomes.enabled", MAP_BIOMES);
        MAP_BIOMES_BLEND = MathHelper.clamp(getInt("map.biomes.blend-biomes", MAP_BIOMES_BLEND), 0, 15);
    }

    private void waterSettings() {
        MAP_WATER_BIOMES = getBoolean("map.water.biomes.enabled", MAP_WATER_BIOMES);
        MAP_WATER_BIOMES_BLEND = MathHelper.clamp(getInt("map.water.biomes.blend-biomes", MAP_WATER_BIOMES_BLEND), 0, 15);
        MAP_WATER_CLEAR = getBoolean("map.water.clear-depth", MAP_WATER_CLEAR);
        MAP_WATER_CHECKERBOARD = getBoolean("map.water.checkerboard", MAP_WATER_CHECKERBOARD);
    }

    private void lavaSettings() {
        MAP_LAVA_CHECKERBOARD = getBoolean("map.lava.checkerboard", MAP_LAVA_CHECKERBOARD);
    }

    public boolean PLAYER_TRACKER_ENABLED = true;
    public boolean PLAYER_TRACKER_SHOW_TOGGLE = true;
    public boolean PLAYER_TRACKER_NAMEPLATE_ENABLED = true;
    public boolean PLAYER_TRACKER_NAMEPLATE_SHOW_HEAD = true;
    public boolean PLAYER_TRACKER_HIDE_INVISIBLE = true;
    public boolean PLAYER_TRACKER_HIDE_SPECTATORS = true;

    private void playerTrackerSettings() {
        PLAYER_TRACKER_ENABLED = getBoolean("player-tracker.enabled", PLAYER_TRACKER_ENABLED);
        PLAYER_TRACKER_SHOW_TOGGLE = getBoolean("player-tracker.show-toggle", PLAYER_TRACKER_SHOW_TOGGLE);
        PLAYER_TRACKER_NAMEPLATE_ENABLED = getBoolean("player-tracker.nameplate.enabled", PLAYER_TRACKER_NAMEPLATE_ENABLED);
        PLAYER_TRACKER_NAMEPLATE_SHOW_HEAD = getBoolean("player-tracker.nameplate.show-head", PLAYER_TRACKER_NAMEPLATE_SHOW_HEAD);
        PLAYER_TRACKER_HIDE_INVISIBLE = getBoolean("player-tracker.hide.invisible", PLAYER_TRACKER_HIDE_INVISIBLE);
        PLAYER_TRACKER_HIDE_SPECTATORS = getBoolean("player-tracker.hide.spectators", PLAYER_TRACKER_HIDE_SPECTATORS);
    }

    public String UI_TITLE = "Pl3xMap";
    public boolean UI_COORDINATES = true;

    private void uiSettings() {
        UI_TITLE = getString("ui.title", UI_TITLE);
        UI_COORDINATES = getBoolean("ui.coordinates", UI_COORDINATES);
    }
}
