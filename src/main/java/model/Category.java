package model;

public class Category {
  private int IDCategory;
  private String name;
  private String colorCode;

  public Category(int IDCategory, String name, String colorCode) {
    this.IDCategory = IDCategory;
    this.name = name;
    this.colorCode = colorCode;
  }

  public int getIDCategory() {
    return IDCategory;
  }

  public String getName() {
    return name;
  }

  public String getColorCode() {
    return colorCode;
  }

  @Override
  public String toString() {
    return name;
  }
}
