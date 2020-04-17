//package com.techelevator.controller;
//
//import java.beans.Encoder;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.techelevator.model.User.UserDAO;
//
//public class APIWeatherController {
//
//@Autowired
//private UserDAO userDAO;
//
//@RequestMapping (path = "/users/weather", method=RequestMethod.GET)
//public String testAPI(HttpServletRequest request, ModelMap map) {
//
//final String appId = "kbHpWf6e";
//final String consumerKey = "dj0yJmk9ZXB2T05RQzl5bjlNJmQ9WVdrOWEySkljRmRtTm1VbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTcx";
//final String consumerSecret = "e11ba75081b2652d3d8b7dd28803ce83868f0cf8";
//final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";
//	
//map.addAttribute("idRequest", appId);
//map.addAttribute("ckRequest", consumerKey);
//map.addAttribute("csRequest", consumerSecret);
//
//long timestamp = new Date().getTime() / 1000;
//byte[] nonce = new byte[32];
//Random rand = new Random();
//rand.nextBytes(nonce);
//String oauthNonce = new String(nonce).replaceAll("\\W", "");
//
//List<String> parameters = new ArrayList<>();
//parameters.add("oauth_consumer_key=" + consumerKey);
//parameters.add("oauth_nonce=" + oauthNonce);
//parameters.add("oauth_signature_method=HMAC-SHA1");
//parameters.add("oauth_timestamp=" + timestamp);
//parameters.add("oauth_version=1.0");
//// Make sure value is encoded
//parameters.add("location=" + URLEncoder.encode("detroit,mi", "UTF-8"));
//parameters.add("format=json");
//Collections.sort(parameters);
//
//StringBuffer parametersList = new StringBuffer();
//for (int i = 0; i < parameters.size(); i++) {
//  parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
//}
//
//String signatureString = "GET&" +
//  URLEncoder.encode(url, "UTF-8") + "&" +
//  URLEncoder.encode(parametersList.toString(), "UTF-8");
//
//String signature = null;
//try {
//  SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
//  Mac mac = Mac.getInstance("HmacSHA1");
//  mac.init(signingKey);
//  byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
//  Encoder encoder = Base64.getEncoder();
//  signature = encoder.encodeToString(rawHMAC);
//} catch (Exception e) {
//  System.err.println("Unable to append signature");
//  System.exit(0);
//}
//
//String authorizationLine = "OAuth " +
//  "oauth_consumer_key=\"" + consumerKey + "\", " +
//  "oauth_nonce=\"" + oauthNonce + "\", " +
//  "oauth_timestamp=\"" + timestamp + "\", " +
//  "oauth_signature_method=\"HMAC-SHA1\", " +
//  "oauth_signature=\"" + signature + "\", " +
//  "oauth_version=\"1.0\"";
//
//HttpClient client = HttpClient.newHttpClient();
//HttpRequest request = HttpRequest.newBuilder()
//  .uri(URI.create(url + "?location=detroit,mi&format=json"))
//  .header("Authorization", authorizationLine)
//  .header("X-Yahoo-App-Id", appId)
//  .header("Content-Type", "application/json")
//  .build();
//
//HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//System.out.println(response.body());
//}
//}
//return "map";
//}
//
//
//}