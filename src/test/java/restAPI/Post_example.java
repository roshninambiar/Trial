package restAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class Post_example {
    @Test
    public void postcall(){
        RestAssured.baseURI ="http://localhost:3000";
        RequestSpecification request = RestAssured.given();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","2");
        jsonObject.put("title","hapi");
        jsonObject.put("author", "roshni");

        request.header("Content-Type", "application/json");
        request.body(jsonObject.toJSONString());
        Response response = request.post("/posts");
        System.out.println(response.getStatusCode());

    }
}
