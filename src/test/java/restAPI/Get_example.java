package restAPI;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidatorSettings;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.array;
import static org.hamcrest.Matchers.lessThan;

public class Get_example {

    DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    LocalDateTime timenow = LocalDateTime.now();
    String date = datetimeformatter.format(timenow);


    public static ArrayList<String> splitTheParams(String data) {
        ArrayList<String> result = new ArrayList<String>();

        if (data != null) {
            String[] splitData = data.split("\\s*,\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    result.add(splitData[i].trim());
                }
            }
        }
        return result;
    }

    @Test
    private void takeParams() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/input/file.csv"));
        String read = "";
        while((read = bufferedReader.readLine())!= null){
            System.out.println(splitTheParams(read));
            ArrayList<String> arrayList = splitTheParams(read);
            for(int j=0; j<arrayList.size(); j++) {
                System.out.println(arrayList.get(j));
            }
            getCall(arrayList.get(1), arrayList.get(2), arrayList.get(3), arrayList.get(4), arrayList.get(5), arrayList.get(6));
            whenValidateResponseTime_thenSuccess(arrayList.get(1), arrayList.get(2), arrayList.get(3));
            whenLogRequest_thenOK(arrayList.get(1), arrayList.get(2), arrayList.get(3));
        }
    }


    @Test
    public void getCall(String baseurl, String port, String routepath,  String params, String response, String code) throws IOException {
        System.out.println("GET CALL");
        RestAssured.baseURI = baseurl;
        RestAssured.port =  Integer.parseInt(port);
        String path = routepath;
        String parameters = params;
        String expectedResponse = response;
        int status = Integer.parseInt(code);

        Response finalresponse = expect()
                .statusCode(status)
                .when()
                    .get(path);
             // .then()
             //     .body(matchesJsonSchemaInClasspath(expectedResponse));

   /*     JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
                .freeze();
*/
        System.out.println("YELLOW");
        String s = get(path).then().assertThat()
                .body(matchesJsonSchemaInClasspath("posts-1.json")).toString();
        System.out.println("YELLOW");

        System.out.println(finalresponse.getBody().asString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(finalresponse.getBody().asString());

        JsonPath jsonPath = finalresponse.jsonPath();
        List<String> list = jsonPath.get("batters.batter.id");
        //System.out.println(list.toString());
    }

    @Test
    public void whenValidateResponseTime_thenSuccess(String baseurl, String port, String routepath) {
        System.out.println("RESPONSE TIME");
        RestAssured.baseURI = baseurl;
        RestAssured.port =  Integer.parseInt(port);
        String path = routepath;
        ValidatableResponse validatableResponse = when().get(path).then().time(lessThan(5000L));

    }

    @Test
    public void whenLogRequest_thenOK(String baseurl, String port, String routepath) {
        System.out.println("LOGGING");
        RestAssured.baseURI = baseurl;
        RestAssured.port =  Integer.parseInt(port);
        String path = routepath;
        given().log().all()
                .when().get(path)
                .then().statusCode(200);
    }

}

/*
when()
    .get("/person/12345")
.then()
    .body("$", hasKey("surname"))
    .body("$", not(hasKey("age")));
 */