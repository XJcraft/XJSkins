package com.xj.skins.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflections {

    public static Object getValue(Object obj, String field) throws Exception {
        Field f = obj.getClass().getDeclaredField(field);
        if (!f.isAccessible())
            f.setAccessible(true);
        return f.get(obj);
    }

    public static void setValue(Object obj, String field, Object value)
            throws Exception {
        Field f = obj.getClass().getDeclaredField(field);
        if (!f.isAccessible())
            f.setAccessible(true);
        f.set(obj, value);
    }

    public static Object invokeMethod(Object obj, String method, Object... args)
            throws Exception {
        Class<?>[] parameterTypes = new Class<?>[args.length];
        if (args.length > 0)
            for (int i = 0; i < args.length; i++)
                parameterTypes[i] = args[i].getClass();
        Method m = obj.getClass().getMethod(method, parameterTypes);
        if (!m.isAccessible())
            m.setAccessible(true);
        return m.invoke(obj, args);
    }

    private static Constructor<?> getConstructor(Class<?> clazz, Class<?>... args) throws Exception {
        Constructor<?> c = clazz.getConstructor(args);
        if (!c.isAccessible()) {
            c.setAccessible(true);
        }
        return c;
    }

    public static Object newInstance(Class<?> clazz, Class<?>[] args, Object... initargs) throws Exception {
        return getConstructor(clazz, args).newInstance(initargs);
    }
}
