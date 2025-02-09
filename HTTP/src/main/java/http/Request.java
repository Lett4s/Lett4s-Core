package http;

import http.misc.DownloadCallback;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private final Map<String, String> headers = new HashMap<>();
    private HttpURLConnection connection;
    private final String endpoint;
    private final boolean output;
    private final Method method;
    private final Proxy proxy;
    private Object body;

    public Request(String endpoint) {
        this(endpoint, null);
    }

    public Request(String endpoint, Proxy proxy) {
        this(endpoint, proxy, Method.GET, false);
    }

    public Request(String endpoint, Method method, boolean output) {
        this(endpoint, null, method, output);
    }

    public Request(String endpoint, Proxy proxy, Method method, boolean output) {
        this.endpoint = endpoint;
        this.method = method;
        this.output = output;
        this.proxy = proxy;
    }

    private Request prepare() throws IOException {
        connection = BasicHttp.open(endpoint, proxy);
        connection.setRequestMethod(method.name());
        for (String header : headers.keySet()) {
            connection.addRequestProperty(header, headers.get(header));
        }
        connection.setDoOutput(output);
        if (output) {
            try (OutputStream out = connection.getOutputStream()) {
                out.write(body.toString().getBytes());
                out.flush();
            }
        }
        return this;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void write(Object output) {
        this.body = output;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public boolean isOutput() {
        return output;
    }

    public Method getMethod() {
        return method;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public Response execute() throws IOException {
        return new Response(prepare());
    }

    public Response execute(DownloadCallback callback) throws IOException {
        return new Response(prepare(), callback);
    }
}
