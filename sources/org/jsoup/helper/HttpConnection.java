package org.jsoup.helper;

import j$.net.URLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import org.jsoup.Connection;
import org.jsoup.UncheckedIOException;
import org.jsoup.internal.ConstrainableInputStream;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.TokenQueue;

public class HttpConnection implements Connection {
    public static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
    private static final String DefaultUploadType = "application/octet-stream";
    private static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final int HTTP_TEMP_REDIR = 307;
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String USER_AGENT = "User-Agent";
    private Connection.Request req = new Request();
    private Connection.Response res = new Response();

    public static abstract class Base<T extends Connection.Base> implements Connection.Base<T> {
        Map<String, String> cookies;
        Map<String, List<String>> headers;
        Connection.Method method;
        URL url;

        private static String fixHeaderEncoding(String str) {
            try {
                byte[] bytes = str.getBytes("ISO-8859-1");
                if (!looksLikeUtf8(bytes)) {
                    return str;
                }
                return new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException unused) {
                return str;
            }
        }

        private List<String> getHeadersCaseInsensitive(String str) {
            Validate.notNull(str);
            for (Map.Entry next : this.headers.entrySet()) {
                if (str.equalsIgnoreCase((String) next.getKey())) {
                    return (List) next.getValue();
                }
            }
            return Collections.emptyList();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
            if ((r0 & r4) != false) goto L_0x002a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static boolean looksLikeUtf8(byte[] r8) {
            /*
                int r0 = r8.length
                r1 = 1
                r2 = 0
                r3 = 3
                if (r0 < r3) goto L_0x0029
                byte r0 = r8[r2]
                r0 = r0 & 255(0xff, float:3.57E-43)
                r4 = 239(0xef, float:3.35E-43)
                if (r0 != r4) goto L_0x0029
                byte r0 = r8[r1]
                r0 = r0 & 255(0xff, float:3.57E-43)
                r4 = 187(0xbb, float:2.62E-43)
                if (r0 != r4) goto L_0x0018
                r0 = 1
                goto L_0x0019
            L_0x0018:
                r0 = 0
            L_0x0019:
                r4 = 2
                byte r4 = r8[r4]
                r4 = r4 & 255(0xff, float:3.57E-43)
                r5 = 191(0xbf, float:2.68E-43)
                if (r4 != r5) goto L_0x0024
                r4 = 1
                goto L_0x0025
            L_0x0024:
                r4 = 0
            L_0x0025:
                r0 = r0 & r4
                if (r0 == 0) goto L_0x0029
                goto L_0x002a
            L_0x0029:
                r3 = 0
            L_0x002a:
                int r0 = r8.length
            L_0x002b:
                if (r3 >= r0) goto L_0x005d
                byte r4 = r8[r3]
                r5 = r4 & 128(0x80, float:1.794E-43)
                if (r5 != 0) goto L_0x0034
                goto L_0x005a
            L_0x0034:
                r5 = r4 & 224(0xe0, float:3.14E-43)
                r6 = 192(0xc0, float:2.69E-43)
                if (r5 != r6) goto L_0x003d
                int r4 = r3 + 1
                goto L_0x004e
            L_0x003d:
                r5 = r4 & 240(0xf0, float:3.36E-43)
                r7 = 224(0xe0, float:3.14E-43)
                if (r5 != r7) goto L_0x0046
                int r4 = r3 + 2
                goto L_0x004e
            L_0x0046:
                r4 = r4 & 248(0xf8, float:3.48E-43)
                r5 = 240(0xf0, float:3.36E-43)
                if (r4 != r5) goto L_0x005c
                int r4 = r3 + 3
            L_0x004e:
                if (r3 >= r4) goto L_0x005a
                int r3 = r3 + 1
                byte r5 = r8[r3]
                r5 = r5 & r6
                r7 = 128(0x80, float:1.794E-43)
                if (r5 == r7) goto L_0x004e
                return r2
            L_0x005a:
                int r3 = r3 + r1
                goto L_0x002b
            L_0x005c:
                return r2
            L_0x005d:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.HttpConnection.Base.looksLikeUtf8(byte[]):boolean");
        }

        private Map.Entry<String, List<String>> scanHeaders(String str) {
            String lowerCase = Normalizer.lowerCase(str);
            for (Map.Entry<String, List<String>> next : this.headers.entrySet()) {
                if (Normalizer.lowerCase(next.getKey()).equals(lowerCase)) {
                    return next;
                }
            }
            return null;
        }

        public T addHeader(String str, String str2) {
            Validate.notEmpty(str);
            if (str2 == null) {
                str2 = "";
            }
            List headers2 = headers(str);
            if (headers2.isEmpty()) {
                headers2 = new ArrayList();
                this.headers.put(str, headers2);
            }
            headers2.add(fixHeaderEncoding(str2));
            return this;
        }

        public String cookie(String str) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            return this.cookies.get(str);
        }

        public Map<String, String> cookies() {
            return this.cookies;
        }

        public boolean hasCookie(String str) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            return this.cookies.containsKey(str);
        }

        public boolean hasHeader(String str) {
            Validate.notEmpty(str, "Header name must not be empty");
            if (getHeadersCaseInsensitive(str).size() != 0) {
                return true;
            }
            return false;
        }

        public boolean hasHeaderWithValue(String str, String str2) {
            Validate.notEmpty(str);
            Validate.notEmpty(str2);
            for (String equalsIgnoreCase : headers(str)) {
                if (str2.equalsIgnoreCase(equalsIgnoreCase)) {
                    return true;
                }
            }
            return false;
        }

        public String header(String str) {
            Validate.notNull(str, "Header name must not be null");
            List<String> headersCaseInsensitive = getHeadersCaseInsensitive(str);
            if (headersCaseInsensitive.size() > 0) {
                return StringUtil.join((Collection) headersCaseInsensitive, ", ");
            }
            return null;
        }

        public List<String> headers(String str) {
            Validate.notEmpty(str);
            return getHeadersCaseInsensitive(str);
        }

        public Connection.Method method() {
            return this.method;
        }

        public Map<String, List<String>> multiHeaders() {
            return this.headers;
        }

        public T removeCookie(String str) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            this.cookies.remove(str);
            return this;
        }

        public T removeHeader(String str) {
            Validate.notEmpty(str, "Header name must not be empty");
            Map.Entry<String, List<String>> scanHeaders = scanHeaders(str);
            if (scanHeaders != null) {
                this.headers.remove(scanHeaders.getKey());
            }
            return this;
        }

        public URL url() {
            return this.url;
        }

        private Base() {
            this.headers = new LinkedHashMap();
            this.cookies = new LinkedHashMap();
        }

        public T method(Connection.Method method2) {
            Validate.notNull(method2, "Method must not be null");
            this.method = method2;
            return this;
        }

        public T url(URL url2) {
            Validate.notNull(url2, "URL must not be null");
            this.url = url2;
            return this;
        }

        public T cookie(String str, String str2) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            Validate.notNull(str2, "Cookie value must not be null");
            this.cookies.put(str, str2);
            return this;
        }

        public Map<String, String> headers() {
            LinkedHashMap linkedHashMap = new LinkedHashMap(this.headers.size());
            for (Map.Entry next : this.headers.entrySet()) {
                String str = (String) next.getKey();
                List list = (List) next.getValue();
                if (list.size() > 0) {
                    linkedHashMap.put(str, list.get(0));
                }
            }
            return linkedHashMap;
        }

        public T header(String str, String str2) {
            Validate.notEmpty(str, "Header name must not be empty");
            removeHeader(str);
            addHeader(str, str2);
            return this;
        }
    }

    public static class KeyVal implements Connection.KeyVal {
        private String contentType;
        private String key;
        private InputStream stream;
        private String value;

        private KeyVal() {
        }

        public static KeyVal create(String str, String str2) {
            return new KeyVal().key(str).value(str2);
        }

        public Connection.KeyVal contentType(String str) {
            Validate.notEmpty(str);
            this.contentType = str;
            return this;
        }

        public boolean hasInputStream() {
            return this.stream != null;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        public static KeyVal create(String str, String str2, InputStream inputStream) {
            return new KeyVal().key(str).value(str2).inputStream(inputStream);
        }

        public KeyVal inputStream(InputStream inputStream) {
            Validate.notNull(this.value, "Data input stream must not be null");
            this.stream = inputStream;
            return this;
        }

        public KeyVal key(String str) {
            Validate.notEmpty(str, "Data key must not be empty");
            this.key = str;
            return this;
        }

        public KeyVal value(String str) {
            Validate.notNull(str, "Data value must not be null");
            this.value = str;
            return this;
        }

        public String contentType() {
            return this.contentType;
        }

        public InputStream inputStream() {
            return this.stream;
        }

        public String key() {
            return this.key;
        }

        public String value() {
            return this.value;
        }
    }

    public static class Request extends Base<Connection.Request> implements Connection.Request {
        private String body = null;
        private Collection<Connection.KeyVal> data = new ArrayList();
        private boolean followRedirects = true;
        private boolean ignoreContentType = false;
        private boolean ignoreHttpErrors = false;
        private int maxBodySizeBytes = 1048576;
        private Parser parser;
        /* access modifiers changed from: private */
        public boolean parserDefined = false;
        private String postDataCharset = "UTF-8";
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;
        private int timeoutMilliseconds = 30000;
        private boolean validateTSLCertificates = true;

        public Request() {
            super();
            this.method = Connection.Method.GET;
            addHeader("Accept-Encoding", "gzip");
            addHeader(HttpConnection.USER_AGENT, HttpConnection.DEFAULT_UA);
            this.parser = Parser.htmlParser();
        }

        public /* bridge */ /* synthetic */ String cookie(String str) {
            return super.cookie(str);
        }

        public /* bridge */ /* synthetic */ Map cookies() {
            return super.cookies();
        }

        public boolean followRedirects() {
            return this.followRedirects;
        }

        public /* bridge */ /* synthetic */ boolean hasCookie(String str) {
            return super.hasCookie(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeader(String str) {
            return super.hasHeader(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeaderWithValue(String str, String str2) {
            return super.hasHeaderWithValue(str, str2);
        }

        public /* bridge */ /* synthetic */ String header(String str) {
            return super.header(str);
        }

        public /* bridge */ /* synthetic */ List headers(String str) {
            return super.headers(str);
        }

        public boolean ignoreContentType() {
            return this.ignoreContentType;
        }

        public boolean ignoreHttpErrors() {
            return this.ignoreHttpErrors;
        }

        public int maxBodySize() {
            return this.maxBodySizeBytes;
        }

        public /* bridge */ /* synthetic */ Connection.Method method() {
            return super.method();
        }

        public /* bridge */ /* synthetic */ Map multiHeaders() {
            return super.multiHeaders();
        }

        public Connection.Request postDataCharset(String str) {
            Validate.notNull(str, "Charset must not be null");
            if (Charset.isSupported(str)) {
                this.postDataCharset = str;
                return this;
            }
            throw new IllegalCharsetNameException(str);
        }

        public Connection.Request requestBody(String str) {
            this.body = str;
            return this;
        }

        public SSLSocketFactory sslSocketFactory() {
            return this.sslSocketFactory;
        }

        public /* bridge */ /* synthetic */ URL url() {
            return super.url();
        }

        public boolean validateTLSCertificates() {
            return this.validateTSLCertificates;
        }

        public Request data(Connection.KeyVal keyVal) {
            Validate.notNull(keyVal, "Key val must not be null");
            this.data.add(keyVal);
            return this;
        }

        public Connection.Request followRedirects(boolean z) {
            this.followRedirects = z;
            return this;
        }

        public /* bridge */ /* synthetic */ Map headers() {
            return super.headers();
        }

        public Connection.Request ignoreContentType(boolean z) {
            this.ignoreContentType = z;
            return this;
        }

        public Connection.Request ignoreHttpErrors(boolean z) {
            this.ignoreHttpErrors = z;
            return this;
        }

        public Connection.Request maxBodySize(int i) {
            Validate.isTrue(i >= 0, "maxSize must be 0 (unlimited) or larger");
            this.maxBodySizeBytes = i;
            return this;
        }

        public Request parser(Parser parser2) {
            this.parser = parser2;
            this.parserDefined = true;
            return this;
        }

        public String requestBody() {
            return this.body;
        }

        public void sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactory = sSLSocketFactory;
        }

        public int timeout() {
            return this.timeoutMilliseconds;
        }

        public void validateTLSCertificates(boolean z) {
            this.validateTSLCertificates = z;
        }

        public Proxy proxy() {
            return this.proxy;
        }

        public Request timeout(int i) {
            Validate.isTrue(i >= 0, "Timeout milliseconds must be 0 (infinite) or greater");
            this.timeoutMilliseconds = i;
            return this;
        }

        public Collection<Connection.KeyVal> data() {
            return this.data;
        }

        public Parser parser() {
            return this.parser;
        }

        public Request proxy(Proxy proxy2) {
            this.proxy = proxy2;
            return this;
        }

        public String postDataCharset() {
            return this.postDataCharset;
        }

        public Request proxy(String str, int i) {
            this.proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(str, i));
            return this;
        }
    }

    public static class Response extends Base<Connection.Response> implements Connection.Response {
        private static final String LOCATION = "Location";
        private static final int MAX_REDIRECTS = 20;
        private static SSLSocketFactory sslSocketFactory;
        private static final Pattern xmlContentTypeRxp = Pattern.compile("(application|text)/\\w*\\+?xml.*");
        private InputStream bodyStream;
        private ByteBuffer byteData;
        private String charset;
        private String contentType;
        private boolean executed = false;
        private boolean inputStreamRead = false;
        private int numRedirects = 0;
        private Connection.Request req;
        private int statusCode;
        private String statusMessage;

        public Response() {
            super();
        }

        private static HttpURLConnection createConnection(Connection.Request request) throws IOException {
            URLConnection uRLConnection;
            if (request.proxy() == null) {
                uRLConnection = request.url().openConnection();
            } else {
                uRLConnection = request.url().openConnection(request.proxy());
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnection;
            httpURLConnection.setRequestMethod(request.method().name());
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setConnectTimeout(request.timeout());
            httpURLConnection.setReadTimeout(request.timeout() / 2);
            if (httpURLConnection instanceof HttpsURLConnection) {
                SSLSocketFactory sslSocketFactory2 = request.sslSocketFactory();
                if (sslSocketFactory2 != null) {
                    ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sslSocketFactory2);
                } else if (!request.validateTLSCertificates()) {
                    initUnSecureTSL();
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
                    httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
                    httpsURLConnection.setHostnameVerifier(getInsecureVerifier());
                }
            }
            if (request.method().hasBody()) {
                httpURLConnection.setDoOutput(true);
            }
            if (request.cookies().size() > 0) {
                httpURLConnection.addRequestProperty("Cookie", getRequestCookieString(request));
            }
            for (Map.Entry next : request.multiHeaders().entrySet()) {
                for (String addRequestProperty : (List) next.getValue()) {
                    httpURLConnection.addRequestProperty((String) next.getKey(), addRequestProperty);
                }
            }
            return httpURLConnection;
        }

        private static LinkedHashMap<String, List<String>> createHeaderMap(HttpURLConnection httpURLConnection) {
            LinkedHashMap<String, List<String>> linkedHashMap = new LinkedHashMap<>();
            int i = 0;
            while (true) {
                String headerFieldKey = httpURLConnection.getHeaderFieldKey(i);
                String headerField = httpURLConnection.getHeaderField(i);
                if (headerFieldKey == null && headerField == null) {
                    return linkedHashMap;
                }
                i++;
                if (!(headerFieldKey == null || headerField == null)) {
                    if (linkedHashMap.containsKey(headerFieldKey)) {
                        linkedHashMap.get(headerFieldKey).add(headerField);
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(headerField);
                        linkedHashMap.put(headerFieldKey, arrayList);
                    }
                }
            }
        }

        public static Response execute(Connection.Request request) throws IOException {
            return execute(request, (Response) null);
        }

        private static HostnameVerifier getInsecureVerifier() {
            return new HostnameVerifier() {
                public boolean verify(String str, SSLSession sSLSession) {
                    return true;
                }
            };
        }

        private static String getRequestCookieString(Connection.Request request) {
            StringBuilder stringBuilder = StringUtil.stringBuilder();
            boolean z = true;
            for (Map.Entry next : request.cookies().entrySet()) {
                if (!z) {
                    stringBuilder.append("; ");
                } else {
                    z = false;
                }
                stringBuilder.append((String) next.getKey());
                stringBuilder.append('=');
                stringBuilder.append((String) next.getValue());
            }
            return stringBuilder.toString();
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(7:5|6|7|8|9|10|11) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0028 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static synchronized void initUnSecureTSL() throws java.io.IOException {
            /*
                java.lang.Class<org.jsoup.helper.HttpConnection$Response> r0 = org.jsoup.helper.HttpConnection.Response.class
                monitor-enter(r0)
                javax.net.ssl.SSLSocketFactory r1 = sslSocketFactory     // Catch:{ all -> 0x0032 }
                if (r1 != 0) goto L_0x0030
                r1 = 1
                javax.net.ssl.TrustManager[] r1 = new javax.net.ssl.TrustManager[r1]     // Catch:{ all -> 0x0032 }
                org.jsoup.helper.HttpConnection$Response$2 r2 = new org.jsoup.helper.HttpConnection$Response$2     // Catch:{ all -> 0x0032 }
                r2.<init>()     // Catch:{ all -> 0x0032 }
                r3 = 0
                r1[r3] = r2     // Catch:{ all -> 0x0032 }
                java.lang.String r2 = "SSL"
                javax.net.ssl.SSLContext r2 = javax.net.ssl.SSLContext.getInstance(r2)     // Catch:{ KeyManagementException | NoSuchAlgorithmException -> 0x0028 }
                java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ KeyManagementException | NoSuchAlgorithmException -> 0x0028 }
                r3.<init>()     // Catch:{ KeyManagementException | NoSuchAlgorithmException -> 0x0028 }
                r4 = 0
                r2.init(r4, r1, r3)     // Catch:{ KeyManagementException | NoSuchAlgorithmException -> 0x0028 }
                javax.net.ssl.SSLSocketFactory r1 = r2.getSocketFactory()     // Catch:{ KeyManagementException | NoSuchAlgorithmException -> 0x0028 }
                sslSocketFactory = r1     // Catch:{ KeyManagementException | NoSuchAlgorithmException -> 0x0028 }
                goto L_0x0030
            L_0x0028:
                java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0032 }
                java.lang.String r2 = "Can't create unsecure trust manager"
                r1.<init>(r2)     // Catch:{ all -> 0x0032 }
                throw r1     // Catch:{ all -> 0x0032 }
            L_0x0030:
                monitor-exit(r0)
                return
            L_0x0032:
                r1 = move-exception
                monitor-exit(r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.HttpConnection.Response.initUnSecureTSL():void");
        }

        private void prepareByteData() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            if (this.byteData == null) {
                Validate.isFalse(this.inputStreamRead, "Request has already been read (with .parse())");
                try {
                    this.byteData = DataUtil.readToByteBuffer(this.bodyStream, this.req.maxBodySize());
                    this.inputStreamRead = true;
                    safeClose();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                } catch (Throwable th) {
                    this.inputStreamRead = true;
                    safeClose();
                    throw th;
                }
            }
        }

        private void safeClose() {
            InputStream inputStream = this.bodyStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                } catch (Throwable th) {
                    this.bodyStream = null;
                    throw th;
                }
                this.bodyStream = null;
            }
        }

        private static void serialiseRequestUrl(Connection.Request request) throws IOException {
            boolean z;
            URL url = request.url();
            StringBuilder stringBuilder = StringUtil.stringBuilder();
            stringBuilder.append(url.getProtocol());
            stringBuilder.append("://");
            stringBuilder.append(url.getAuthority());
            stringBuilder.append(url.getPath());
            stringBuilder.append("?");
            if (url.getQuery() != null) {
                stringBuilder.append(url.getQuery());
                z = false;
            } else {
                z = true;
            }
            for (Connection.KeyVal next : request.data()) {
                Validate.isFalse(next.hasInputStream(), "InputStream data not supported in URL query string.");
                if (!z) {
                    stringBuilder.append('&');
                } else {
                    z = false;
                }
                stringBuilder.append(URLEncoder.encode(next.key(), "UTF-8"));
                stringBuilder.append('=');
                stringBuilder.append(URLEncoder.encode(next.value(), "UTF-8"));
            }
            request.url(new URL(stringBuilder.toString()));
            request.data().clear();
        }

        private static String setOutputContentType(Connection.Request request) {
            if (!request.hasHeader(HttpConnection.CONTENT_TYPE)) {
                if (HttpConnection.needsMultipart(request)) {
                    String mimeBoundary = DataUtil.mimeBoundary();
                    request.header(HttpConnection.CONTENT_TYPE, "multipart/form-data; boundary=" + mimeBoundary);
                    return mimeBoundary;
                }
                request.header(HttpConnection.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + request.postDataCharset());
            }
            return null;
        }

        private void setupFromConnection(HttpURLConnection httpURLConnection, Connection.Response response) throws IOException {
            this.method = Connection.Method.valueOf(httpURLConnection.getRequestMethod());
            this.url = httpURLConnection.getURL();
            this.statusCode = httpURLConnection.getResponseCode();
            this.statusMessage = httpURLConnection.getResponseMessage();
            this.contentType = httpURLConnection.getContentType();
            processResponseHeaders(createHeaderMap(httpURLConnection));
            if (response != null) {
                for (Map.Entry next : response.cookies().entrySet()) {
                    if (!hasCookie((String) next.getKey())) {
                        cookie((String) next.getKey(), (String) next.getValue());
                    }
                }
            }
        }

        private static void writePost(Connection.Request request, OutputStream outputStream, String str) throws IOException {
            String str2;
            Collection<Connection.KeyVal> data = request.data();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, request.postDataCharset()));
            if (str != null) {
                for (Connection.KeyVal next : data) {
                    bufferedWriter.write("--");
                    bufferedWriter.write(str);
                    bufferedWriter.write("\r\n");
                    bufferedWriter.write("Content-Disposition: form-data; name=\"");
                    bufferedWriter.write(HttpConnection.encodeMimeName(next.key()));
                    bufferedWriter.write("\"");
                    if (next.hasInputStream()) {
                        bufferedWriter.write("; filename=\"");
                        bufferedWriter.write(HttpConnection.encodeMimeName(next.value()));
                        bufferedWriter.write("\"\r\nContent-Type: ");
                        if (next.contentType() != null) {
                            str2 = next.contentType();
                        } else {
                            str2 = HttpConnection.DefaultUploadType;
                        }
                        bufferedWriter.write(str2);
                        bufferedWriter.write("\r\n\r\n");
                        bufferedWriter.flush();
                        DataUtil.crossStreams(next.inputStream(), outputStream);
                        outputStream.flush();
                    } else {
                        bufferedWriter.write("\r\n\r\n");
                        bufferedWriter.write(next.value());
                    }
                    bufferedWriter.write("\r\n");
                }
                bufferedWriter.write("--");
                bufferedWriter.write(str);
                bufferedWriter.write("--");
            } else if (request.requestBody() != null) {
                bufferedWriter.write(request.requestBody());
            } else {
                boolean z = true;
                for (Connection.KeyVal next2 : data) {
                    if (!z) {
                        bufferedWriter.append('&');
                    } else {
                        z = false;
                    }
                    bufferedWriter.write(URLEncoder.encode(next2.key(), request.postDataCharset()));
                    bufferedWriter.write(61);
                    bufferedWriter.write(URLEncoder.encode(next2.value(), request.postDataCharset()));
                }
            }
            bufferedWriter.close();
        }

        public String body() {
            String str;
            prepareByteData();
            String str2 = this.charset;
            if (str2 == null) {
                str = Charset.forName("UTF-8").decode(this.byteData).toString();
            } else {
                str = Charset.forName(str2).decode(this.byteData).toString();
            }
            this.byteData.rewind();
            return str;
        }

        public byte[] bodyAsBytes() {
            prepareByteData();
            return this.byteData.array();
        }

        public BufferedInputStream bodyStream() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            Validate.isFalse(this.inputStreamRead, "Request has already been read");
            this.inputStreamRead = true;
            return ConstrainableInputStream.wrap(this.bodyStream, 32768, this.req.maxBodySize());
        }

        public Connection.Response bufferUp() {
            prepareByteData();
            return this;
        }

        public String contentType() {
            return this.contentType;
        }

        public /* bridge */ /* synthetic */ String cookie(String str) {
            return super.cookie(str);
        }

        public /* bridge */ /* synthetic */ Map cookies() {
            return super.cookies();
        }

        public /* bridge */ /* synthetic */ boolean hasCookie(String str) {
            return super.hasCookie(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeader(String str) {
            return super.hasHeader(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeaderWithValue(String str, String str2) {
            return super.hasHeaderWithValue(str, str2);
        }

        public /* bridge */ /* synthetic */ String header(String str) {
            return super.header(str);
        }

        public /* bridge */ /* synthetic */ List headers(String str) {
            return super.headers(str);
        }

        public /* bridge */ /* synthetic */ Connection.Method method() {
            return super.method();
        }

        public /* bridge */ /* synthetic */ Map multiHeaders() {
            return super.multiHeaders();
        }

        public Document parse() throws IOException {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
            if (this.byteData != null) {
                this.bodyStream = new ByteArrayInputStream(this.byteData.array());
                this.inputStreamRead = false;
            }
            Validate.isFalse(this.inputStreamRead, "Input stream already read and parsed, cannot re-read.");
            Document parseInputStream = DataUtil.parseInputStream(this.bodyStream, this.charset, this.url.toExternalForm(), this.req.parser());
            this.charset = parseInputStream.outputSettings().charset().name();
            this.inputStreamRead = true;
            safeClose();
            return parseInputStream;
        }

        public void processResponseHeaders(Map<String, List<String>> map) {
            for (Map.Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                if (str != null) {
                    List<String> list = (List) next.getValue();
                    if (str.equalsIgnoreCase("Set-Cookie")) {
                        for (String str2 : list) {
                            if (str2 != null) {
                                TokenQueue tokenQueue = new TokenQueue(str2);
                                String trim = tokenQueue.chompTo("=").trim();
                                String trim2 = tokenQueue.consumeTo(";").trim();
                                if (trim.length() > 0) {
                                    cookie(trim, trim2);
                                }
                            }
                        }
                    }
                    for (String addHeader : list) {
                        addHeader(str, addHeader);
                    }
                }
            }
        }

        public int statusCode() {
            return this.statusCode;
        }

        public String statusMessage() {
            return this.statusMessage;
        }

        public /* bridge */ /* synthetic */ URL url() {
            return super.url();
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x007f A[Catch:{ IOException -> 0x01f1 }] */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x00a0 A[Catch:{ IOException -> 0x01f1 }] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x010d A[Catch:{ IOException -> 0x01f1 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static org.jsoup.helper.HttpConnection.Response execute(org.jsoup.Connection.Request r9, org.jsoup.helper.HttpConnection.Response r10) throws java.io.IOException {
            /*
                java.lang.String r0 = "Content-Encoding"
                java.lang.String r1 = "Location"
                java.lang.String r2 = "Request must not be null"
                org.jsoup.helper.Validate.notNull(r9, r2)
                java.net.URL r2 = r9.url()
                java.lang.String r2 = r2.getProtocol()
                java.lang.String r3 = "http"
                boolean r3 = r2.equals(r3)
                if (r3 != 0) goto L_0x002a
                java.lang.String r3 = "https"
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x0022
                goto L_0x002a
            L_0x0022:
                java.net.MalformedURLException r9 = new java.net.MalformedURLException
                java.lang.String r10 = "Only http & https protocols supported"
                r9.<init>(r10)
                throw r9
            L_0x002a:
                org.jsoup.Connection$Method r2 = r9.method()
                boolean r2 = r2.hasBody()
                java.lang.String r3 = r9.requestBody()
                r4 = 1
                if (r3 == 0) goto L_0x003b
                r3 = 1
                goto L_0x003c
            L_0x003b:
                r3 = 0
            L_0x003c:
                if (r2 != 0) goto L_0x0053
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                java.lang.String r6 = "Cannot set a request body for HTTP method "
                r5.<init>(r6)
                org.jsoup.Connection$Method r6 = r9.method()
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                org.jsoup.helper.Validate.isFalse(r3, r5)
            L_0x0053:
                java.util.Collection r5 = r9.data()
                int r5 = r5.size()
                r6 = 0
                if (r5 <= 0) goto L_0x0066
                if (r2 == 0) goto L_0x0062
                if (r3 == 0) goto L_0x0066
            L_0x0062:
                serialiseRequestUrl(r9)
                goto L_0x006d
            L_0x0066:
                if (r2 == 0) goto L_0x006d
                java.lang.String r2 = setOutputContentType(r9)
                goto L_0x006e
            L_0x006d:
                r2 = r6
            L_0x006e:
                long r7 = java.lang.System.nanoTime()
                java.net.HttpURLConnection r3 = createConnection(r9)
                r3.connect()     // Catch:{ IOException -> 0x01f1 }
                boolean r5 = r3.getDoOutput()     // Catch:{ IOException -> 0x01f1 }
                if (r5 == 0) goto L_0x0086
                java.io.OutputStream r5 = r3.getOutputStream()     // Catch:{ IOException -> 0x01f1 }
                writePost(r9, r5, r2)     // Catch:{ IOException -> 0x01f1 }
            L_0x0086:
                int r2 = r3.getResponseCode()     // Catch:{ IOException -> 0x01f1 }
                org.jsoup.helper.HttpConnection$Response r5 = new org.jsoup.helper.HttpConnection$Response     // Catch:{ IOException -> 0x01f1 }
                r5.<init>(r10)     // Catch:{ IOException -> 0x01f1 }
                r5.setupFromConnection(r3, r10)     // Catch:{ IOException -> 0x01f1 }
                r5.req = r9     // Catch:{ IOException -> 0x01f1 }
                boolean r10 = r5.hasHeader(r1)     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x010d
                boolean r10 = r9.followRedirects()     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x010d
                r10 = 307(0x133, float:4.3E-43)
                if (r2 == r10) goto L_0x00b8
                org.jsoup.Connection$Method r10 = org.jsoup.Connection.Method.GET     // Catch:{ IOException -> 0x01f1 }
                r9.method(r10)     // Catch:{ IOException -> 0x01f1 }
                java.util.Collection r10 = r9.data()     // Catch:{ IOException -> 0x01f1 }
                r10.clear()     // Catch:{ IOException -> 0x01f1 }
                r9.requestBody(r6)     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r10 = "Content-Type"
                r9.removeHeader(r10)     // Catch:{ IOException -> 0x01f1 }
            L_0x00b8:
                java.lang.String r10 = r5.header(r1)     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x00d3
                java.lang.String r0 = "http:/"
                boolean r0 = r10.startsWith(r0)     // Catch:{ IOException -> 0x01f1 }
                if (r0 == 0) goto L_0x00d3
                r0 = 6
                char r1 = r10.charAt(r0)     // Catch:{ IOException -> 0x01f1 }
                r2 = 47
                if (r1 == r2) goto L_0x00d3
                java.lang.String r10 = r10.substring(r0)     // Catch:{ IOException -> 0x01f1 }
            L_0x00d3:
                java.net.URL r0 = r9.url()     // Catch:{ IOException -> 0x01f1 }
                java.net.URL r10 = org.jsoup.helper.StringUtil.resolve((java.net.URL) r0, (java.lang.String) r10)     // Catch:{ IOException -> 0x01f1 }
                java.net.URL r10 = org.jsoup.helper.HttpConnection.encodeUrl((java.net.URL) r10)     // Catch:{ IOException -> 0x01f1 }
                r9.url(r10)     // Catch:{ IOException -> 0x01f1 }
                java.util.Map<java.lang.String, java.lang.String> r10 = r5.cookies     // Catch:{ IOException -> 0x01f1 }
                java.util.Set r10 = r10.entrySet()     // Catch:{ IOException -> 0x01f1 }
                java.util.Iterator r10 = r10.iterator()     // Catch:{ IOException -> 0x01f1 }
            L_0x00ec:
                boolean r0 = r10.hasNext()     // Catch:{ IOException -> 0x01f1 }
                if (r0 == 0) goto L_0x0108
                java.lang.Object r0 = r10.next()     // Catch:{ IOException -> 0x01f1 }
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ IOException -> 0x01f1 }
                java.lang.Object r1 = r0.getKey()     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r1 = (java.lang.String) r1     // Catch:{ IOException -> 0x01f1 }
                java.lang.Object r0 = r0.getValue()     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r0 = (java.lang.String) r0     // Catch:{ IOException -> 0x01f1 }
                r9.cookie(r1, r0)     // Catch:{ IOException -> 0x01f1 }
                goto L_0x00ec
            L_0x0108:
                org.jsoup.helper.HttpConnection$Response r9 = execute(r9, r5)     // Catch:{ IOException -> 0x01f1 }
                return r9
            L_0x010d:
                r10 = 200(0xc8, float:2.8E-43)
                if (r2 < r10) goto L_0x0115
                r10 = 400(0x190, float:5.6E-43)
                if (r2 < r10) goto L_0x011b
            L_0x0115:
                boolean r10 = r9.ignoreHttpErrors()     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x01e1
            L_0x011b:
                java.lang.String r10 = r5.contentType()     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x014c
                boolean r1 = r9.ignoreContentType()     // Catch:{ IOException -> 0x01f1 }
                if (r1 != 0) goto L_0x014c
                java.lang.String r1 = "text/"
                boolean r1 = r10.startsWith(r1)     // Catch:{ IOException -> 0x01f1 }
                if (r1 != 0) goto L_0x014c
                java.util.regex.Pattern r1 = xmlContentTypeRxp     // Catch:{ IOException -> 0x01f1 }
                java.util.regex.Matcher r1 = r1.matcher(r10)     // Catch:{ IOException -> 0x01f1 }
                boolean r1 = r1.matches()     // Catch:{ IOException -> 0x01f1 }
                if (r1 == 0) goto L_0x013c
                goto L_0x014c
            L_0x013c:
                org.jsoup.UnsupportedMimeTypeException r0 = new org.jsoup.UnsupportedMimeTypeException     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r1 = "Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml"
                java.net.URL r9 = r9.url()     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r9 = r9.toString()     // Catch:{ IOException -> 0x01f1 }
                r0.<init>(r1, r10, r9)     // Catch:{ IOException -> 0x01f1 }
                throw r0     // Catch:{ IOException -> 0x01f1 }
            L_0x014c:
                if (r10 == 0) goto L_0x016e
                java.util.regex.Pattern r1 = xmlContentTypeRxp     // Catch:{ IOException -> 0x01f1 }
                java.util.regex.Matcher r10 = r1.matcher(r10)     // Catch:{ IOException -> 0x01f1 }
                boolean r10 = r10.matches()     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x016e
                boolean r10 = r9 instanceof org.jsoup.helper.HttpConnection.Request     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x016e
                r10 = r9
                org.jsoup.helper.HttpConnection$Request r10 = (org.jsoup.helper.HttpConnection.Request) r10     // Catch:{ IOException -> 0x01f1 }
                boolean r10 = r10.parserDefined     // Catch:{ IOException -> 0x01f1 }
                if (r10 != 0) goto L_0x016e
                org.jsoup.parser.Parser r10 = org.jsoup.parser.Parser.xmlParser()     // Catch:{ IOException -> 0x01f1 }
                r9.parser(r10)     // Catch:{ IOException -> 0x01f1 }
            L_0x016e:
                java.lang.String r10 = r5.contentType     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r10 = org.jsoup.helper.DataUtil.getCharsetFromContentType(r10)     // Catch:{ IOException -> 0x01f1 }
                r5.charset = r10     // Catch:{ IOException -> 0x01f1 }
                int r10 = r3.getContentLength()     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x01d8
                org.jsoup.Connection$Method r10 = r9.method()     // Catch:{ IOException -> 0x01f1 }
                org.jsoup.Connection$Method r1 = org.jsoup.Connection.Method.HEAD     // Catch:{ IOException -> 0x01f1 }
                if (r10 == r1) goto L_0x01d8
                r5.bodyStream = r6     // Catch:{ IOException -> 0x01f1 }
                java.io.InputStream r10 = r3.getErrorStream()     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x0191
                java.io.InputStream r10 = r3.getErrorStream()     // Catch:{ IOException -> 0x01f1 }
                goto L_0x0195
            L_0x0191:
                java.io.InputStream r10 = r3.getInputStream()     // Catch:{ IOException -> 0x01f1 }
            L_0x0195:
                r5.bodyStream = r10     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r10 = "gzip"
                boolean r10 = r5.hasHeaderWithValue(r0, r10)     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x01a9
                java.util.zip.GZIPInputStream r10 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x01f1 }
                java.io.InputStream r0 = r5.bodyStream     // Catch:{ IOException -> 0x01f1 }
                r10.<init>(r0)     // Catch:{ IOException -> 0x01f1 }
                r5.bodyStream = r10     // Catch:{ IOException -> 0x01f1 }
                goto L_0x01bf
            L_0x01a9:
                java.lang.String r10 = "deflate"
                boolean r10 = r5.hasHeaderWithValue(r0, r10)     // Catch:{ IOException -> 0x01f1 }
                if (r10 == 0) goto L_0x01bf
                java.util.zip.InflaterInputStream r10 = new java.util.zip.InflaterInputStream     // Catch:{ IOException -> 0x01f1 }
                java.io.InputStream r0 = r5.bodyStream     // Catch:{ IOException -> 0x01f1 }
                java.util.zip.Inflater r1 = new java.util.zip.Inflater     // Catch:{ IOException -> 0x01f1 }
                r1.<init>(r4)     // Catch:{ IOException -> 0x01f1 }
                r10.<init>(r0, r1)     // Catch:{ IOException -> 0x01f1 }
                r5.bodyStream = r10     // Catch:{ IOException -> 0x01f1 }
            L_0x01bf:
                java.io.InputStream r10 = r5.bodyStream     // Catch:{ IOException -> 0x01f1 }
                int r0 = r9.maxBodySize()     // Catch:{ IOException -> 0x01f1 }
                r1 = 32768(0x8000, float:4.5918E-41)
                org.jsoup.internal.ConstrainableInputStream r10 = org.jsoup.internal.ConstrainableInputStream.wrap(r10, r1, r0)     // Catch:{ IOException -> 0x01f1 }
                int r9 = r9.timeout()     // Catch:{ IOException -> 0x01f1 }
                long r0 = (long) r9     // Catch:{ IOException -> 0x01f1 }
                org.jsoup.internal.ConstrainableInputStream r9 = r10.timeout(r7, r0)     // Catch:{ IOException -> 0x01f1 }
                r5.bodyStream = r9     // Catch:{ IOException -> 0x01f1 }
                goto L_0x01de
            L_0x01d8:
                java.nio.ByteBuffer r9 = org.jsoup.helper.DataUtil.emptyByteBuffer()     // Catch:{ IOException -> 0x01f1 }
                r5.byteData = r9     // Catch:{ IOException -> 0x01f1 }
            L_0x01de:
                r5.executed = r4
                return r5
            L_0x01e1:
                org.jsoup.HttpStatusException r10 = new org.jsoup.HttpStatusException     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r0 = "HTTP error fetching URL"
                java.net.URL r9 = r9.url()     // Catch:{ IOException -> 0x01f1 }
                java.lang.String r9 = r9.toString()     // Catch:{ IOException -> 0x01f1 }
                r10.<init>(r0, r2, r9)     // Catch:{ IOException -> 0x01f1 }
                throw r10     // Catch:{ IOException -> 0x01f1 }
            L_0x01f1:
                r9 = move-exception
                r3.disconnect()
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.HttpConnection.Response.execute(org.jsoup.Connection$Request, org.jsoup.helper.HttpConnection$Response):org.jsoup.helper.HttpConnection$Response");
        }

        public String charset() {
            return this.charset;
        }

        public /* bridge */ /* synthetic */ Map headers() {
            return super.headers();
        }

        public Response charset(String str) {
            this.charset = str;
            return this;
        }

        private Response(Response response) throws IOException {
            super();
            if (response != null) {
                int i = response.numRedirects + 1;
                this.numRedirects = i;
                if (i >= 20) {
                    throw new IOException(String.format("Too many redirects occurred trying to load URL %s", new Object[]{response.url()}));
                }
            }
        }
    }

    private HttpConnection() {
    }

    public static Connection connect(String str) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(str);
        return httpConnection;
    }

    /* access modifiers changed from: private */
    public static String encodeMimeName(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\"", "%22");
    }

    private static String encodeUrl(String str) {
        try {
            return encodeUrl(new URL(str)).toExternalForm();
        } catch (Exception unused) {
            return str;
        }
    }

    /* access modifiers changed from: private */
    public static boolean needsMultipart(Connection.Request request) {
        for (Connection.KeyVal hasInputStream : request.data()) {
            if (hasInputStream.hasInputStream()) {
                return true;
            }
        }
        return false;
    }

    public Connection cookie(String str, String str2) {
        this.req.cookie(str, str2);
        return this;
    }

    public Connection cookies(Map<String, String> map) {
        Validate.notNull(map, "Cookie map must not be null");
        for (Map.Entry next : map.entrySet()) {
            this.req.cookie((String) next.getKey(), (String) next.getValue());
        }
        return this;
    }

    public Connection data(String str, String str2) {
        this.req.data(KeyVal.create(str, str2));
        return this;
    }

    public Connection.Response execute() throws IOException {
        Response execute = Response.execute(this.req);
        this.res = execute;
        return execute;
    }

    public Connection followRedirects(boolean z) {
        this.req.followRedirects(z);
        return this;
    }

    public Document get() throws IOException {
        this.req.method(Connection.Method.GET);
        execute();
        return this.res.parse();
    }

    public Connection header(String str, String str2) {
        this.req.header(str, str2);
        return this;
    }

    public Connection headers(Map<String, String> map) {
        Validate.notNull(map, "Header map must not be null");
        for (Map.Entry next : map.entrySet()) {
            this.req.header((String) next.getKey(), (String) next.getValue());
        }
        return this;
    }

    public Connection ignoreContentType(boolean z) {
        this.req.ignoreContentType(z);
        return this;
    }

    public Connection ignoreHttpErrors(boolean z) {
        this.req.ignoreHttpErrors(z);
        return this;
    }

    public Connection maxBodySize(int i) {
        this.req.maxBodySize(i);
        return this;
    }

    public Connection method(Connection.Method method) {
        this.req.method(method);
        return this;
    }

    public Connection parser(Parser parser) {
        this.req.parser(parser);
        return this;
    }

    public Document post() throws IOException {
        this.req.method(Connection.Method.POST);
        execute();
        return this.res.parse();
    }

    public Connection postDataCharset(String str) {
        this.req.postDataCharset(str);
        return this;
    }

    public Connection proxy(Proxy proxy) {
        this.req.proxy(proxy);
        return this;
    }

    public Connection referrer(String str) {
        Validate.notNull(str, "Referrer must not be null");
        this.req.header("Referer", str);
        return this;
    }

    public Connection.Request request() {
        return this.req;
    }

    public Connection requestBody(String str) {
        this.req.requestBody(str);
        return this;
    }

    public Connection.Response response() {
        return this.res;
    }

    public Connection sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.req.sslSocketFactory(sSLSocketFactory);
        return this;
    }

    public Connection timeout(int i) {
        this.req.timeout(i);
        return this;
    }

    public Connection url(URL url) {
        this.req.url(url);
        return this;
    }

    public Connection userAgent(String str) {
        Validate.notNull(str, "User agent must not be null");
        this.req.header(USER_AGENT, str);
        return this;
    }

    public Connection validateTLSCertificates(boolean z) {
        this.req.validateTLSCertificates(z);
        return this;
    }

    public Connection data(String str, String str2, InputStream inputStream) {
        this.req.data(KeyVal.create(str, str2, inputStream));
        return this;
    }

    public Connection proxy(String str, int i) {
        this.req.proxy(str, i);
        return this;
    }

    public Connection request(Connection.Request request) {
        this.req = request;
        return this;
    }

    public Connection response(Connection.Response response) {
        this.res = response;
        return this;
    }

    public Connection url(String str) {
        Validate.notEmpty(str, "Must supply a valid URL");
        try {
            this.req.url(new URL(encodeUrl(str)));
            return this;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(y2.i("Malformed URL: ", str), e);
        }
    }

    public static Connection connect(URL url) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(url);
        return httpConnection;
    }

    public static URL encodeUrl(URL url) {
        try {
            return new URL(new URI(url.toExternalForm().replaceAll(" ", "%20")).toASCIIString());
        } catch (Exception unused) {
            return url;
        }
    }

    public Connection data(String str, String str2, InputStream inputStream, String str3) {
        this.req.data(KeyVal.create(str, str2, inputStream).contentType(str3));
        return this;
    }

    public Connection data(Map<String, String> map) {
        Validate.notNull(map, "Data map must not be null");
        for (Map.Entry next : map.entrySet()) {
            this.req.data(KeyVal.create((String) next.getKey(), (String) next.getValue()));
        }
        return this;
    }

    public Connection data(String... strArr) {
        Validate.notNull(strArr, "Data key value pairs must not be null");
        Validate.isTrue(strArr.length % 2 == 0, "Must supply an even number of key value pairs");
        for (int i = 0; i < strArr.length; i += 2) {
            String str = strArr[i];
            String str2 = strArr[i + 1];
            Validate.notEmpty(str, "Data key must not be empty");
            Validate.notNull(str2, "Data value must not be null");
            this.req.data(KeyVal.create(str, str2));
        }
        return this;
    }

    public Connection data(Collection<Connection.KeyVal> collection) {
        Validate.notNull(collection, "Data collection must not be null");
        for (Connection.KeyVal data : collection) {
            this.req.data(data);
        }
        return this;
    }

    public Connection.KeyVal data(String str) {
        Validate.notEmpty(str, "Data key must not be empty");
        for (Connection.KeyVal next : request().data()) {
            if (next.key().equals(str)) {
                return next;
            }
        }
        return null;
    }
}
