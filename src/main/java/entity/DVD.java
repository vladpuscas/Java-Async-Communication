package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vlad on 18-Nov-17.
 */
@Entity
public class DVD implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private int year;

    private double price;

    public DVD() {
    }

    public DVD(String title, int year, double price) {
        this.title = title;
        this.year = year;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return " Title='" + title + '\'' +
                ", Year=" + year +
                ", Price=" + price;
    }
}