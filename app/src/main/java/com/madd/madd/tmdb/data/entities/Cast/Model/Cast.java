package com.madd.madd.tmdb.data.entities.Cast.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cast {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("cast")
    @Expose
    List<Actor> actorList;

    Long timeStap;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public Long getTimeStap() {
        return timeStap;
    }

    public void setTimeStap() {
        this.timeStap = System.currentTimeMillis();
    }

    public class Actor {

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("character")
        @Expose
        private String character;

        @SerializedName("profile_path")
        @Expose
        private String profilePath;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCharacter() {
            return character != null && !character.isEmpty() ? character : "Sin nombre";
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getProfilePath() {
            return profilePath != null ? "https://image.tmdb.org/t/p/w500/" + profilePath : "" ;
        }

        public void setProfilePath(String profilePath) {
            this.profilePath = profilePath;
        }
    }


}
