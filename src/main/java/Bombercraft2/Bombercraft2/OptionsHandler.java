package Bombercraft2.Bombercraft2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OptionsHandler {
    private final Map<Options, Object> options = new HashMap<>();

    public int getInt(Options option) {
        return Integer.valueOf((String) options.get(option));
    }

    public float getFloat(Options option) {
        return Float.valueOf((String) options.get(option));
    }

    public String getString(Options option) {
        return String.valueOf(options.get(option));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Options option) {
        return (T) options.get(option);
    }

    public void loadFile(BufferedReader reader) {
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                final String[] splitLine = line.split("=");
                if (splitLine.length != 2) {
                    throw new Error("Wrong config line: " + line);
                }
                Options option = Options.valueOf(splitLine[0].trim());
                options.put(option, splitLine[1].trim());
            }

            if (options.size() != Options.values().length) {
                throw new Error("Loaded " + options.size() + " values but required number is " + Options.values().length);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOptions() {
        final StringBuilder builder = new StringBuilder();
        this.options.forEach((key, value) -> builder.append(key)
                                                    .append("=")
                                                    .append(value)
                                                    .append(System.lineSeparator()));
        return builder.toString();
    }
}
