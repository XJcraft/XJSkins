package com.xj.skins.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Channels {

    public static byte[] format(String channel, String... args) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        out.writeUTF(channel);

        for (String arg : args) {
            if (arg != null) {
                out.writeUTF(arg);
            }
        }
        return b.toByteArray();
    }

    public static String[] parse(DataInputStream in, int length) throws IOException {
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = in.readUTF();
        }
        return result;
    }
}
