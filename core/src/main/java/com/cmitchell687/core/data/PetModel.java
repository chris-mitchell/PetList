package com.cmitchell687.core.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(
        tableName = "pets"
)
public class PetModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "dog_name")
    private String dogName;
    @ColumnInfo(name = "owner_name")
    private String ownerName;
    @ColumnInfo(name = "dog_photo_uri")
    private String dogPhotoUri;

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    @NonNull
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(@NonNull String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDogPhotoUri() {
        return dogPhotoUri;
    }

    public void setDogPhotoUri(String dogPhotoUri) {
        this.dogPhotoUri = dogPhotoUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetModel petModel = (PetModel) o;
        return id == petModel.id &&
                Objects.equals(dogName, petModel.dogName) &&
                Objects.equals(ownerName, petModel.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dogName, ownerName);
    }
}
