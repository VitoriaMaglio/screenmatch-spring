package com.estudando.spring.Screenmatch.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaTranslation {
//Consumindo API para tradução das sinopses das series
    public static String obterTraducao(String texto) {
        try {
            String encodedText = URLEncoder.encode(texto, "UTF-8");
            String langpair = URLEncoder.encode("en|pt", "UTF-8"); // <-- codifica o "|"

            String url = String.format(
                    "https://api.mymemory.translated.net/get?q=%s&langpair=%s",
                    encodedText, langpair
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Extrai a tradução do JSON retornado
            int start = response.body().indexOf("\"translatedText\":\"") + 18;
            int end = response.body().indexOf("\"", start);
            if (start > 17 && end > start) {
                return response.body().substring(start, end);
            }

            return texto; // se não achar tradução, retorna original
        } catch (Exception e) {
            e.printStackTrace();
            return texto;
        }
    }

 }


