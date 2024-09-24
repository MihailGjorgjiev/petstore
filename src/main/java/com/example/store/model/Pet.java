package com.example.store.model;

import com.example.store.enums.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;
//@Data
public class Pet {
    private final UUID id;
    private User owner;
    @NotBlank
    private final String name;
    private final Type type;
    private final String description;
    private final Date dateOfBirth;
    private final float price;
    private int rating;

    public Pet(@JsonProperty("id") UUID id,@JsonProperty("name") String name,@JsonProperty("type") Type type,@JsonProperty("description") String description,@JsonProperty("dateOfBirth") String dateOfBirth,@JsonProperty("rating") int rating) {
        this.id = id;
        this.owner = null;
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateOfBirth = new Date(dateOfBirth);
        this.price =type==Type.CAT ? (new Date()).getYear() -  this.dateOfBirth.getYear() : (new Date()).getYear() -  this.dateOfBirth.getYear() + rating;;
        this.rating=type==Type.CAT ? -1 : rating;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDateOfBirth() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        String stringDateOfBirth = dateFormat.format(this.dateOfBirth.getTime());
        return stringDateOfBirth;
    }

    public float getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }
}
