package restAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Post_example {
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
    private void takeParams() throws IOException, ParseException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/input/post_input.csv"));
        String read = "";
        while ((read = bufferedReader.readLine()) != null) {
            System.out.println(splitTheParams(read));
            ArrayList<String> arrayList = splitTheParams(read);
            for (int j = 0; j < arrayList.size(); j++) {
                System.out.println(arrayList.get(j));
            }
            System.out.println("boo");
            postcall(arrayList.get(1), arrayList.get(2), arrayList.get(3), arrayList.get(4), arrayList.get(5), arrayList.get(6));
            System.out.println("boo");
        }
    }


    @Test
    public void postcall(String baseurl, String port, String routepath,  String params, String expectedresponse, String code) throws ParseException {
        RestAssured.baseURI = baseurl;
        RestAssured.port =  Integer.parseInt(port);
        String path = routepath;
        String parameters = params;
        String expectedResponse = expectedresponse;
        int status = Integer.parseInt(code);

        RequestSpecification request = RestAssured.given();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(parameters);


/*
        jsonObject.put("id","2");
        jsonObject.put("title","hapi");
        jsonObject.put("author", "roshni");

*/
        request.header("Content-Type", "application/json");
        request.body(jsonObject.toJSONString());
        Response response = request.post("/posts");
        System.out.println(response.getStatusCode());

    }
}
