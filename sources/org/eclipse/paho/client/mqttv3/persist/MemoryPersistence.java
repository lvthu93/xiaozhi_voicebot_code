package org.eclipse.paho.client.mqttv3.persist;

import java.util.Enumeration;
import java.util.Hashtable;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class MemoryPersistence implements MqttClientPersistence {
    private Hashtable<String, MqttPersistable> data;

    private void checkIsOpen() throws MqttPersistenceException {
        if (this.data == null) {
            throw new MqttPersistenceException();
        }
    }

    public void clear() throws MqttPersistenceException {
        checkIsOpen();
        this.data.clear();
    }

    public void close() throws MqttPersistenceException {
        Hashtable<String, MqttPersistable> hashtable = this.data;
        if (hashtable != null) {
            hashtable.clear();
        }
    }

    public boolean containsKey(String str) throws MqttPersistenceException {
        checkIsOpen();
        return this.data.containsKey(str);
    }

    public MqttPersistable get(String str) throws MqttPersistenceException {
        checkIsOpen();
        return this.data.get(str);
    }

    public Enumeration<String> keys() throws MqttPersistenceException {
        checkIsOpen();
        return this.data.keys();
    }

    public void open(String str, String str2) throws MqttPersistenceException {
        this.data = new Hashtable<>();
    }

    public void put(String str, MqttPersistable mqttPersistable) throws MqttPersistenceException {
        checkIsOpen();
        this.data.put(str, mqttPersistable);
    }

    public void remove(String str) throws MqttPersistenceException {
        checkIsOpen();
        this.data.remove(str);
    }
}
