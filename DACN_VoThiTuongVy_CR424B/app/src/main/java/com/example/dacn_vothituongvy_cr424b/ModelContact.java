package com.example.dacn_vothituongvy_cr424b;

public class ModelContact {
    private String id, name, image, phone, email, note, addedTime, updatedTime;

    public ModelContact(String id, String name, String image, String phone,
                        String email, String note, String addedTime, String updatedTime) {
        this.id = id;         this.name = name;     this.image = image;
        this.phone = phone;   this.email = email;   this.note = note;
        this.addedTime = addedTime; this.updatedTime = updatedTime;
    }

    /* --- getter / setter --- */
    public String getId()          { return id; }
    public String getName()        { return name; }
    public String getImage()       { return image; }
    public String getPhone()       { return phone; }
    public String getEmail()       { return email; }
    public String getNote()        { return note; }
    public String getAddedTime()   { return addedTime; }
    public String getUpdatedTime() { return updatedTime; }

    public void setImage(String image)       { this.image = image; }
    public void setName(String name)         { this.name = name; }
    public void setPhone(String phone)       { this.phone = phone; }
    public void setEmail(String email)       { this.email = email; }
    public void setNote(String note)         { this.note = note; }
}
