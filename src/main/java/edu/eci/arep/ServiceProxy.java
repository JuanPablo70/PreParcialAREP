package edu.eci.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static spark.Spark.*;

public class ServiceProxy {
    private static final String USER_AGENT = "Mozilla/5.0";

    private static final List<String> services = new ArrayList<>(Arrays.asList("http://ec2-3-95-210-132.compute-1.amazonaws.com:5000/espalindromo?value=", "http://ec2-3-84-9-55.compute-1.amazonaws.com:5000/espalindromo?value="));

    public static void main(String[] args) {
        port(getPort());
        staticFileLocation("/");
        get("espalindromo", (req, res) -> connection(req.queryParams("value")));

    }

    private static String connection(String word) throws IOException {
        System.out.println(word);
        Random random = new Random();
        int number = random.nextInt(2);
        URL obj = new URL(services.get(number) + word);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        System.out.println(con);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        StringBuffer response = null;
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return response.toString();
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}