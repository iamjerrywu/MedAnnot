package edu.cmu.cs.cs214.medannot.selection;

import edu.cmu.cs.cs214.medannot.framework.core.AnnotPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginSelect implements PluginSelection {
    private final String error;
    public PluginSelect() {
        this(null);
    }
    public PluginSelect(String error) {
        this.error = error;
    }
    public String getError() {
        return this.error;
    }
    public String getInstructions() {
        return "Register a plugin in the META-INF.services file, then select it from the list below";
    }
    public String getSelect() {
        return "Plugin";
    }
    public static List<AnnotPlugin> loadPlugins() {
        ServiceLoader<AnnotPlugin> plugins = ServiceLoader.load(AnnotPlugin.class);
        List<AnnotPlugin> result = new ArrayList<>();
        for (AnnotPlugin plugin : plugins) {
            result.add(plugin);
        }
        return result;
    }

    public AnnotPlugin getPlugin(String pluginName) {
        for (AnnotPlugin plugin : loadPlugins()) {
            if (plugin.getPluginName().equals(pluginName)) return plugin;
        }
        return null;
    }

    public View[] getCells() {
        List<AnnotPlugin> plugins = loadPlugins();
        View[] views = new View[plugins.size()];
        for (int i = 0; i < plugins.size(); i++) {
            views[i] = new View("/selectplugins?plugin=" + plugins.get(i).getPluginName(),
                    plugins.get(i).getPluginName());
        }
        return views;
    }
}
