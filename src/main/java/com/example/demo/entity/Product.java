package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "product")
public class Product extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6103594877366251208L;

    @Column(nullable = false)
    @NotEmpty()
    private String name;

    @Column(nullable = false)
    @NotEmpty()
    private Double price;

    @Column(nullable = false)
    @NotEmpty()
    private String description;

    private Double discount;

    private String image; // product1.png

    public Product() {
    }

    public Product(String name, Double price, Double discount, String image, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.discount = discount;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + getId() + ", name='" + name + ", price=" + price + ", description='" + description + ", discount=" + discount + ", image='" + image + '}';
    }
}
