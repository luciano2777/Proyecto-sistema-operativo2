/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assets;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.*;
import java.awt.Color;
import java.lang.reflect.Type;

/**
 *
 * @author Juan
 */
public class ColorTypeAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("red", src.getRed());
        jsonObject.addProperty("green", src.getGreen());
        jsonObject.addProperty("blue", src.getBlue());
        return jsonObject;
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int red = jsonObject.get("red").getAsInt();
        int green = jsonObject.get("green").getAsInt();
        int blue = jsonObject.get("blue").getAsInt();
        return new Color(red, green, blue);
    }
}
