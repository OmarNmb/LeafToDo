package model;

import Services.CategoryServices;
import Services.SessionServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import controllers.ViewCard;


public class Task {
    private int IDTask;
    private String name;
    private String status;
    private LocalDate expirationDate;
    private int IDUser;
    private int IDCategory;
    private LocalTime expirationTime;
    private ViewCard viewCard;

    public Task(String name, String status, LocalDate expirationDate, int IDCategory, LocalTime expirationTime) {
        this.name = name;
        this.status = status;
        this.expirationDate = expirationDate;
        IDUser = SessionServices.getCurrentUser().getIDUser();
        this.IDCategory = IDCategory;
        this.expirationTime = expirationTime;
        // Loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ViewCard.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewCard = loader.getController();
        CategoryServices categoryServices = new CategoryServices();
        List<Category> categories = categoryServices.getCategoriesByID(IDCategory);
        viewCard.insertData(name, expirationDate.toString(), categories.get(0).getColorCode(), status);
    }

    public void setID(int id) {
        this.IDTask = id;
        viewCard.setIDTask(id);
    }

    public int getID() {
        return IDTask;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getIDUser() {
        return IDUser;
    }

    public int getIDCategory() {
        return IDCategory;
    }

    public LocalTime getExpirationTime() {
        return expirationTime;
    }
    public HBox getModel() {
        return viewCard.getModel();
    }

}
