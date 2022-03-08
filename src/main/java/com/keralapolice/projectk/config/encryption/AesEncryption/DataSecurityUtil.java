package com.keralapolice.projectk.config.encryption.AesEncryption;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
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

    private static final String PRIVATEKEY_STRING="-----BEGIN CERTIFICATE-----\n" +
            "MIIJQgIBADANBgkqhkiG9w0BAQEFAASCCSwwggkoAgEAAoICAQC8Mbzt2mIugqVm\n" +
            "RYYwziE+LIwp23J+ISI0Dvn4VdB6tSSNm/EE6Mz/mHrzQYGNvwxTUGzv9ErWrqGm\n" +
            "Wn2ukhxgXFisOf+sdfSOFa/l1Iiw1LybmFx7T1a+w5VwufMTbxkXa/ChfbH8gwW7\n" +
            "YOyyRIMnW0wCB6W77oqTVSI4+t4vLYgY9Zcu7jNVqsyLKM8oekNE1qpdJ7fT+UDV\n" +
            "r0hlKmZhPoSWjCYTbFESX1nvzjz3ZtUL6MVIPxUJAM1s6LfFl2D3X+gVPu/ZUnzS\n" +
            "xKZ19Kbx2ogw553+vB8iCB73ixHM7cZ4DnjTljvkSnUdmTq5d2O3urJJyl98eAj+\n" +
            "Sff2Da7ShkcL9hSv+eVbhU6JW+ufPDgaG87qL78sDx9gcDjo3tpRfTnbylbsZUnq\n" +
            "64jCFyknLcmPdcNt9Bh4v16eT1ydOh/NBHDER3h+QhR4xYEx8mUedcBPoy4MP3o+\n" +
            "dGWB5OAoZRLmITjm/M3nYyeogYOWxweljeQ2qK5jfFJ4dgc55ICSDZ9LQaX47WNp\n" +
            "Tu1EEQnXJhV86f+4FcoqL0/ohhcmZnHcdOulf/pv2okIv8BfWq2pp//wZpVs07gk\n" +
            "0ptCdWrhBtGIexr428kY6cp7pean7oBJVKxJBZQzWgfsk3C+K0zBLNVwYdQeGtHM\n" +
            "LTf+zj3gbDfTSmul/OuL/gV3unTtYQIDAQABAoICAAOf6J3jKCNxsDpXSfWcLr86\n" +
            "D+wJdjvQKw66IB3klzqZzQ2uLHb1/ebJWsrfHt3qSVQ8dxCrEZrva50e3o9TcJj5\n" +
            "hlqw1kgN0ItBBy2DyaswOb68cUGNvmO3tCPi7PfZmq/dDBosJ9avdleMzxpC4AzM\n" +
            "wRrD53EGb+RVDi7vRALAG4AxkLseat4yRzTB8TV8H1oO0WKwbmW2hgVnlhsetTCX\n" +
            "yel1YxFaM3iJXhvUis+QCzfqwIv67Q6KlapMlCeJ2hwBnKB2IRv8LvvHPlaEwwdi\n" +
            "+F8KwvwCRw46flAxvULHbJxo2PJoURh9R6Ktjhg+4KyL+72YmHy2+iNdrNkwG8cZ\n" +
            "NEdCN6wOhCj1C0ximW3mUKrsjb7ZyWIWDXhpnwwB7snHNuFOdBmFRW2Q6FtXqUnr\n" +
            "iz3tymBOTXlbZ8/ujOEHokGbgeOCWAROnA1lbGJ7LvpiqH4LFa41BkQSpNbhKVZc\n" +
            "uPwmR3+AfNk1O9aRLzazflIZtwZqZ7o633mf3Ry36JB1suORB4ctqKLin4cWlpM+\n" +
            "mzbHitngNaMXij3VeO0pvUotzJSXhvR3awmtrvw25IHBUtfUG87DloMdG7YvQKaC\n" +
            "U2Fzn1lyx7b7LOQCUFsNvR4vbArPr8ErNGZJsXOcj2zStT4W8O1u1VUcMoquP9+9\n" +
            "x2hxOBzHLf55z9ZGyNVNAoIBAQD9qha57jf6Y2dJZwc1ZQBl8Q9rTJI1q6UjLHso\n" +
            "uccSGrxmy/sYIP8nZyaFyn1wgZsIpBjayEb9Xd1lET+nwQvPZOVPpacZs3voqJT5\n" +
            "sffSGxPheljXdlMa7obrWm9EzhjR7h3k49cxOiH5Nz1HnGzoAbC0jEmfkm4uoLjZ\n" +
            "tZ4vQQirDTXrlwNltQGzuJBQwLOfdHtWFzEnn9hVdhKXSewpNO/R2d298543KFKd\n" +
            "9XiPJoVMoUPVcSRBKenEUnYtmVgPnmwex+0PG0yQ+FwAH7/mJ3mSo52fUZJYKOBP\n" +
            "vKKxMNpMsBR4Tsph5j0NuVin2yKI1WOoHBPXK8itjaEaPTktAoIBAQC97VR0zLgm\n" +
            "Z1cdR00IzP8flJ6ln9JRIwRsxiG/3777yqTBHU5mlKU4A8HYTBWyPSfjZvosYuwC\n" +
            "sFUaR9NMvEFapl9C6j4JtuITlMFfp0rYJZ9dfO+hibodgOW+gIwPHfilEqgJS4Xv\n" +
            "tUwDj2naRUqvTqVJ4QAqMHcvOxqN0gXGVBkgLiT4IxjID6T0gv7en6wVUN4ij3VS\n" +
            "9eHc01IPtLXe1p8++sh/I8WLv8+XPQ4QY3e/SSUmFHByQJ0xTg/5+NPb5J3oDUOl\n" +
            "6JyOY6ekfHNLfmXhulYr/THs0M1Fu9qTGSMIfgd0SrBnysQAFCWB/sHP4l2oyivr\n" +
            "5HDCIboBML2FAoIBAQCSiAisy0Gm8Hkf29Y8L+Y0/TWtBASJ4Gd6lB1o/zhR6wPe\n" +
            "VgLsg1MhjeKg8sHXleba7QA2ke8AOZLE6VveKhQKb/xPMCABJr9zPL8iJn5D7sRj\n" +
            "QevEO2PycMubdRhFpNCNpIG8USlkFHK/pftOPLxkoWVURp3ZAhuH7+wYR2PjZCv/\n" +
            "s5fsScX6oL8k9H7ZRxf8CqucpbUNcLblV1mR72/utde7X6FKM0q4DiD7gmtsNhJW\n" +
            "m+m1iLAqvG1/rSr7WFOYYimO9mqoiVqHfBCZOb3+09rUrJNHDzX8m1BIf21z8gaI\n" +
            "ukZ44Prj5r5qCSF/hYTDipEQ8Xc8mtxH9UKVB7zZAoIBAHin5vl0fKBZEiyVeUjY\n" +
            "AlNrtQV+7LuCxXLcw+ADIm8PI29TUNU2JWfaazEf1MCwvUBn0RKVnV1v7ACQFJox\n" +
            "FkuiWe5GpTbCqgjKfbZortF1xTI9BnbW6heIJWvKei+4WJd1rK6K/gF0aV/xoct6\n" +
            "LaIFElhGyjYDKBQnNg1WmTiPgI7DAdVpZnriBWcYORXw1f+MqjHGdUBI2D3TOdSk\n" +
            "ds2kBvKZmvm6HetT5Wss7FxY9F/e9eylG3JZvgLulpO1EdImAHLEym8BwUNr57pF\n" +
            "HXGeUNb/63JynV7WnzFnz8mXNHxWpCGquTqWw8FRCyr0Tufj5KAwH4rySvfpOSM8\n" +
            "dYkCggEAQF/7rDoYt2ROHhWbN+QIZ+v1Di3EGU1MTLL79oG87mayT73U2W4iUKhf\n" +
            "ZWjGJpkpWB+OX9PaGl+gg5qQ/uJS7qgs0UJl61ItMzHnzOKtv59kZoBWo9r8iG3h\n" +
            "wF11lsJGX7Xn1EZaPs92B8+T//Lsal73asnawJONpv79Rbf/5To+KjpkFDtttDcu\n" +
            "gUn+ElIWL+aSadoO4cr+wkVVV/jPzDmHiyUeSFD9rsP1K5IM/JpDP/bDrF1sgDjJ\n" +
            "N+5hclhAtA+i2yCndznutvsbOA9EAgyQ3LwswvWgtzuhPbN+kNNfhVuCbo29+mmq\n" +
            "r6Bb0k5BTkpsdj8V4H/osD7szvgpWQ==\n" +
            "-----END CERTIFICATE-----";
    private static final String PUBLICKEY_STRING="-----BEGIN CERTIFICATE-----\n" +
            "MIIEyDCCArCgAwIBAgIGAX9oJAwwMA0GCSqGSIb3DQEBCwUAMBcxFTATBgNVBAMT\n" +
            "DGtlcmFsYXBvbGljZTAeFw0yMjAzMDcwNjA3NDdaFw0yMjAyMjAxNDI1NTdaMBcx\n" +
            "FTATBgoJkiaJk/IsZAEZFgVJQ09QUzCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCC\n" +
            "AgoCggIBALwxvO3aYi6CpWZFhjDOIT4sjCnbcn4hIjQO+fhV0Hq1JI2b8QTozP+Y\n" +
            "evNBgY2/DFNQbO/0StauoaZafa6SHGBcWKw5/6x19I4Vr+XUiLDUvJuYXHtPVr7D\n" +
            "lXC58xNvGRdr8KF9sfyDBbtg7LJEgydbTAIHpbvuipNVIjj63i8tiBj1ly7uM1Wq\n" +
            "zIsozyh6Q0TWql0nt9P5QNWvSGUqZmE+hJaMJhNsURJfWe/OPPdm1QvoxUg/FQkA\n" +
            "zWzot8WXYPdf6BU+79lSfNLEpnX0pvHaiDDnnf68HyIIHveLEcztxngOeNOWO+RK\n" +
            "dR2ZOrl3Y7e6sknKX3x4CP5J9/YNrtKGRwv2FK/55VuFTolb6588OBobzuovvywP\n" +
            "H2BwOOje2lF9OdvKVuxlSerriMIXKSctyY91w230GHi/Xp5PXJ06H80EcMRHeH5C\n" +
            "FHjFgTHyZR51wE+jLgw/ej50ZYHk4ChlEuYhOOb8zedjJ6iBg5bHB6WN5DaormN8\n" +
            "Unh2BznkgJINn0tBpfjtY2lO7UQRCdcmFXzp/7gVyiovT+iGFyZmcdx066V/+m/a\n" +
            "iQi/wF9aramn//BmlWzTuCTSm0J1auEG0Yh7GvjbyRjpynul5qfugElUrEkFlDNa\n" +
            "B+yTcL4rTMEs1XBh1B4a0cwtN/7OPeBsN9NKa6X864v+BXe6dO1hAgMBAAGjGjAY\n" +
            "MBYGA1UdJQEB/wQMMAoGCCsGAQUFBwMIMA0GCSqGSIb3DQEBCwUAA4ICAQAJlrHU\n" +
            "x9X3ajCoxIQ5c/1uoKZZNDE1EJ38KcYnT0yLyYmRico3YypzkGlsT3nbsAM/Z/rD\n" +
            "Cuy7ICjfZWVPV7auOp2TF8prZBp3aAC2Jx5IQXOqOByldvXLmjUXMfXO4ZaAo5QU\n" +
            "oTc/ltd4h8jyBL62Na93DFXr/aCW4O3Gzv0AbB6AMQj7sPt1ZquaoA2/nmje4qH9\n" +
            "L+Mil50LlPa2SE/iWxyTa0tCAjuC7grTjrlBiK+NWAN/wVIqzkkUe214/0d5js1s\n" +
            "iscIHBRWo7SDyWMgFQobM5VJ7RXUCqQYP4toM8tfV4WTDVM6XuPkluFg/4LkZKn8\n" +
            "EBBTzNQfLofhPc1acYLEhiZmAArJSdra1jGpSuH6xX1owe/SVpae7bOH7hLW4gYD\n" +
            "kCaqJtGPHdKqYIComUef/jFHbTo/n9hmgBJs1B4+cfvu2p23U1LU3nilnxV4NmI5\n" +
            "8jtzgi2nCI4LYguCOreyqU3k7QgvkdACqfrMb9w6JVjMEEBesDgB2iLjiKz9HVuA\n" +
            "xyQuLe44IVXgP5jdgSOhd+X7ucNBIfKFu7PrEBsNYmLghjhgiSNQ7c5jR4/gSrRn\n" +
            "otFNtBcts3wB8iBe1JAhFUz9cQbWe8fQJhruXJmcKsEwLIPSzjoOSCun0u1QmRvJ\n" +
            "RyTUgoIIjYJbZNXI6VnUkw49WaXvcs4ntZ6MkA==\n" +
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
