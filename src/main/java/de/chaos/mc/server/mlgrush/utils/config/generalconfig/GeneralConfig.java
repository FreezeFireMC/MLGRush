package de.chaos.mc.server.mlgrush.utils.config.generalconfig;

import de.chaos.mc.server.mlgrush.MLGRush;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneralConfig {
    @Getter
    private File configFile;
    @Getter private YamlConfiguration config;

    public void loadConfig() {
        configFile = new File(MLGRush.getInstance().getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        saveGenerelConfig();
    }

    public String getValue(String key) {
        return config.getString(key);
    }

    public List<String> getBuilder() {
        List<String> list = (List<String>) config.getList("Builder");
        return list;
    }

    public void setDeafultValues() {
        config.set("MapName", "Oreo");
        List<String> helper = new ArrayList<>();
        helper.add("dergrosehd");
        helper.add("BySxsuke");
        config.set("Builder", helper);
        try {
            config.save(configFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveGenerelConfig() {
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
                this.setDeafultValues();
            }
            config.save(configFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
