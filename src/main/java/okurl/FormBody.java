package okurl;

import okurl.internal.BufferedSink;
import okurl.util.Assert;
import okurl.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class FormBody extends RequestBody {
    private static final MediaType CONTENT_TYPE =
            MediaType.parse("application/x-www-form-urlencoded");

    private List<FormParam> params;
    public FormBody(List<FormParam> params) {
        this.params = params;
    }

    @Override
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public long contentLength() throws IOException {
        return 1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        StringBuilder sb = new StringBuilder(256);
        for (int i = 0, size = params.size(); i < size; i++) {
            if (i > 0)
                sb.append('&');

            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
        }
        sink.write(sb.toString().getBytes(Util.UTF_8));
    }

    public static final class Builder {
        private List<FormParam> params;

        public Builder add(String name, String value) {
            addParam(name, value, false);
            return this;
        }

        public Builder addEncoded(String name, String value) {
            addParam(name, value, true);
            return this;
        }

        private void addParam(String name, String value, boolean encode) {
            Assert.notEmpty(name);
            if(this.params==null) {
                this.params = new ArrayList<>();
            }
            FormParam param;
            if(encode) {
                param = new FormParam(Util.encode(name), value==null ? "":Util.encode(value));
            } else {
                param = new FormParam(name, value);
            }
            this.params.add(param);
        }

        public FormBody build() {
            return new FormBody(params);
        }
    }

    static class FormParam {
        private String name;
        private String value;

        public FormParam(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
