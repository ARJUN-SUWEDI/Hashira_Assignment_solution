import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PolynomialConstantFinder {
    public static void main(String[] args) {
        try {
            // Step 1: Read the JSON file
            String jsonData = new String(Files.readAllBytes(Paths.get("data.json")));
            JSONObject jsonObject = new JSONObject(jsonData);

            // Extract keys
            JSONObject keys = jsonObject.getJSONObject("keys");
            int n = keys.getInt("n");
            int k = keys.getInt("k");

            // Step 2: Decode y-values
            List<Double> decodedValues = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                if (key.equals("keys")) continue;

                JSONObject entry = jsonObject.getJSONObject(key);
                int base = Integer.parseInt(entry.getString("base"));
                String value = entry.getString("value");

                // Decode the value from its base
                double decoded = Integer.parseInt(value, base);
                decodedValues.add(decoded);
            }

            // Sort and take first k values (as required)
            Collections.sort(decodedValues);
            List<Double> subset = decodedValues.subList(0, Math.min(k, decodedValues.size()));

            // Step 3: Use subset to estimate constant term (C)
            // Simplified assumption: C = product of (-roots) for polynomial with leading coeff = 1
            double C = 1;
            for (double val : subset) {
                C *= -val;
            }

            System.out.println("Constant term C = " + C);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}