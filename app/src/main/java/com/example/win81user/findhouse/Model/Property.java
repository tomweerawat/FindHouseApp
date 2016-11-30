package com.example.win81user.findhouse.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Property {
    @Expose
    @SerializedName("property_id")
    private int property_id;
    @Expose
    @SerializedName("contact")
    private String contact;

    @Expose
    @SerializedName("propertyname")
    private String propertyname;
    @Expose
    @SerializedName("location")
    private String location;

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("activation")
    private String activation;

    @Expose
    @SerializedName("Image")
    private String Image;


    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPropertyname() {
        return propertyname;
    }

    public void setPropertyname(String propertyname) {
        this.propertyname = propertyname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

}
