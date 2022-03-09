package com.keralapolice.projectk.config.encryption.AesEncryption;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.security.crypto.codec.Utf8;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;

public class DataSecurityUtil {

    private static final String PRIVATEKEY_STRING="-----BEGIN PRIVATE KEY-----\n" +
            "MIIJRAIBADANBgkqhkiG9w0BAQEFAASCCS4wggkqAgEAAoICAQC4eCO9Ry4r0YtZ\n" +
            "9uFFSoJT82UnQNz+CN6F2Oc64E6jfWSnHVsSf+wpnKstnnr1SlbIKShEX1bpwgSP\n" +
            "Hvv/x+DdmGajOzcWweTmxRE9MybOyXGnTNwACTjxeBeFfS1LqWegFmICrVPm8Ffx\n" +
            "irMCpZY3fciH0umlUoU57+T+l/8np6KCw5iNXSiiyvJixZRzwnOrh/PCSX7MzKPx\n" +
            "XeW78UP+nGeZ5ggI+Gvtd8FDlQGU0cMSILVfZ/cEwKlo0tJ1yBOCPT+CFSYN3dMQ\n" +
            "drRmCS3s0hEiHTkNsPrxxaqLlxy/d1e4+Ab2QCgph/xj4+QOXa1t0bjIW/kmYlq9\n" +
            "PPSoyUpL9K1ryRksn4XE55jV8hrXtKcoMsrIEf3pXEUIo9C6e3WiAT0G88GTwQ6v\n" +
            "joE4AcJXlHvXnVMMf5/sgOVOAhuRyaMudu7Q/rsSHqrgKfYcq9mtvzWeLdo8La+l\n" +
            "EcwRQtmE8WUdpPJFW2931jTGwZOQbQXyLJTnkYbAUFv8AvlH+cFOkGGXxW1iigik\n" +
            "+SJkwVuaytDvjEnUWk/7lHcx+uDZXdsD/P25WhYm1GgYSmsaywYihN138vQzNCSa\n" +
            "E629N28W0vuZ6ADUJxdMQdRQ0C3waS0TvOkY+9xumorg95E7o076UBpN0MnXeiAU\n" +
            "rLeSOKfxfkUbpJdEQIQeH2RjuwwWaQIDAQABAoICAADfBGAw9V1cnHlcypyOqQXn\n" +
            "eNtSGZkThqWALDthcs2f2nlVkKgKNZ3OsRpCjNcatkU0uI12G813zpi0b0E6euvg\n" +
            "+98aePyhBsk2NgohczX3ypMdME7JvFybkhTdzfh1ANnHMUhM9D/7c5be7lB9YrO5\n" +
            "YtiNXPAcbyeiY+T1580SpBhU/yPvxDDcn39q6nQPf4dYWYTZ3wDtmHqb1GlqTsVl\n" +
            "jLGz3ebY8X2KGr4Mdpjy0qaDxAeC0K5TkJk/oaHPw5PSYTnmiqr3GLeTr+9pbK+Z\n" +
            "IF6WX74aT10T72XAXXDkCeSEO1gvXmEv1LfODDYrKBYELwi3MnqFbWNGZetkI/cs\n" +
            "yatapYlID/YgwewKXr4S3+kBtLyeEh57klUp6hug9fIAbFDg7VZWSDR0S765Xf8t\n" +
            "rtdmPZQjFY95F+XN7zyGiINqV0Nzvt7qoG4L5Dff82Dggiew+1t+fgU3vicMRrWO\n" +
            "FQ4VcidirA0qbhNd1IxWLIzwY3ag9Y3lLcsT9KX3G6thFGiPm34uBzykBzXiCRbS\n" +
            "eJ/bjKAZfkrnH4xHpJwRsoq/qr5wmF1tVF3Ks7QvOPGAuxB1bPutmOYzt9RpFKeg\n" +
            "5aSS3+L3xnIFcPA6DaugmENQyn2QT4c2nxEsos5e0m23yuEnMsr8MLIdjmTs8aVO\n" +
            "0yvYsz8+LQ6Pg9AjJg1BAoIBAQD8xmIFwbxinTDDqplGP1qOXQ6r3ZbS+FZXt5oB\n" +
            "eMQwf4TqKw1uZcNCNyvfJOShXIMQVzCicrfXh+0n4Q0Fqo5grIwHB8ol8X7TyREN\n" +
            "+O+RshI/jPOJi8qKXQvyMOebrmzbbtAOj/GOj/2knOVdx0RCeMETKA/9SHiX/UgW\n" +
            "f+SRakz/zQLesvre9SP9mpMdpV+IuIJQp25CRe4uxYvc9B5riz3+VVqbUsZTk2p5\n" +
            "MfOuMPFrejo8D2HlP73bgh+sMIVBcIQ7KYDtPbWtwi4p0q2oHrg8EMe/IrdnAKWJ\n" +
            "wODr1ShSETbKGFGORYMGXA6gp6SVnMFVpik7ZcsHzJoanUqZAoIBAQC60qfmLBKw\n" +
            "gdsX5U42lTp3xq661cPPKYK7FvTXDk+RNQHnPzfE3rKepu5ir2/R8mgDF8Ny1HA7\n" +
            "KVHvP+R8M2ylueHVfiavKc2bp8QXOIKSS1EmTRoxSye7thhJEgfMlyzv0plFILWL\n" +
            "/qEVrzVsI9YdaR4gH318GtVhxx+o4ATmx+vaXx1dyrXQx65BjAczPmnxFG6+Vi3A\n" +
            "CbmDGR8ikL4AR/XLy29rzAslmkPjAqfLQPKBjUTA3h1WsBbZb7+H4cF/8uo8YgBg\n" +
            "zBvXhCRPl34WwYHrtN7XvQEbUifSvb0KWy7c3omFSmRvohIPz1QqwnhbpbrGzMIq\n" +
            "gWK/m2JEDNxRAoIBAQCHk6QZsFrpjaQNHqCNRAe/Gfx3hCeUSmyNKcXpiiu4U30n\n" +
            "2CWhE+XRyyO10OTsA2Gszwcf6mXerWIvJI9Gfb9V7NZCIxRym3/J+isyklV0kM0m\n" +
            "1uP830SoR9XTdhWTs89hNqNTXDNUoriJpTmEvg9Hk9sC+lbxJADCjLAwmTIwKJLa\n" +
            "FMy4Q8k48sT2DgVgPkXqYARNXDBQTY+9+GjW/vyd9BYxJvitHI2RYB0Ymgk4Ybnr\n" +
            "SqiXdCyuuCyN4tI4GeIvQjk6Pj2PgazDYMY/2mcYKDnqxZlNVEKIdZlUNGavNyok\n" +
            "MmVvqfF8XJ41OAUEeEZXlLdByBiPaj6mlUAGjFzpAoIBAQCqPOEALfMN4haKbMpb\n" +
            "lJoFoQeBet8F/DaDU0TiXskEcX/9f67K939ecJDTXjgP4MsE3necQJbQYjOmRttm\n" +
            "jdmYHoz6ZQzNGtOf3j0iR707n+Qfeay/dFONij4Q/5wZwzwQgSBnTIoCOpIA13pf\n" +
            "wzwN6san0Oog17bPdYqU8SWmQlDZ2UhCOkB5MivI0NeSy3HwW4Uabh8cWgAwxQiJ\n" +
            "D8oDKY+CGCu8197kcfEggD/0R4dzpA4djYjAgI8hreGi6YXQmML3KPpdOkC4n1IT\n" +
            "MOGVzNkdtuBFSJYkljVxpLIJKNPIMgSwb/ybeNzqiSuSF0ljgd5z3wujfelE3kup\n" +
            "5OoxAoIBAQCgnS8tIIE9/oqHzYkXfQ0vL6hQu/bOXHVRmAl6YVLxd6Ptl2hXG3rw\n" +
            "6Cjx1Ob05lmLI4wB17O3/Y6WBoHBi9SgHzXQHmhbXBPnb8pc+EKObApfCLSB8/FW\n" +
            "pFTF7W8XpzVQbXrc3gTFVbUFKRRaCA2pDeTU6XlzRKLW7PW5FmbPmGQ6yX28GB3V\n" +
            "wLl2IpPQzUWm4OhH8TrNqKeXaN8i8HHR4oyxnuBUqYhYxxeiGt/w58BffZvCqV1X\n" +
            "S36FqHrBnoi2EQyxhaNZJp6s2ZCSY7r24lTZnhJXhxPl0KeWjxBNhgu5RRBM3E27\n" +
            "Y8Iv6O84U9BbWbFimusE0LBsEHK/WTZQ\n" +
            "-----END PRIVATE KEY-----";

    private static final String PUBLICKEY_STRING="-----BEGIN CERTIFICATE-----\n" +
            "MIIEyDCCArCgAwIBAgIGAX9tcLjvMA0GCSqGSIb3DQEBCwUAMBcxFTATBgNVBAMT\n" +
            "DGtlcmFsYXBvbGljZTAeFw0yMjAzMDgwNjQ5MzhaFw0yMjAyMjExNTA3NDhaMBcx\n" +
            "FTATBgoJkiaJk/IsZAEZFgVJQ09QUzCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCC\n" +
            "AgoCggIBALh4I71HLivRi1n24UVKglPzZSdA3P4I3oXY5zrgTqN9ZKcdWxJ/7Cmc\n" +
            "qy2eevVKVsgpKERfVunCBI8e+//H4N2YZqM7NxbB5ObFET0zJs7JcadM3AAJOPF4\n" +
            "F4V9LUupZ6AWYgKtU+bwV/GKswKlljd9yIfS6aVShTnv5P6X/yenooLDmI1dKKLK\n" +
            "8mLFlHPCc6uH88JJfszMo/Fd5bvxQ/6cZ5nmCAj4a+13wUOVAZTRwxIgtV9n9wTA\n" +
            "qWjS0nXIE4I9P4IVJg3d0xB2tGYJLezSESIdOQ2w+vHFqouXHL93V7j4BvZAKCmH\n" +
            "/GPj5A5drW3RuMhb+SZiWr089KjJSkv0rWvJGSyfhcTnmNXyGte0pygyysgR/elc\n" +
            "RQij0Lp7daIBPQbzwZPBDq+OgTgBwleUe9edUwx/n+yA5U4CG5HJoy527tD+uxIe\n" +
            "quAp9hyr2a2/NZ4t2jwtr6URzBFC2YTxZR2k8kVbb3fWNMbBk5BtBfIslOeRhsBQ\n" +
            "W/wC+Uf5wU6QYZfFbWKKCKT5ImTBW5rK0O+MSdRaT/uUdzH64Nld2wP8/blaFibU\n" +
            "aBhKaxrLBiKE3Xfy9DM0JJoTrb03bxbS+5noANQnF0xB1FDQLfBpLRO86Rj73G6a\n" +
            "iuD3kTujTvpQGk3Qydd6IBSst5I4p/F+RRukl0RAhB4fZGO7DBZpAgMBAAGjGjAY\n" +
            "MBYGA1UdJQEB/wQMMAoGCCsGAQUFBwMIMA0GCSqGSIb3DQEBCwUAA4ICAQAPCIuE\n" +
            "tW+Y7jR4IPG0ALXDoAtM4h4/WoOxylxxgskB6pj/3PgzH28xv3iO0lhxkKLbv+Hw\n" +
            "8OpfztjsSzlSyfuN7fcEhr0in58HvlzyECAgqiUGcibyeAMEt1txe+Konnj+PiRS\n" +
            "E3Qcc1Q/chRlRaVRKM0NLRbZbr+U4n9WzFDJnVvVbHOmSfePNlZbmo/X3g5dE73Z\n" +
            "AEHRIuSFGsDQvekB2nOdqcJ7Yb1Atc2uLgfeeWsXxqvhiG+B7eqZQdQSdxz1doeP\n" +
            "5ySc98HRXUSJalGXhkSonQTsZFETTlUf2Y4lZKOL+gk0DoscxOFBsAw7VTNaMbGr\n" +
            "yDwp72O32ayiPZJtTqQcAMq7UUqVyQOEzp25tL436kUE6zZBSlm76oxoUAaayhxJ\n" +
            "3B4eRricH9eM6jPkDwF0OlLab3QB6n3jOFondLhHTs3/63RBs8Wi3S9AxH7Myx0U\n" +
            "bT9wIqJLkBOthkoi+iTvKlFnNOCBCRXMnTO9LPB9evf+oRDk5rkABo/SDc9BWxPR\n" +
            "qsNWu+I8HBMXZThTRKQ2NUTIF+hic0DXPTpvKOSmFz9lGBb4iCCozjtfwSYYTzpv\n" +
            "4uwE0gcBpD4Z3r70GGiSZwDLlKy/iqHG9bE/cQlA7ywiYDQD7Wu4P02LFJQth8u8\n" +
            "5099V3071lF94chjU/2skvbUoh9MjZ8PoBGS2g==\n" +
            "-----END CERTIFICATE-----";


    public  byte[] encryptData(byte[] data) throws CertificateException, CMSException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //X509Certificate encryptionCertificate = (X509Certificate) fact.generateCertificate(classloader.getResourceAsStream("cert_cctns.pem"));
        X509Certificate encryptionCertificate = convertToX509Cert(PUBLICKEY_STRING);
        byte[] encryptedData = null;
        if (null != data && null != encryptionCertificate) {
            CMSEnvelopedDataGenerator cmsEnvelopedDataGenerator = new CMSEnvelopedDataGenerator();
            JceKeyTransRecipientInfoGenerator jceKey = new JceKeyTransRecipientInfoGenerator(encryptionCertificate);
            cmsEnvelopedDataGenerator.addRecipientInfoGenerator(jceKey);
            CMSTypedData msg = new CMSProcessableByteArray(data);
            OutputEncryptor encryptor = new JceCMSContentEncryptorBuilder(CMSAlgorithm.AES256_CBC).setProvider("BC")
                    .build();
            CMSEnvelopedData cmsEnvelopedData = cmsEnvelopedDataGenerator.generate(msg, encryptor);
            encryptedData = cmsEnvelopedData.getEncoded();
        }
        return encryptedData;
    }

    public  byte[] decryptData(byte[] encryptedData) throws Exception {
            CMSEnvelopedData envelopedData = new CMSEnvelopedData(encryptedData);
            Collection<RecipientInformation> recipients = envelopedData.getRecipientInfos().getRecipients();
            KeyTransRecipientInformation recipientInfo = (KeyTransRecipientInformation) recipients.iterator().next();
            JceKeyTransRecipient recipient = new JceKeyTransEnvelopedRecipient(getPrivateKey(PRIVATEKEY_STRING));
            return recipientInfo.getContent(recipient);
    }

    public  X509Certificate convertToX509Cert(String certificateString) throws CertificateException {
        X509Certificate certificate = null;
        CertificateFactory cf = null;
        try {
            if (certificateString != null && !certificateString.trim().isEmpty()) {
                certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----\n", "")
                        .replace("-----END CERTIFICATE-----", ""); // NEED FOR PEM FORMAT CERT STRING
                byte[] certificateData = Base64.getMimeDecoder().decode(certificateString);
                cf = CertificateFactory.getInstance("X509");
                certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));
            }
        } catch (CertificateException e) {
            throw new CertificateException(e);
        }
        return certificate;
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




    public byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }


}
