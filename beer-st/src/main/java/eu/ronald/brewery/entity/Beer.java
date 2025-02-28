package eu.ronald.brewery.entity;


public record Beer(String brewery, String name, String imageLink, double alcByVol) {
  public static final Beer DEFAULT = new Beer("NO BEER TODAY", "", "", 0.0);

}
