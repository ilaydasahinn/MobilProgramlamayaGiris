package com.ilayda.mobilprogramming;

public class MyListData {
    private String username;
    private String password;
    private String imageUri;

    public MyListData(String username, String password, String imageUri){
       this.username = username;
       this.password = password;
       this.imageUri = imageUri;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
