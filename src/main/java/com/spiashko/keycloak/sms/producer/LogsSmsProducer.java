package com.spiashko.keycloak.sms.producer;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.net.URLEncoder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Singleton
@Slf4j
@ToString(callSuper = true)
public class LogsSmsProducer implements SmsProducer {

    @Override
    public void produce(String smsMessage, String destinationNumber) {


        log.info("produce sms with message=" + smsMessage +
                " for destination=" + destinationNumber);

        try {
            HttpClient client = HttpClient.newHttpClient();
            destinationNumber = URLEncoder.encode(destinationNumber, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");

            smsMessage = URLEncoder.encode(smsMessage, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");

            HttpRequest request = HttpRequest.newBuilder(
                URI.create("http://gate.smsaero.ru/send/?to="+destinationNumber+"&text="+smsMessage+"&from=SMS%20Aero&user=USERNAME&password=MD5PASSWORD"))
            .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(InterruptedException | IOException e) {
            log.info("SMS ERROR");
        }
    }
}
