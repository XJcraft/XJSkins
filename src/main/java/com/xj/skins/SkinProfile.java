package com.xj.skins;

public class SkinProfile {

    private long timestamp;
    private ProfileProperty playerSkinData;

    public SkinProfile(ProfileProperty skinData) {
        timestamp = System.currentTimeMillis();
        playerSkinData = skinData;
    }

    public SkinProfile(ProfileProperty skinData, long creationTime) {
        this(skinData);
        timestamp = creationTime;
    }

    public boolean isTooDamnOld() {
        return (System.currentTimeMillis() - timestamp) > (2 * 60 * 60 * 1000);
    }

    public long getCreationDate() {
        return timestamp;
    }

    public ProfileProperty getPlayerSkinProperty() {
        return playerSkinData;
    }

    @Override
    public String toString() {
        return "SkinProfile {" +
                "timestamp=" + timestamp +
                ", playerSkinData=" + playerSkinData +
                '}';
    }
}
