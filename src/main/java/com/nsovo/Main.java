
package com.nsovo;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter country name: ");
        String countryName = scanner.nextLine();

        String encodedCountryName = URLEncoder.encode(
                countryName,
                StandardCharsets.UTF_8
        ).replace("+", "%20");

        String url = "https://countries.dev/name/" + encodedCountryName;

        HttpClient client = HttpClient.newHttpClient();
 
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("Status: " + response.statusCode());
        System.out.println("Content-Type: " +
                response.headers().firstValue("Content-Type").orElse("Unknown"));

        if (response.statusCode() == 200) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode results = mapper.readTree(response.body());

            if (results.isArray() && results.size() > 0) {

                JsonNode country = results.get(0);

                System.out.println("\nCOUNTRY DETAILS");
                System.out.println("  ");
                System.out.println("Name: " + country.path("name").asText());
                System.out.println("Capital: " + country.path("capital").asText());
                System.out.println("Region: " + country.path("region").asText());
                System.out.println("Subregion: " + country.path("subregion").asText());
                System.out.println("Population: " + country.path("population").asText());
                System.out.println("Area: " + country.path("area").asText());
                System.out.println("Country code: " + country.path("alpha3Code").asText());
                System.out.println("Flag: " + country.path("flag").asText());

            } else {
                System.out.println("No country results found.");
            }

        } else {
            System.out.println("Request failed.");
            System.out.println(response.body());
        }

        scanner.close();
    }
}
