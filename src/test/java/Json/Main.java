package Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import entity.Person;

import java.io.IOException;
import java.net.HttpURLConnection;

public class Main {
    public static void main(String[] args) {
        try {
            String targetUrl = "http://localhost:8080/person";
           //ADD Person
            /**
            Person person = new Person("Lola", "Luns", 23);
            HttpURLConnection htconnect  = JsonRequestSender.connection(targetUrl , "POST");
            JsonRequestSender.sendPersonRequest(htconnect, person);
            **/

            //List a Persong
            /**
            HttpURLConnection htconnect  = JsonRequestSender.connection(targetUrl+"/list" , "GET");
            JsonRequestSender.sendGetRequest(htconnect);
          **/

            //delete
            //  JsonRequestSender.deleteGetRequest(Long.valueOf("55"));
            //  JsonRequestSender.deleteGetRequest2(targetUrl , Long.valueOf("52"));


           // UPDATE
            Person person = new Person( "Marki", "yank", 26);
        try {
                JsonRequestSender.updateRequest(51L, person);
            } catch (IOException e) {
                e.printStackTrace();
            }



          } catch (Exception e) {
            e.printStackTrace();
        }

    }
}