package restAPI;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class Post_Delete_Put_example {

    @Test
    public void test1(){
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("id", "2");
        json.put("title", "Selenium Webdriver");
        json.put("author", "Learn automation");

        request.body(json.toJSONString());

        Response response = request.post("http://localhost:3000/posts/");

        Assert.assertEquals(response.getStatusCode(), 201);

    }
}
