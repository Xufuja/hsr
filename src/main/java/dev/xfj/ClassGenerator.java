package dev.xfj;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassGenerator {
    private final String dataDirectory;
    private final String outputDirectory;

    public ClassGenerator(String dataDirectory, String outputDirectory) {
        this.dataDirectory = dataDirectory;
        this.outputDirectory = outputDirectory;
    }

    public void createClasses() throws IOException {
        Set<String> set = getAllFiles();
        for (String item : set) {
            JsonReader jsonReader = new JsonReader(new FileReader(dataDirectory + item));
            JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();

            if (!jsonObject.entrySet().isEmpty()) {
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    Files.createDirectories(Paths.get(outputDirectory));
                    String noExtension = item.replace(".json", "");
                    createClass(entry.getValue().toString(), new File(outputDirectory), "dev.xfj." + noExtension.toLowerCase(), noExtension);
                    break;
                }

            }
        }
    }

    private void createClass(String jsonString, File outputDirectory, String packageName, String className)
            throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }

            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public boolean isUsePrimitives() {
                return true;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new GsonAnnotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, className, packageName, jsonString);

        jcodeModel.build(outputDirectory);
    }

    private Set<String> getAllFiles() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dataDirectory))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }
}
