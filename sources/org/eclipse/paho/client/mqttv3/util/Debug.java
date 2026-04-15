package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Debug {
    private static final String CLASS_NAME = ClientComms.class.getName();
    private static final String lineSep = System.getProperty("line.separator", "\n");
    private static final String separator = "==============";
    private String clientID;
    private ClientComms comms;
    private Logger log;

    public Debug(String str, ClientComms clientComms) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
        this.log = logger;
        this.clientID = str;
        this.comms = clientComms;
        logger.setResourceName(str);
    }

    public static String dumpProperties(Properties properties, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration<?> propertyNames = properties.propertyNames();
        String str2 = lineSep;
        stringBuffer.append(String.valueOf(str2) + "============== " + str + " ==============" + str2);
        while (propertyNames.hasMoreElements()) {
            String str3 = (String) propertyNames.nextElement();
            stringBuffer.append(String.valueOf(left(str3, 28, ' ')) + ":  " + properties.get(str3) + lineSep);
        }
        stringBuffer.append("==========================================" + lineSep);
        return stringBuffer.toString();
    }

    public static String left(String str, int i, char c) {
        if (str.length() >= i) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        stringBuffer.append(str);
        int length = i - str.length();
        while (true) {
            length--;
            if (length < 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c);
        }
    }

    public void dumpBaseDebug() {
        dumpVersion();
        dumpSystemProperties();
        dumpMemoryTrace();
    }

    public void dumpClientComms() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            this.log.fine(CLASS_NAME, "dumpClientComms", dumpProperties(clientComms.getDebug(), String.valueOf(this.clientID).concat(" : ClientComms")).toString());
        }
    }

    public void dumpClientDebug() {
        dumpClientComms();
        dumpConOptions();
        dumpClientState();
        dumpBaseDebug();
    }

    public void dumpClientState() {
        ClientComms clientComms = this.comms;
        if (clientComms != null && clientComms.getClientState() != null) {
            this.log.fine(CLASS_NAME, "dumpClientState", dumpProperties(this.comms.getClientState().getDebug(), String.valueOf(this.clientID).concat(" : ClientState")).toString());
        }
    }

    public void dumpConOptions() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            this.log.fine(CLASS_NAME, "dumpConOptions", dumpProperties(clientComms.getConOptions().getDebug(), String.valueOf(this.clientID).concat(" : Connect Options")).toString());
        }
    }

    public void dumpMemoryTrace() {
        this.log.dumpTrace();
    }

    public void dumpSystemProperties() {
        this.log.fine(CLASS_NAME, "dumpSystemProperties", dumpProperties(System.getProperties(), "SystemProperties").toString());
    }

    public void dumpVersion() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = lineSep;
        stringBuffer.append(String.valueOf(str) + "============== Version Info ==============" + str);
        stringBuffer.append(String.valueOf(left("Version", 20, ' ')) + ":  " + ClientComms.VERSION + str);
        stringBuffer.append(String.valueOf(left("Build Level", 20, ' ')) + ":  " + ClientComms.BUILD_LEVEL + str);
        StringBuilder sb = new StringBuilder("==========================================");
        sb.append(str);
        stringBuffer.append(sb.toString());
        this.log.fine(CLASS_NAME, "dumpVersion", stringBuffer.toString());
    }
}
