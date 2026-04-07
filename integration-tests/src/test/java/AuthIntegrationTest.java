import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.notNullValue;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:8083";
    }

    // HOW TO STRUCTURE A TEST

    // 1. Arrange: In this you do any setup that this test needs in order
    // to work a 100% of the time.

    // 2. Act: This is the code we write that actually triggers the thing
    // that we are testing.

    // 3. Assert: In this we check that for example, that response is a valid
    // token and also has an OK status

    @Test
    public void shouldReturnOKWithValidToken(){

        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();

        System.out.println("Generated Token: " + response.jsonPath().getString("token"));

    }

}
