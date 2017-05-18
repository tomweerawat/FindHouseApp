package com.example.win81user.findhouse.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Property implements Parcelable {
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
    @SerializedName("img1")
    private String Image;
    @Expose
    @SerializedName("img2")
    private String Image2;

    @Expose
    @SerializedName("img3")
    private String Image3;
    @Expose
    @SerializedName("img4")
    private String Image4;
    @Expose
    @SerializedName("img5")
    private String Image5;
    @Expose
    @SerializedName("lat")
    private Double lat;
    @Expose
    @SerializedName("lng")
    private Double lng;

    public Property(Parcel parcel){
        setProperty_id(parcel.readInt());
        setContact(parcel.readString());
        setPropertyname(parcel.readString());
        setLocation(parcel.readString());
        setStatus(parcel.readString());
        setPrice(parcel.readString());
        setDescription(parcel.readString());
        setActivation(parcel.readString());
        setImage(parcel.readString());
        setImage2(parcel.readString());
        setImage3(parcel.readString());
        setImage4(parcel.readString());
        setImage5(parcel.readString());
        setLat(parcel.readDouble());
        setLongtitude(parcel.readDouble());
      /*  setPropertyname(parcel.readString());
        setActivation(parcel.readString());
        setDescription(parcel.readString());
        setContact(parcel.readString());
        setPrice(parcel.readString());
        setImage(parcel.readString());*/

    }
    public Property(String m, String b, int p, String up){
        description = m;
        propertyname = b;
        property_id = p;
        Image2 = up;
    }


    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

    public Double getLongtitude() {
        return lng;
    }

    public void setLongtitude(Double longtitude) {
        this.lng = longtitude;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }







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
    public String getImage5() {
        return Image5;
    }

    public void setImage5(String image5) {
        Image5 = image5;
    }

    public String getImage4() {
        return Image4;
    }

    public void setImage4(String image4) {
        Image4 = image4;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getProperty_id());
        dest.writeString(getContact());
        dest.writeString(getPropertyname());
        dest.writeString( getLocation() );
        dest.writeString(getStatus());
        dest.writeString( getPrice() );
        dest.writeString( getDescription() );
        dest.writeString( getActivation() );
        dest.writeString( getImage() );
        dest.writeString( getImage2() );
        dest.writeString( getImage3() );
        dest.writeString( getImage4() );
        dest.writeString( getImage5() );
        dest.writeDouble( getLat() );
        dest.writeDouble( getLongtitude() );
       /* dest.writeString( getPropertyname() );
        dest.writeString( getDescription() );
        dest.writeInt( getProperty_id() );
        dest.writeString( getImage() );
        dest.writeString( getImage2() );
        dest.writeString( getImage3() );
        dest.writeString( getImage4() );
        dest.writeString( getPrice() );
        dest.writeString( getImage4() );
        dest.writeString( getImage5() );
        dest.writeString( getContact() );
        dest.writeString( getActivation() );
        dest.writeString( getLocation() );
        dest.writeDouble( getLat() );
        dest.writeDouble( getLongtitude() );
        dest.writeString( getContact() );*/


    }

}
