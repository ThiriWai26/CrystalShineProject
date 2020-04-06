package com.chann.crystalshineproject.data;

public class ADDShopModel {
    String token;
    String category_Id;
    String townShip_Id;
    String rate;
    String name;
    String grade;
    String address;

    public ADDShopModel(){

    }
    public ADDShopModel(String token,String category_Id,String townShip_Id,String rate, String name, String grade,String address){
        this.token=token;
        this.category_Id=category_Id;
        this.townShip_Id=townShip_Id;
        this.rate = rate;
        this.name = name;
        this.grade= grade;
        this.address=address;

    }
    public String getToken() {
        return token;
    }

    public String getCategory_Id() {
        return category_Id;
    }

    public String getTownShip_Id() {
        return townShip_Id;
    }

    public String getRate() {
        return rate;
    }

    public String getName() {
        return name;

    }

    public String getGrade() {
        return grade;
    }

    public String getAddress() {
        return address;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCategory_Id(String category_Id) {
        this.category_Id = category_Id;
    }

    public void setTownShip_Id(String townShip_Id) {
        this.townShip_Id = townShip_Id;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}



