package college.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Name  重复读的servlet
 *
 * @author xuxb
 * Date 2018-12-10
 * VersionV1.0
 * @description
 */
public class RepeatReadServletRequestWrapper extends HttpServletRequestWrapper {
    private String requestBody = null;

    public RepeatReadServletRequestWrapper(HttpServletRequest request) {
        super(request);
        if (requestBody == null) {
            requestBody = readBody(request);
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomServletInputStream(requestBody);
    }

    private static String readBody(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String inputLine;
        BufferedReader br = null;
        try {
            br = request.getReader();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read body.", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    private class CustomServletInputStream extends ServletInputStream {
        private ByteArrayInputStream buffer;

        public CustomServletInputStream(String body) {
            body = body == null ? "" : body;
            this.buffer = new ByteArrayInputStream(body.getBytes());
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }

}
