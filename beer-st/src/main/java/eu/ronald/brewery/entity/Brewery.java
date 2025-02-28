package eu.ronald.brewery.entity;


import java.time.LocalDateTime;

public record Brewery(String name, String imageLink, int yearOfFounding, String history, LocalDateTime createTime) {
   public static final Brewery DEFAULT = new Brewery("NO BEER TODAY","",0,"",LocalDateTime.of(2000,1,1,1,1,1));
}
