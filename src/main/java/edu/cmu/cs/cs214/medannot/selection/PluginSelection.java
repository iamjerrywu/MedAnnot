package edu.cmu.cs.cs214.medannot.selection;

import edu.cmu.cs.cs214.medannot.framework.core.AnnotPlugin;

public interface PluginSelection extends Selection {
    AnnotPlugin getPlugin(String pluginName);
    String getError();
}
