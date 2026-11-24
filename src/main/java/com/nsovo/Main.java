package com.nsovo;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner  = new Scanner(System.in);
        String countryName = scanner.nextLine();

        //now we fix for unwanted characters
        String encodedCountryName = URLEncoder.encode(
                countryName,
                StandardCharsets.UTF_8
        ).replace("+","%20");


        //add the country to the url
        String url  =  "https://api.restcountries.com/countries/v5/names.common/"
                +encodedCountryName;

        HttpClient client  = HttpClient.newHttpClient();

        HttpRequest request  = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer rc_live_demo")

                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("The response statuscode is : " +response.statusCode() );
        System.out.println("The response body is : " +response.body());

        scanner.close();

    }
}
