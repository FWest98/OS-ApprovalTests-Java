package com.spun.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.lambda.functions.Function1;

import java.io.IOException;
import java.time.Instant;

public class JsonUtils
{
  public static String prettyPrint(String json)
  {
    if (!ObjectUtils.isClassPresent("com.google.gson.Gson"))
    {
      throw new RuntimeException(
          "Missing Gson dependency\n  Pretty print uses Gson parser.\n  You can get this from the maven repo \n  or https://github.com/google/gson");
    }
    try
    {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      JsonElement je = JsonParser.parseString(json);
      return gson.toJson(je);
    }
    catch (JsonSyntaxException e)
    {
      return String.format("Error:%s\nJson:\n%s", e.getMessage(), json);
    }
  }
  public static String asJson(Object o)
  {
    return asJson(o, (b) -> b);
  }
  public static <T> String asJsonWithBuilder(Object o, Function1<T, T> gsonBuilder, Class<T> gsonBuilderClass)
  {
    if (!ObjectUtils.isThisInstanceOfThat(gsonBuilderClass, com.google.gson.GsonBuilder.class))
    { throw new FormattedException("Class must be of type %s", GsonBuilder.class.getName()); }
    return asJson(o, (Function1<GsonBuilder, GsonBuilder>) gsonBuilder);
  }
  public static <T> String asJson(Object o, Function1<GsonBuilder, GsonBuilder> gsonBuilder)
  {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    builder = addHandlingForDateObjects(builder);
    Gson gson = gsonBuilder.call(builder).create();
    return gson.toJson(o);
  }
  private static GsonBuilder addHandlingForDateObjects(GsonBuilder builder)
  {
    builder = builder.registerTypeAdapter(Instant.class, new InstantAdapter());
    return builder;
  }
  public static class InstantAdapter extends TypeAdapter<Instant>
  {
    @Override
    public void write(JsonWriter jsonWriter, Instant instant) throws IOException
    {
      jsonWriter.value(instant.toString());
    }
    @Override
    public Instant read(JsonReader jsonReader) throws IOException
    {
      throw new IOException("Never called");
    }
  }
}
