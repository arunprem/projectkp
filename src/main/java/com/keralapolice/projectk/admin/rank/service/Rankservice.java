package com.keralapolice.projectk.admin.rank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralapolice.projectk.admin.rank.vo.AuthVo;
import com.keralapolice.projectk.config.database.MySQLBaseDao;
import com.keralapolice.projectk.config.encryption.GeneratePublicPrivateRsaKey;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class Rankservice {

    @Autowired
    MySQLBaseDao rankDao;

    @Autowired
    GeneratePublicPrivateRsaKey generatePublicPrivateRsaKey;


    public Integer rankList() {
        int count = 0;
        count= rankDao.queryNameForInteger("get.count.rank");
        return count;
    }


    public Object testRestTemplate(AuthVo authVo) throws JsonProcessingException {

            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl
                    = "http://testcas-dev-keycloak.ns.keralapolice.gov.in/auth/realms/cctns_cas/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", authVo.getClient_id());
            map.add("client_secret", authVo.getClient_secret());
            map.add("grant_type", authVo.getGrant_type());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
            ResponseEntity<String> response
                    = restTemplate.postForEntity(fooResourceUrl, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.getBody());
            return node;


    }

}
