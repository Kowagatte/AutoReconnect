package ca.damocles.mods.autoreconnect;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "autoreconnect")
public class ModConfig implements ConfigData {
    public int reconnect_timer = 15;
}