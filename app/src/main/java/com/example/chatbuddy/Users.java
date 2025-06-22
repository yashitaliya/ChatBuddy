package com.example.chatbuddy;

public class Users
{
    String email,namee,passwordd,id,lastmessage,status;

    public Users(){}

    public Users(String id, String namee, String emaill, String passwordd, String status)
    {
        this.id = id;
        this.namee = namee;
        this.email = emaill;
        this.passwordd = passwordd;
        //this.profilepic = imageuri;
        this.status = status;

    }


    /*public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getPasswordd() {
        return passwordd;
    }

    public void setPasswordd(String passwordd) {
        this.passwordd = passwordd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

}
