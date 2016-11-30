package com.example.win81user.findhouse.Model;


public class User {

//    private String user_id;
//    @Expose
//    @SerializedName("username")
//    private String username;
//    private String first_name;
//    private String last_name;
//    @Expose
//    @SerializedName("password")
//    private String password;
//    @Expose
//    @SerializedName("email_address")
//    private String email_address;
//    private String tel;
//    private String userimage;
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getUserimage() {
//        return userimage;
//    }
//
//    public void setUserimage(String userimage) {
//        this.userimage = userimage;
//    }
//
//    public String getTel() {
//        return tel;
//    }
//
//    public void setTel(String tel) {
//        this.tel = tel;
//    }
//
//    public String getEmail_address() {
//        return email_address;
//    }
//
//    public void setEmail_address(String email_address) {
//        this.email_address = email_address;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
private String name;
    private String email;
    private String unique_id;
    private String password;
    private String old_password;
    private String new_password;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUnique_id() {
        return unique_id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }




}
