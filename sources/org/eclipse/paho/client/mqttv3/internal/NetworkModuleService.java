package org.eclipse.paho.client.mqttv3.internal;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.spi.NetworkModuleFactory;

public class NetworkModuleService {
    private static final Pattern AUTHORITY_PATTERN = Pattern.compile("((.+)@)?([^:]*)(:(\\d+))?");
    private static final int AUTH_GROUP_HOST = 3;
    private static final int AUTH_GROUP_PORT = 5;
    private static final int AUTH_GROUP_USERINFO = 2;
    private static final ServiceLoader<NetworkModuleFactory> FACTORY_SERVICE_LOADER = ServiceLoader.load(NetworkModuleFactory.class, NetworkModuleService.class.getClassLoader());
    private static Logger LOG = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, "NetworkModuleService");

    private NetworkModuleService() {
    }

    public static void applyRFC3986AuthorityPatch(URI uri) {
        int i;
        if (uri != null && uri.getHost() == null && uri.getAuthority() != null && !uri.getAuthority().isEmpty()) {
            Matcher matcher = AUTHORITY_PATTERN.matcher(uri.getAuthority());
            if (matcher.find()) {
                setURIField(uri, "userInfo", matcher.group(2));
                setURIField(uri, "host", matcher.group(3));
                String group = matcher.group(5);
                if (group != null) {
                    i = Integer.parseInt(group);
                } else {
                    i = -1;
                }
                setURIField(uri, "port", Integer.valueOf(i));
            }
        }
    }

    public static NetworkModule createInstance(String str, MqttConnectOptions mqttConnectOptions, String str2) throws MqttException, IllegalArgumentException {
        try {
            URI uri = new URI(str);
            applyRFC3986AuthorityPatch(uri);
            String lowerCase = uri.getScheme().toLowerCase();
            ServiceLoader<NetworkModuleFactory> serviceLoader = FACTORY_SERVICE_LOADER;
            synchronized (serviceLoader) {
                Iterator<NetworkModuleFactory> it = serviceLoader.iterator();
                while (it.hasNext()) {
                    NetworkModuleFactory next = it.next();
                    if (next.getSupportedUriSchemes().contains(lowerCase)) {
                        NetworkModule createNetworkModule = next.createNetworkModule(uri, mqttConnectOptions, str2);
                        return createNetworkModule;
                    }
                }
                throw new IllegalArgumentException(uri.toString());
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(str, e);
        }
    }

    private static void setURIField(URI uri, String str, Object obj) {
        try {
            Field declaredField = URI.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(uri, obj);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            Logger logger = LOG;
            String name = NetworkModuleService.class.getName();
            Object[] objArr = {uri.toString()};
            logger.warning(name, "setURIField", "115", objArr, e);
        }
    }

    public static void validateURI(String str) throws IllegalArgumentException {
        try {
            URI uri = new URI(str);
            String scheme = uri.getScheme();
            if (scheme == null || scheme.isEmpty()) {
                throw new IllegalArgumentException("missing scheme in broker URI: " + str);
            }
            String lowerCase = scheme.toLowerCase();
            ServiceLoader<NetworkModuleFactory> serviceLoader = FACTORY_SERVICE_LOADER;
            synchronized (serviceLoader) {
                Iterator<NetworkModuleFactory> it = serviceLoader.iterator();
                while (it.hasNext()) {
                    NetworkModuleFactory next = it.next();
                    if (next.getSupportedUriSchemes().contains(lowerCase)) {
                        next.validateURI(uri);
                        return;
                    }
                }
                throw new IllegalArgumentException("no NetworkModule installed for scheme \"" + lowerCase + "\" of URI \"" + str + "\"");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(y2.j("Can't parse string to URI \"", str, "\""), e);
        }
    }
}
