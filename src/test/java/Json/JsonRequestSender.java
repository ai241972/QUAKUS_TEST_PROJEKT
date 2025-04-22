package Json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import  entity.Person;

public class JsonRequestSender {

    //Http Connection
    public static  HttpURLConnection connection(String targetUrl , String Method) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Method);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }

    //send new Person
    public static void sendPersonRequest(HttpURLConnection connection, Person person) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(person);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
         connection.disconnect();
    }

    //get a list from person
    public static void sendGetRequest( HttpURLConnection connection) throws Exception {
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println("Response Body:");
                System.out.println(response.toString());
            }
        } else {
            System.out.println("Error en el GET request.");
        }

        connection.disconnect();
    }


    public static  void deleteGetRequest(Long id ) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()

                .uri(new URI("http://localhost:8080/person/"+id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Manejar la respuesta
        System.out.println("Response code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }

    public static  void deleteGetRequest2(String targetURL , Long id) throws URISyntaxException, IOException, InterruptedException {
        URL obj = new URL(targetURL+"/"+id);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");  // Establecer el método DELETE
        con.setRequestProperty("Content-Type", "application/json");
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Leer la respuesta (opcional)
        if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 OK
            System.out.println("Request successful");
        } else {
            System.out.println("Request failed");
        }

        // Cerrar la conexión
        con.disconnect();
    }

    public static void updateRequest(Long id, Person person) throws IOException {
        if (person == null) {
            System.out.println("El objeto person es nulo.");
            return;
        }
          URL url = new URL("http://localhost:8080/person/update/" + id);


        // Abrir la conexión HTTP
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");


        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json"); // Asegúrate de que acepta JSON como respuesta
        con.setDoOutput(true);
        con.setDoInput(true);

        // Convertir el objeto Person a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(person);

        // Enviar el JSON como cuerpo de la solicitud
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Obtener y mostrar el código de respuesta
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Leer la respuesta del servidor
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("Response: " + response.toString());
        }
    }
}

