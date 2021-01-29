package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers.*;
public class SoapTest {
    @Test
    public void postSoap() throws IOException {
        FileInputStream fis = new FileInputStream(".\\soap\\request.xml");
        RestAssured.baseURI = "http://currencyconverter.kowabunga.net";
        Response response = 
             given()
                .header("content-type","text/xml")
                .and()
                .body(IOUtils.toString(fis, "UTF-8"))
                .when()
                .post("/converter.asmx")
                .then()
                .statusCode(200)
                .and()
                .log()
                .all()
                .extract()
            .response();
        XmlPath xmlPath = new XmlPath(response.asString());
        String rate = xmlPath.getString("GetConversionRateResult");
        System.out.print("the rate is : " + rate);
    }
}    
