package com.keralapolice.projectk.admin.rank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralapolice.projectk.config.encryption.AesEncryption.DataSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class TestDecryption {

    @Autowired
    DataSecurityUtil dataSecurityUtil;


    @PostMapping("/decryptUsingAes")
    public String decryptUsingAes() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DataSecurityUtil dataSecurityUtil = new DataSecurityUtil();
        byte [] data = dataSecurityUtil.decryptData(Base64.getDecoder().decode("MIAGCSqGSIb3DQEHA6CAMIACAQAxggI9MIICOQIBADAhMBcxFTATBgNVBAMTDGtlcmFsYXBvbGljZQIGAYCdKMczMA0GCSqGSIb3DQEBAQUABIICADCCunuv1uFf6BU7zVrp5hFCDM9XCxeT4TKYY+NmNpvmrmBZvNJxQiLWjK3HRl4Kv2d4ydH/WGYrhnjH05OQm6H8I82Zb1Du+XlyY68CcoGtk5Jj4YwydFtHZO7JlD6+1x7kKJ/xPMoieONlI7vSfkPD2hfgTqMIBahHIEZP2QKgFMuj+Hldg4eCDj8z/JxdKDdH/2B7Wfi4S6YKt96IbZhI3kLOtLcVKqQlFEhBfKsZKIxJAVkpEiWBdMdjUJckBEGXjri9mvtbDqcsk8b3fw7H9dSrUU7zhptJEna28PKXQ3PWFy39vZn7VdnTVm8ijHg6iwLJ6kTBFUU/Gk3Ptqq21EQCyBJKVqf80TSR4Pw0263qxOGiyidNc+AkUXIu9+ZcrcGjcNklAnQSt0i0GNu8MMEjiK79evVRgnDMAWBPwySAyzoyWXH1nRfWkvM1GDcot6OIlLrte+g3kPwCl7YMvMZq2Z4ygvWng7Xl4ou0KIB7yty/KlUubgj7z7Ioc1ACfOSiB7LVVmRHdiI9DYD68taN7B63gSJdVfiTW9IgUXRMBP12Eg7NQM/7csauxF6LArZXfwZfRKhMEsRycMp+Rxk9BIM2gc/M0Rtu8K2xTNzieM8tW/RAqvctO27v2OxgbQRZ1/1LmBfmP/dLvxmEzjCHOaM01PikWGXr8OEMMIAGCSqGSIb3DQEHATAdBglghkgBZQMEASoEEHMYkfYOnIG6Et0bMkYrVkiggARQZr9uH5BHUhGbGz4oe7ZhD87T6rTOtBoQi9A6v0D7fmxu4ti7//ZvxIrsf+98Em3ZD6p7qTAhxt1x6yqV9q1rXvoYhGgR/s/tgeSehwVsQgsAAAAAAAAAAAAA"));
        byte[] decodeBytes= Base64.getDecoder().decode(dataSecurityUtil.encode(data));
//                RankVo rankVo = objectMapper.readValue(decodeBytes,RankVo.class);
//                return rankVo;
        String decryptedString = new String(decodeBytes);
        return decryptedString;
    }
}
