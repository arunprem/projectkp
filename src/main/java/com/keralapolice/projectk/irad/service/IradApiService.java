package com.keralapolice.projectk.irad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

@Service
public class IradApiService {

    public Object getAccidentDetails() throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            //System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            String resourceUrl
                    = "https://gisnic.tn.nic.in/irad/services/police/police.php";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", "test");
            map.add("password", "test@1234");
            map.add("accidentid", "202215281010033");
            map.add("data", "generalinfo");
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
            ResponseEntity<String> response
                    = restTemplate.postForEntity(resourceUrl, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            return node;
        }catch (Exception e){
            throw  new Exception("Cannot Connect to IRAD API please re try");
        }


    }
}
