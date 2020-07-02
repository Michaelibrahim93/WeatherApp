package com.test.basemodule.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReflectionUtils {
    public static boolean isIdentical(Object o1, Object o2) {
        if ((o1 == null) != (o2 == null))
            return false;
        if (o1 == null)
            return true;
        if (!o1.getClass().equals(o2.getClass()))
            return false;

        ArrayList<Field> fields = new ArrayList<>();
        setAllDeclaredFields(o1.getClass(), fields);
        for (Field field : fields) {
            if (field.getName().contains("job"))
                LogUtils.here();

            field.setAccessible(true);
            Object o1FieldValue = getValue(field, o1.getClass(), o1);
            Object o2FieldValue = getValue(field, o2.getClass(), o2);
            if ((o1FieldValue == null) != (o2FieldValue == null))
                return false;
            if (o1FieldValue == null && o2FieldValue == null)
                continue;
            if (!Modifier.isStatic(field.getModifiers()) && isPrimitiveType(field.getType())) {
                if (!Utils.equals(o1FieldValue, o2FieldValue, false))
                    return false;
            }
            else if (!Modifier.isStatic(field.getModifiers()) && isListType(o1FieldValue)) {
                if(((List) o1FieldValue).size()!=((List) o2FieldValue).size())
                    return false;
                Iterator iterator1 = ((List) o1FieldValue).iterator();
                Iterator iterator2 = ((List) o2FieldValue).iterator();
                while (iterator1.hasNext()) {

                    Object obj1= iterator1.next();
                    Object obj2= iterator2.next();
                    if (!Modifier.isStatic(field.getModifiers()) && isPrimitiveType(obj1.getClass())) {
                        if (!Utils.equals(obj1, obj2, false))
                            return false;
                    }
                    else if (!Modifier.isStatic(field.getModifiers()) && !isPrimitiveType(obj1.getClass())) {
                        if (!isIdentical(obj1, obj2))
                            return false;
                    }
                }
            }
            else if (!Modifier.isStatic(field.getModifiers()) && !isPrimitiveType(field.getType())) {
                if (!isIdentical(o1FieldValue, o2FieldValue))
                    return false;
            }
        }
        return true;
    }

    private static void setAllDeclaredFields(Class<?> aClass, ArrayList<Field> fields) {
        if (aClass == null || aClass.equals(Object.class))
            return;

        fields.addAll(CollectionsUtils.toList(aClass.getDeclaredFields()));
        setAllDeclaredFields(aClass.getSuperclass(), fields);
    }

    private static boolean isListType(Object object) {
        return object instanceof Collection;
    }

    private static boolean isPrimitiveType(Class<?> type) {
        return type.isPrimitive() || type.equals(Date.class)
                || type.equals(String.class) || type.equals(Long.class)
                || type.equals(Double.class) || type.equals(Float.class)
                || type.equals(Integer.class) || type.equals(Boolean.class);
    }

    private static Object getValue(Field field, Class aClass, Object o) {
        try {
            field.setAccessible(true);
            Object value = field.get(o);
            if (value != null)
                return value;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // MZ: Find the correct method
        for (Method method : aClass.getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))
                    && method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                // MZ: Method found, run it
                try {
                    return method.invoke(o);
                } catch (IllegalAccessException ignored) {
                } catch (InvocationTargetException ignored) {
                }
            }
        }
        return null;
    }
}