package eu.ronald.brewery.entity;

import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public record Brewery(String name, String imageLink, int yearOfFounding, String history, LocalDateTime createTime) {
   public static final Brewery DEFAULT = new Brewery("NO BEER TODAY","",0,"",LocalDateTime.of(2000,1,1,1,1,1));

   public static Brewery of(JsonObject input) {
      return new Brewery(
         input.getString("name"),
         input.getString("imageLink",""),
         input.getInteger("yearOfFounding",2025),
         input.getString("history",""),
         LocalDateTime.now()
      );
   }
}
