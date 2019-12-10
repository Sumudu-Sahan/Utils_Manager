package com.ssw.commonutilsmanager.serializer;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;
import static com.ssw.commonutilsmanager.common.ConstantList.EMPTY_STRING;

public class ObjectSerializer {
    private static final String TAG = "ObjectSerializer";

    private static ObjectSerializer objectSerializer;

    public static synchronized ObjectSerializer getInstance() {
        if (objectSerializer == null) {
            objectSerializer = new ObjectSerializer();
        }
        return objectSerializer;
    }

    public String serializeObject(Serializable object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return EMPTY_STRING;
        }
    }

    public Object deserializeObject(String serializedData) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(serializedData, Base64.DEFAULT));
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object result;
            result = ois.readObject();
            ois.close();
            return result;
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
