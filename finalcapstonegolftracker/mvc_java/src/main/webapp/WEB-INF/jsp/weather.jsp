<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
<script>

GET /forecastrss?location= detroit,mi HTTP/1.1
		Host: weather-ydn-yql.media.yahoo.com
		X-Yahoo-App-Id: ${idRequest}
		Authorization: OAuth
		oauth_consumer_key=${ckRequest},oauth_signature_method="HMAC-SHA1",oauth_timestamp="YOUR_TIMESTAMP",oauth_nonce="YOUR_NONCE",oauth_version="1.0",oauth_signature="YOUR_GENERATED_SIGNATURE"
		cache-control: no-cache

// APIWeatherController		
// ${idRequest} : line 33
// OAuth : 
		
		
</script>