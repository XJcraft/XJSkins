package com.xj.skins;

import java.util.List;

public class Profile {

    private String id;
    private String name;
    private List<ProfileProperty> properties;

    public Profile(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProfileProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<ProfileProperty> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Profile {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                '}';
    }
}
