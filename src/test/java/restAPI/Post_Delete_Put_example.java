package restAPI;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class Post_Delete_Put_example{

    StringBuilder stringBuilder = new StringBuilder();
    File file = null;
    FileWriter fileWriter = null;

    public Post_Delete_Put_example() throws IOException {
    }

    @Test
    public void test1(){
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("id", "2");
        json.put("title", "Selenium Webdriver");
        json.put("author", "Learn automation");

        request.body(json.toJSONString());

        Response response = request.post("http://ec2-18-205-237-120.compute-1.amazonaws.com:8080/asyncapi/nav2");

        Assert.assertEquals(response.getStatusCode(), 201);

    }

    @Test(invocationCount = 100)
    public void getResponse(ITestContext testContext) throws IOException {

        //int currentCount = testContext.getAllTestMethods()[0].getCurrentInvocationCount();

        Response response = expect().
                statusCode(200).
                body("id", equalTo(1),
                        "title", equalTo("json-server"),
                        "author", equalTo("typicode")).
                when().
                get("http://localhost:3000/posts/1");

        System.out.println("Response time: "+ response.getTimeIn(TimeUnit.MILLISECONDS));

        //stringBuilder.append("Response"+",");
        //stringBuilder.append("Response time" + "\n");
        //stringBuilder.append(currentCount + ",");
        stringBuilder.append(response.toString() + ",");
        stringBuilder.append(response.getTimeIn(TimeUnit.MILLISECONDS) + ",");
        stringBuilder.append(response.statusCode() + "\n");
        file = new File("/home/roshni/IdeaProjects/ApiTesting/src/test/output/test1.csv");
        fileWriter = new FileWriter(file);
        fileWriter.write(stringBuilder.toString());
        fileWriter.close();


    }

}
