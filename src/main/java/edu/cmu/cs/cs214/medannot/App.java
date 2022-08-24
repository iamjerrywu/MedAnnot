package edu.cmu.cs.cs214.medannot;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import edu.cmu.cs.cs214.medannot.annotation.Annotation;
import edu.cmu.cs.cs214.medannot.framework.core.AnnotFramework;
import edu.cmu.cs.cs214.medannot.framework.core.AnnotPlugin;
import edu.cmu.cs.cs214.medannot.image.Image;
import edu.cmu.cs.cs214.medannot.image.ImageError;
import edu.cmu.cs.cs214.medannot.selection.ImageSelect;
import edu.cmu.cs.cs214.medannot.selection.PluginSelect;
import edu.cmu.cs.cs214.medannot.selection.PluginSelection;
import edu.cmu.cs.cs214.medannot.selection.TableSelect;
import edu.cmu.cs.cs214.medannot.table.Table;
import edu.cmu.cs.cs214.medannot.table.TableError;
import fi.iki.elonen.NanoHTTPD;
import java.io.*;

import java.io.IOException;
import java.util.Map;


public class App extends NanoHTTPD {
    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start the server!: \n" + ioe);
        }
    }
    private Response render(Object o, Template template) throws IOException {
        return newFixedLengthResponse(template.apply(o));
    }

    private final AnnotFramework framework = AnnotFramework.getNewFramework();
    Image image = null;
    Table table = null;
    AnnotPlugin plugin = null;
    Annotation annotation = null;
    private final String prefixImagePath = "src/main/resources/availableImages/";
    private final String prefixTablesPath = "src/main/resources/availableTables/";
    private final String ImageFormat = ".png";
    private final String TableFormat = ".csv";
    private final int MaxImageWidth = 4096;
    private final int MaxImageHeight = 4096;
    private static final int PORT = 8000;
    private final Template selectTemplate = new Handlebars().compile("select");
    private final Template visualizerTemplate = new Handlebars().compile("visualizer");
    private PluginSelection pluginSelect = null;
    private ImageSelect imageSelect = null;
    private TableSelect tableSelect = null;
    public App() throws IOException {
        super(PORT);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8000/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String fileName = "";
        String filePath = "";
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        try {
            if (uri.equals("/selectplugins")) {
                String pluginString = params.get("plugin");
                plugin = pluginSelect.getPlugin(pluginString);
                if (plugin == null) {
                    pluginSelect = new PluginSelect("Invalid plugin selected, please try again");
                    return render(pluginSelect, selectTemplate);
                } else {
                    imageSelect = new ImageSelect();
                    return render(imageSelect, selectTemplate);
                }
            } else if (uri.equals("/selectimage")) {
                fileName = params.get("image");
                filePath = prefixImagePath + fileName + ImageFormat;
                try {
                    image = new Image(MaxImageWidth, MaxImageHeight, filePath);
                    tableSelect = new TableSelect();
                    return render(tableSelect, selectTemplate);
                } catch (ImageError.InvalidImage e) {
                    pluginSelect = new PluginSelect("Invalid image, try again");
                    return render(pluginSelect, selectTemplate);
                }
            } else if (uri.equals("/selecttable")) {
                fileName = params.get("table");
                filePath = prefixTablesPath + fileName + TableFormat;
                try {
                    table = new Table(filePath);
                    if (!AnnotFramework.isImageTablePluginValid(image, table, plugin)) {
                        pluginSelect = new PluginSelect("Table does not contain required fields for " + plugin.getPluginName() + " plugin");
                        return render(pluginSelect, selectTemplate);
                    }
                    annotation = framework.render(image, table, plugin);
                    return render(annotation, visualizerTemplate);
                } catch (TableError.InvalidFile invalidFile) {
                    pluginSelect = new PluginSelect("Invalid table file, please try again");
                    return render(pluginSelect, selectTemplate);
                } catch (TableError.MissingCoreFields missingCoreFields) {
                    pluginSelect = new PluginSelect("Table missing core fields: " + missingCoreFields.getFieldsMissing());
                    return render(pluginSelect, selectTemplate);
                } catch (TableError.EmptyCSV emptyCSV) {
                    pluginSelect = new PluginSelect("CSV is empty");
                    return render(pluginSelect, selectTemplate);
                }
            } else if (uri.equals("/visualizer")) {
                int button = Integer.parseInt(params.get("button"));
                annotation.buttonPressed(button);
                return render(annotation, visualizerTemplate);
            } else if (uri.equals("/reset")) {
                return render(pluginSelect, selectTemplate);
            } else if (uri.equals("/image")) {
                FileInputStream fis = null;
                try {
                    File file = new File("src/main/resources/renderedImages/rendered.png");
                    if (file.exists()){
                        fis = new FileInputStream(file);
                    } else {
                        System.out.println("Rendered image file doesn't exist!");
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return newChunkedResponse(Response.Status.OK, "image/jpeg", fis);
            } else {
                pluginSelect = new PluginSelect();
                return render(pluginSelect, selectTemplate);
            }
        } catch(IOException e) {
            e.printStackTrace();
            return serve(session);
        }
    }
}
