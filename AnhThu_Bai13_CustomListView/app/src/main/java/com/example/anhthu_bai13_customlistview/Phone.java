package com.example.anhthu_bai13_customlistview;

public class Phone {
    private  int image ;
    private  String name ;
    private  String money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setImage(int image) {
        this.image = image;
    }
    // tao constructor

    public Phone(int image, String name , String money) {
        this.image = image;
        this.name = name;
        this.money = money;
    }
}
