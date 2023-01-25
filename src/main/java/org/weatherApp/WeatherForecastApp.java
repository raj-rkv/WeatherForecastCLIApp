package org.weatherApp;

import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class WeatherForecastApp {


    public static void callWeatherForecastApi() throws URISyntaxException , IOException {

        //Input the location to be forecasted
        System.out.println("Please Enter the location you want to check:");
        Scanner sc=new Scanner(System.in);
        String location=sc.nextLine();

        URIBuilder builder=new URIBuilder("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/forecast");

        builder.setParameter("aggregateHours","24");
        builder.setParameter("contentType","json");
        builder.setParameter("unitGroup","metric");
        builder.setParameter("locationMode","single");
        builder.setParameter("key","1PYNQ6AWUDJE9AFERDCHJHSXK");
        builder.setParameter("location",location);

        HttpGet getData = new HttpGet(builder.build());

        CloseableHttpClient httpClient= HttpClients.createDefault();

        CloseableHttpResponse httpResponse=httpClient.execute(getData);

        if(httpResponse.getStatusLine().getStatusCode()==200)
        {
            HttpEntity responseEntity=httpResponse.getEntity();

            String result= EntityUtils.toString(responseEntity);
//            System.out.println(result);

            //JSON response formatting for attributing 1.mint 2.maxt 3.datetimestr 4.humidity 5.visibility

            JSONObject responseObject=new JSONObject(result);
            JSONObject locationObject=responseObject.getJSONObject("location");
            JSONArray valueObject=locationObject.getJSONArray("values");

            System.out.println("datetimestr \t\t\t\t\t mint \t\t maxt \t\t humidity \t visibility");
            for(int i=0;i<valueObject.length();i++)
            {
                JSONObject value=valueObject.getJSONObject(i);
                String dateTime=value.getString("datetimeStr");
                double mintemp=value.getDouble("mint");
                double maxtemp=value.getDouble("maxt");
                double humidity=value.getDouble("humidity");
                double visibility=value.getDouble("visibility");

                System.out.printf("%s \t\t %f \t %f \t %f \t %f\n",dateTime,mintemp,maxtemp,humidity,visibility);
            }
        }
        else
        {
            System.out.println("Something went Wrong!");
            return;
        }

        httpClient.close();


    }

    public static void main(String[] args) {


        try {
            callWeatherForecastApi();
        }
        catch (URISyntaxException | IOException e)
        {
            System.out.println(e);
        }
    }
}
