package okurl;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
public interface Call {

    Request request();

    Response execute() throws IOException;
}
