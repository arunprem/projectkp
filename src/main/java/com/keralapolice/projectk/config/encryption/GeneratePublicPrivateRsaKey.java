package com.keralapolice.projectk.config.encryption;

import com.keralapolice.projectk.config.database.MySQLBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class GeneratePublicPrivateRsaKey {

    private PrivateKey privateKey;
    private PublicKey publicKey;



    @Autowired
    MySQLBaseDao mySQLBaseDao;


    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlG5/fcwrAYpSM2CJ57fB13J1AS/49LQ6TzzQ2TIeBKfTj+1djAzQKaqOH2NvbLBfn2EUIFsnA/jOVRx4tmmSGM6QzdjyttKwsNEr0DOsGePCzOexLS3+LupisJoH8IbrGD40NGkt8W+2y8pLfK1kdsfugQqwOgbLtO+xSZHnkqOZJf0tU9691D2sGXQdNzEjfxD/QKKjFwxOGa8KVTXSHSQ/GnbGmyxth+ceLWB7mCBUN6Ci1WsL51gXqckv0FDehyae9pzt1gpnJF+CWPAv28/CahA5msKTHYmpKXZfizMQhReEAJsPxdv01g6MMiST/C52szp0HkQ2RHwr9n1IAQIDAQAB";
    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUbn99zCsBilIzYInnt8HXcnUBL/j0tDpPPNDZMh4Ep9OP7V2MDNApqo4fY29ssF+fYRQgWycD+M5VHHi2aZIYzpDN2PK20rCw0SvQM6wZ48LM57EtLf4u6mKwmgfwhusYPjQ0aS3xb7bLykt8rWR2x+6BCrA6Bsu077FJkeeSo5kl/S1T3r3UPawZdB03MSN/EP9AoqMXDE4ZrwpVNdIdJD8adsabLG2H5x4tYHuYIFQ3oKLVawvnWBepyS/QUN6HJp72nO3WCmckX4JY8C/bz8JqEDmawpMdiakpdl+LMxCFF4QAmw/F2/TWDowyJJP8LnazOnQeRDZEfCv2fUgBAgMBAAECggEALhAEfJOJnRbB37PL2B8MhsG8UbwDdF0h40kqPtzwt90lKHsvFrE7QFLvfAotn/s0EY/c4RWoi/EAhyUoyOMGNbCU+2yiFd5YeubOw/PlKrulzlTwhObLBOAz9D44zInyFznmlYU2fsB+Z8vFlVFx5fKxr1YTzk4hlWr1B7HBvZ2QsRQfYNnkQCa6mhvu6r7SB3YgP5CQe1SFa16b+7ShIDD+lnvjcu7uIpizHbA68gQcVCBlJ5pZsibkRF+DVSf8JdHUR/FKoDosD9xQ8bKPLHg8CXCesWd5bLkB1/n342bxNetv0sXl8ccjPizi0gzC+Dqk4m4ohCSVYju554geaQKBgQDnx9K/vMRLPbL5zalyXybfTXxT7x/LDBYLCpCRbvDorjLn5K6hcJB2i8/7dJXQI/zxEyXt90H/GnVSrGDNmlZkntse8CGZ6sQUaTPZhcslDLQqpG1otcp7k3ayO536P0YueC0zQDEfww94VdM2z3SY/h64sF1PcpJA3gCJUbzqcwKBgQCj8RL8HcVrVlWYB0j0hXIaBdNOoS2nFGPJd6S2C2TZD0C0ylsj5boV1Fq7oI6LgQobkXncEIp1i5nO8W1sGJVIhHP0TzdmU+Pcowxu0ov4UWS+ZNTi8bsCEB+FAHoRf5klDeOSZ3nDZ9xosnqCVeD7gjUjOXq/yt9hIxoVC8piuwKBgFVtbfzje0nsHLo7lhekgHRPX+KjwQkalElPhIdF558rDXIw9Dhmf6hh0a9xggIsMX89On6h4n4+5z0ocal2GB+P+V1GzmunfsuzFrw2tvAZ5ZHN9tFgJ5gyes4ocQqaJajc/HPmVfTJ9Tq6D1YlUmlNSE90GYA9zy//Yx05tjKHAoGAYQvVDC9nbRnHQ16UnkLcf3ERXAtnZe9Y/qr3ctgd06wGxr6+4uoblM6HA2iQ2R4C6LzeIRodjGWeH/Wkq4eF306hAW9fQRDJ4xMVygiS2OT9uQMhTlKAPEFlFytoFzGsa1XkcAcW+khiHgwBH2RM8TVr1TUyZ5RlVTAdQXYgUP8CgYEApNkwFTgfvVQDS4AEN+MmTSwEdcaiA8shT3o+Do8r7uAjIK6o1IYsYa/CcEsqWf/8QM+zepALl36Mz8e/62lhv3BaRIOYTT0ZBT/99hG5inNGKwgyv7u4w3+KXrLnga7TDphlJw5N0bw8W+00hOqIUEYtTEAEUpV3vqqhZuE9n4o=";

    public void generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA","BC");
        generator.initialize(4096);
        KeyPair pair = generator.generateKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();

    }

    public void initKeyFromString() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY));
        PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA","BC");
        publicKey = keyFactory.generatePublic(keySpecPublic);
        privateKey = keyFactory.generatePrivate(keySpecPrivate);


    }

    public void initKeyFromUser(String clientId,String clientSecret) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        String publicKeyString=null;
        String privateKeyString=null;
       List<Map<String,Object>> keys = mySQLBaseDao.queryNameForList("get.key.of.user",new Object[]{clientId,clientSecret});

        publicKeyString = keys.get(0).get("public_key").toString();
        privateKeyString = keys.get(0).get("private_key").toString();
        X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(publicKeyString));
        PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA","BC");
        publicKey = keyFactory.generatePublic(keySpecPublic);
        privateKey = keyFactory.generatePrivate(keySpecPrivate);
    }



    public byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }


    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
