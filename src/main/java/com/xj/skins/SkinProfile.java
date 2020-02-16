package com.xj.skins;

public class SkinProfile {
    // 皮肤有效时间为12h
    private static long EXPIRED = 12 * 60 * 60 * 1000;
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
        return (System.currentTimeMillis() - timestamp) > EXPIRED;
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
