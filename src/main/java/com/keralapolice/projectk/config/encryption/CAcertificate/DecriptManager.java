package com.keralapolice.projectk.config.encryption.CAcertificate;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.KeyTransRecipientInformation;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.PrivateKey;
import java.security.Security;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class DecriptManager {

    String key = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswggknAgEAAoICAQC9khmLBNDfVrgh\n" +
            "zs5RAc/TXFymhH+Aq9HxtDD46j/5aOg/d1N3115aaqnWboSrmMuHXX7xU9wWqzQn\n" +
            "iktPj2MMuQ14w6jl9It5BKksZCHNa9NiW14Mx69uWpv3oHNP/cAW8yD0LDtqL59w\n" +
            "aJSHIvME10I12cJod6Wrfi2dqL1X5gSB/Julvrtn7PZQRSjENMzArLQw/gwz3Dgb\n" +
            "+9SWhLXFof1EuI5Gew4UTPQTA27tAAe4qVJMXgZ08snkxLT0W/8W1c8QXWqQ7Ead\n" +
            "IAbDbm/nVQEfgGS86TYnq+OrRjq+N0aFHRuLYCZJymkxrqiGaejH8UzE83YwVy6U\n" +
            "lgkYk4dJBhQlfQk6GxvV3UPazy19Jfr6rsR2v//YrwlPo8iBlmJa6iYUqRTcNDK1\n" +
            "XZ7PHj79TPP/o4IicEcRo8t7Cal/gl+stKMfKgYI2J0yq41E7gaSiLgUnCok1e0W\n" +
            "5QLZ9YbC4nNLJVmVOve95dRAOuON8HBN3kNcuhpq0bM3x3Unzm5xzYLxcl/2eyzy\n" +
            "Zlblr06z7vWxL8tyv71IwhR8KFHZMOok2QTuNExcXCvhOd1UqVeNWTKFslud8l1J\n" +
            "8jjMc3Jh/INUyaey92EvEN0bdL9BPjTH3ENzx9s+47/OcZVFbXc22NFp8i/NyrB5\n" +
            "Kk1LXt5Hg/8yZqhUXckliiPF7RdFfQIDAQABAoICAAFDXl3nOWv+mAsAlk0r8StC\n" +
            "2DOj96MrUpCibatjvB5frf02rDSJ+PN8tBTeeG9r1pHTSgMFta1kT8A+nhcmLMDQ\n" +
            "8JICuMotofD2e+rvdKHD6OViO7FPILfWCS/gVGnQBv1pwhnOGBZBweEMD0SVDPwn\n" +
            "5xOhXODK3U3ZPfBPfUyJSkMt6V/f/JwS+pIbnaAYL+65VfUyxj3H8WIhJuDBR4ZF\n" +
            "XxqMiCZnf1NPNCjIeWIs4ElBTZOxbpO45Vn/k4IZhmPC6bzzaFIQrATcycdjkm1L\n" +
            "p2qr+CJueUP0d4cBbQX1jQfkvLqUIYFXjGJ5S1oUQPaPECAaCaPhmaKwC6+cSWgQ\n" +
            "aVTLbuF8ca+9oZsnCD/q6GMjBfXcGEpHrC+5D03tE1PP9g1FbAb4svnnSYt5CGxi\n" +
            "p4iZ1oVLPp2xH/E9zBoX5ZJ5/sNFM3aUQ2XfSpuiwyp3nxI7jZAxB1xwL2c5wNYc\n" +
            "VNmJ1puidOAcJvmZkPTrfdq4+61bL6qzLIcS+cAfAhoX8NQKH3f5kKDfjHcCELnj\n" +
            "X336rde8/y/QLh+qzMbEJIM5SnDUcsAiMb6zOD/xdKJoorYDHLnptD2dlmasCewf\n" +
            "npzjYk7yoegA/qrpNJEt+0v2WiMiQOGROwltKhPgFZlFUOzJwH3hOaAqBd9KuPVx\n" +
            "yzXU9ea/zLWtN+DFK0ZhAoIBAQDjvLrzceSroOpf19hhF2Tn/Iocob69tvSb7j9t\n" +
            "n4lEn0XkndwnqbVmuoEU8HT7/UbURjkVCZxI96BZHOpxfWkoE3SRlDDrmAmaYiat\n" +
            "lJx1NFqmnv5n+AdnxGHFIhixVroD733bFUq2QsPUIsz2gR859H817fOQg6MINYle\n" +
            "yu1ry70ePlOqp/Hs15gja2IttOEd2nIRVM4tbZcaRqzeQXJQKPecKf6pJEOKkdds\n" +
            "M1jER2alfMSt1pcapAEiImT+a4JBNuZuZ81xeaM3+mCGhv8iLmmkVAFRjNW4cG/P\n" +
            "6u2uWnkPUkecQQrWwanI4J5dGJOb9kemixnvKMsc7tSKw6jdAoIBAQDVGM8sNqjG\n" +
            "056GfKN6yeKXkRUAbiQhOFU+jx8fH3O7cfqMZDaWGgP7mGj72dslejJmvadEw2zg\n" +
            "z4GTorCpTMNodrCIPeDhqbFLXSZ+7gCI9AO6Y0WFkpB/95PXNtATmBH8gAtU7rA2\n" +
            "8ZnJ0ZSHa/SAt9clyV982ucFWKZ/rXXJ39qwMXbOlyk3clS2Jmz4tVDHugNwtcr8\n" +
            "wXOX4kDH7laj1aBe9xYEsXd8ZPJnfwkBa5ypr4YQjkxAfgvYnn3N4/92iT9oQo6X\n" +
            "PiPkB2s5qCARZJEWEbbtZUp2JsBrZtg5Hx/Rt9HmsKrQWHpoWZjcgV+udWOr1ysS\n" +
            "hlOuKH4In/UhAoIBAHZMJCSvReRtWzHcElck6tKi2S9USZuMvPV+xc3w8EIIJitC\n" +
            "YQjtXAB34vN4kJwRDcjYjXBDJL62esGvhozbY1Ng/81bWCoZOOvMDmFecANl5azl\n" +
            "rCtfqKEbXtojAwajkU7YCd1S6xNQDR9V0Hg/zHOzcJ0X9MxdxMNP5AKskLXNCHj8\n" +
            "UUO1p7Kqrym+7ynk6ocyYMxl7A+m/LW2freY8YN4NF8XZWSBJOCHL4rSBUs397cA\n" +
            "372ZWbaJ+Ni9Ww7Z07iChyJUo2BNmh5K3fcjisY7q76jsGenK6F6Lmqnkz1dW8M8\n" +
            "r1wYYUP7J3HkU/7x9EMwpDl7ykX0P8XQvfEy/7UCggEAYR7ehSsgPwYB0Q+LPpI2\n" +
            "iJeh98E7DDztdQary3Z9O6gV5dhinISc3hU3e9ltrKYFxJeZXZinzcolJ1FVrMOE\n" +
            "p964VE/HZ3H8kPmSavU0Hzb/X/qQm/ERlJEUu1iK+vMq92awQ3RJsbDpQ3cWd6X3\n" +
            "GJcxIlKmB3SJKAGgWL6I2YoB1J2mpT91lISyIRZgQfX5LB4WJU9lhU7xzP5cQgLZ\n" +
            "T/A0GDQKv/bWG0ayyAK46wHNs/SAaJlCHX6VDZQmiCjqrG6fDHiRHlM+7lWHRA7h\n" +
            "Fqw8B8/pDEdG6ZR9zDViRJ5v1RgvPTG1mCFLILX9GjqIji2IULBPPDEmgFCKKIom\n" +
            "oQKCAQBd1/ISpBWW1JP1E6EMBoNCSZHzhNCyEthx4XXbOpP7/py0VW6CT3q8Xo9S\n" +
            "ba5rZwP6EgbUlrK+1JWmBmsNMMSXO5NY5jwAVVSKxNo5iFMLyjwOEDJdvpjzN6/3\n" +
            "Mf7iWWDZwxu1dbScBpIv2o/X3otfGr0nQq/shvRYciy30UgcRZRLeWSQOQTyizC1\n" +
            "XeVEFxw29Ec3EQ/yb09UwZyS6i3BbhCAdOBFCfurAva89+lOzwIO9ziTpDowONQR\n" +
            "ppdMm5DRuTcqQ1wT0uXPW2Q/S67HND7MLYc/uTo+az7PSjaAfWxOahqv8lxxDPnR\n" +
            "7bKw5j3XL9MyfY6bcipU/NsF7XRI\n" +
            "-----END PRIVATE KEY-----";



    public  byte[] decryptData(byte[] encryptedData) throws Exception {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        CMSEnvelopedData envelopedData = new CMSEnvelopedData(encryptedData);
        Collection<RecipientInformation> recipients = envelopedData.getRecipientInfos().getRecipients();
        KeyTransRecipientInformation recipientInfo = (KeyTransRecipientInformation) recipients.iterator().next();
        JceKeyTransRecipient recipient = new JceKeyTransEnvelopedRecipient(getPrivateKey(key));
        return recipientInfo.getContent(recipient);
    }


    public static PrivateKey getPrivateKey(String key) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        InputStream res = new ByteArrayInputStream(key.getBytes());
        Reader fRd = new BufferedReader(new InputStreamReader(res));
        PEMParser pemParser = new PEMParser(fRd);
        Object object = pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        PrivateKeyInfo ukp = (PrivateKeyInfo) object;
        PrivateKey pk = converter.getPrivateKey(ukp);
        pemParser.close();
        return pk;
    }


}
