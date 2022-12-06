1. Build and start the auth-server:

mvn clean install

mvn spring-boot:run

2. Get authorization code

In the browser: http://localhost:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients+deleteIngredients

Login (tacochef/password) and select the desired scope

3. Get the access token, using the authorization code above

Copy the "code=$code" portion of the resulting redirected URL (replace $code in the URL below with the code from this URL)

In the CLI: curl localhost:9000/oauth2/token -H"Content-type: application/x-www-form-urlencoded" -d"grant_type=authorization_code" -d"redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client" -d"code=$code" -u taco-admin-client:secret

The command above will return the access token. Copy the access token. In the CLI/postman etc:

4. Use the access_token to make requests

curl localhost:8080/data-api/ingredients -H "Content-type: application/json" -d "$sample_json_body" -H "Authorization: Bearer $access_token"

Notes:

1: The $access_token has a TTL of 5 minutes. Use the refresh token to request a new $access_token.

2: $sample_json_body: {\"id\":\"FISH\", \"name\":\"Tasty Fish\", \"type\":1}

