package restAPI;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONObject;
import org.junit.After;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class Post_Delete_Put_example{

    DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    LocalDateTime timenow = LocalDateTime.now();
    String date = datetimeformatter.format(timenow);

    StringBuilder stringBuilder = new StringBuilder();
    File file = null;
    FileWriter fileWriter = null;
    String url = "http://localhost:3000/posts/1";

    public Post_Delete_Put_example() throws IOException {
    }

    @Test
    public void test1(){
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("id", "3");
        json.put("title", "Selenium Webdriver");
        json.put("author", "Learn automation");

        request.body(json.toJSONString());

        Response response = request.post("http://localhost:3000/posts/");

        Assert.assertEquals(response.getStatusCode(), 201);

    }

    @DataProvider(name= "inputURL")
    public Object[][] getExcel() throws IOException {

        String inputFilePath = "src/test/input/jsonserver.xls";
        File file = new File(inputFilePath);
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        String url = sheet.getRow(0).getCell(0).getStringCellValue();
        //String urlData[][] = new String["h"]["n"];
        return new String[][]{
                {url}
        };
    }

    @Test(invocationCount = 5)//, dataProvider = "inputURL")
    public void getResponse(ITestContext testContext) throws IOException {

        int currentCount = testContext.getAllTestMethods()[0].getCurrentInvocationCount();

        Response response = expect().
                statusCode(200).
                body("id", equalTo(1),
                        "title", equalTo("json-server"),
                        "author", equalTo("typicode")).
                //given().
                  //  parameters("url", url).
                when().
                get(url);

        System.out.println("Response time: "+ response.getTimeIn(TimeUnit.MILLISECONDS));

        stringBuilder.append(response.getBody().asString() + ",");
        stringBuilder.append(response.getTimeIn(TimeUnit.MILLISECONDS) + ",");
        stringBuilder.append(response.statusCode() + "\n");
        file = new File("src/test/output/output_"+date+".csv");
        fileWriter = new FileWriter(file, true);
        fileWriter.write(stringBuilder.toString());
        fileWriter.close();
    }

    @Test
    public void getResponseFromEc2(){
        Response response = expect().
                statusCode(200).
                body("responseCode", equalTo(200)).
                when()
                .get("http://ec2-18-205-237-120.compute-1.amazonaws.com:8080/asyncapi/nav2");
        System.out.println("Response Time: "+response.getTimeIn(TimeUnit.MILLISECONDS));
    }

    /*
    @AfterTest
    public static void lastFileModified() throws IOException {
        String dir = "src/test/output/";
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        System.out.println("Last modified: "+ choice.getPath());

        File f = new File(choice.getPath());
        if(f.exists()) {
            FileWriter fileW = new FileWriter(f);
            StringBuilder sb = new StringBuilder();
            sb.append("Response, Response Time(millisec), Status code");
            fileW.write(sb.toString());
            fileW.close();
        }

    }*/

}

///home/roshni/IdeaProjects/ApiTesting/src