package org.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexander Bravo
 */
public class test {
    static int getTopicCount(String topic) {

        HttpURLConnection connection = null;
        try{
            URL url = new URL(
                String.format(
                    "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page=%s"
                    , topic));
            //No autocloseable
            connection = (HttpURLConnection) url.openConnection();
            return toJsonObject(connection, topic);
        } catch( IOException e){
            e.printStackTrace();
        } finally{
            if(connection != null) connection.disconnect();
        }
        return 0;
    }

    public static int toJsonObject(HttpURLConnection connection, String topic){
        try(BufferedReader in =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedReader br = new BufferedReader(in)){
            List<String> list = br.lines()
                .filter(line -> line.length() > 0)
                .map(w -> w.split("\\s+")).flatMap(Arrays::stream)
                .filter(line -> line.contains(topic))
                .collect(Collectors.toList());
            list.forEach(System.out::println);

            return list.size();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

  public static void main(String[] args) {
    //
    System.out.println(getTopicCount("Pizza"));
  }
}
