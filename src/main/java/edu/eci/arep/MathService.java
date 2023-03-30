package edu.eci.arep;

import static spark.Spark.*;

public class MathService {
    public static void main(String[] args) {
        port(getPort());
        System.out.println("Entró");
        get("espalindromo", (req, res) -> {
            res.type("application/json");
            return "{\"operation\":\"palindromo\",\"input\":" + req.queryParams("value") +
                    "\",\"output\":\""+esPalindromo(req.queryParams("value")) + "\"}";
        });


    }

    private static String esPalindromo(String word) {
        String reverse = "";
        char c;
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            reverse = c + reverse;
        }
        String result = "Si es palíndromo";
        if (!word.equals(reverse)) {
            result = "No es palíndromo";
        }
        return result;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
