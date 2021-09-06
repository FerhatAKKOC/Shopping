package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "news")
public class News extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8413876025300574325L;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Size(min = 100)
    private String head;

    @Column(nullable = false)
    @NotEmpty()
    private String description;

    private String image;

    public News() {
    }

    public News(String head, String image, String description) {
        this.head = head;
        this.description = description;
        this.image = image;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "News{" + "id=" + getId() + "head='" + head + ", description='" + description + '}';
    }
}
