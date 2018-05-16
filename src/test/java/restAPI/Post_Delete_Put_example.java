package restAPI;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class Post_Delete_Put_example{

    PrintWriter printWriter = null;
    FileWriter fileWriter = null;
    StringBuilder stringBuilder = new StringBuilder();
    String outputfilepath = "/home/roshni/IdeaProjects/ApiTesting/src/test/output/";

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

    @Test(invocationCount = 100, threadPoolSize = 5)
    public void getResponse() throws FileNotFoundException {

        Response response = expect().
                statusCode(200).
                body("id", equalTo(1),
                        "title", equalTo("json-server"),
                        "author", equalTo("typicode")).
                when().
                get("http://localhost:3000/posts/1");

        System.out.println("Response time: "+ response.getTimeIn(TimeUnit.MILLISECONDS));

        response.statusCode();

        printWriter = new PrintWriter(new File(outputfilepath + "test3.csv"));
        stringBuilder.append(response.toString() + ",");
        stringBuilder.append(response.getTimeIn(TimeUnit.MILLISECONDS) + "\n");
        printWriter.write(stringBuilder.toString());
        printWriter.close();

    }

}
