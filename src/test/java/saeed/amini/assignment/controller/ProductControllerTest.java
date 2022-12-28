package saeed.amini.assignment.controller;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import javax.net.ssl.SSLContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.core.io.Resource;
import saeed.amini.assignment.dto.JwtRequest;
import saeed.amini.assignment.dto.JwtResponse;
import saeed.amini.assignment.dto.ProductDto;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    private String baseAddress = "https://localhost:";

    private String baseApiPath = "/domain/springresttest";

    private String token;

    private  String address;



    @BeforeAll()
    void initAddress(){
        address = baseAddress+port+baseApiPath;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Test
    @Order(0)
    void jwtAuthentication(){
        Map<String, Object> body = new HashMap<>();
        body.put("username", "admin");
        body.put("password", "123");
        String api = baseAddress+port+"/authenticate";
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body);
        ResponseEntity<JwtResponse> response = restTemplate.exchange(api, HttpMethod.POST, httpEntity, JwtResponse.class);
        assertEquals(response.getStatusCodeValue() ,200);
        assertNotNull(response.getBody().getJwttoken());
        token ="Bearer "+ response.getBody().getJwttoken();
    }


    @Test
    @Order(1)
    void addProduct() {
        Map<String, Object> body = new HashMap<>();
        body.put("prid", 1);
        body.put("prdname", "Oracle");
        String api = address+"/addProduct";
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, authorizationHeader());
        ResponseEntity<Void> response = restTemplate.exchange(api, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(response.getStatusCodeValue() ,201);
    }

    @Test
    @Order(2)
    void getProducts() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        String api = address+"/prids";
        ResponseEntity<ProductDto[]> entity = restTemplate.exchange(
                api, HttpMethod.GET, new HttpEntity<>(authorizationHeader()),
                ProductDto[].class);
        ProductDto[] products = entity.getBody();
        assertEquals(products.length ,1);

    }

    @Test
    @Order(3)
    void getProduct() {
        String api = address+"/prid/1";
        ResponseEntity<ProductDto> entity = restTemplate.exchange(
                api, HttpMethod.GET, new HttpEntity<>(authorizationHeader()),
                ProductDto.class);
        ProductDto products = entity.getBody();
        assertEquals(entity.getStatusCodeValue() ,200);
        assertEquals(products.getPrdname() , "Oracle");
    }



    @Test
    @Order(4)
    void editProduct() {
        Map<String, Object> body = new HashMap<>();
        body.put("prid", 1);
        body.put("prdname", "linux");
        String api = address+"/editProduct";
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, authorizationHeader());
        ResponseEntity<Void> response = restTemplate.exchange(api, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(response.getStatusCodeValue() ,201);

    }

    @Test
    @Order(5)
    void deleteProduct() {
        String api = address+"/deleteProduct/1";
        ResponseEntity<ProductDto> entity = restTemplate.exchange(
                api, HttpMethod.DELETE, new HttpEntity<>(authorizationHeader()),
                ProductDto.class);
        assertEquals(entity.getStatusCodeValue() ,200);
    }

    private MultiValueMap authorizationHeader(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", token);
        return headers;
    }
}