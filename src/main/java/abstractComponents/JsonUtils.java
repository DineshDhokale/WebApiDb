package abstractComponents;

import org.json.JSONObject;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

public class JsonUtils {
    public static String getPassword() throws Exception {
        InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream("resources/testData/UserDetails.json");
        if (is == null) {
            throw new FileNotFoundException("Cannot find file: testData/UserDetails.json");
        }
        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(content);
        return jsonObject.getString("password");
    }
}