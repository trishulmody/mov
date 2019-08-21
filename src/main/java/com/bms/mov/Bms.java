package com.bms.mov;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Bms
{
    public static void book() throws IOException, InterruptedException, JSONException
    {
        Document doc = Jsoup.connect("https://in.bookmyshow.com/buytickets/inox-inorbit-mall-malad-w/cinema-mumbai-FMMA-MT/20190425").get();
        Elements anchor = doc.select("a[href*=/buytickets/]");
        for (Element t : anchor)
        {
            System.out.println(t.text());
            System.out.println("https://in.bookmyshow.com"+t.attr("href"));
            if (t.text().toLowerCase().contains("endgame")){
                //TODO: Send flock message
                JSONObject json = new JSONObject();
                json.put("text", "Whatever it takes | "+t.text()+" | https://in.bookmyshow.com"+t.attr("href"));

                CloseableHttpClient httpClient = HttpClientBuilder.create().build();

                sendFlockMsg(json, httpClient);
                return;
            }

        }
//        JSONObject json = new JSONObject();
//        json.put("text", "STILL DEAD");
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        sendFlockMsg(json, httpClient);
    }

    private static void sendFlockMsg(JSONObject json, CloseableHttpClient httpClient) throws IOException
    {
        try {
            HttpPost request = new HttpPost();
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
            // handle exception here
        } finally {
            httpClient.close();
        }
    }
}