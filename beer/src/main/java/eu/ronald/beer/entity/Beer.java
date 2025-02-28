package eu.ronald.beer.entity;

import io.vertx.core.json.JsonObject;

public record Beer(String brewery, String name, String imageLink, double alcByVol) {

  public static final Beer DEFAULT = new Beer("NO BEER TODAY", "", "", 0.0);

  public static Beer of(JsonObject input) {
    return new Beer(
        input.getString("brewery"),
        input.getString("name"),
        input.getString("imageLink"),
        input.getDouble("alcByVol")
    );
  }
}
