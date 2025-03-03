package cama.api.local.otp;

import lombok.Getter;
import lombok.ToString;

@ToString
class OtpCode {
    @Getter
    private final String phoneNumber;
    @Getter
    private final String code;
    @Getter
    private final String xCorrelator;

    public OtpCode(String number, String code, String uuid) {
        this.phoneNumber = number;
        this.code = code;
        this.xCorrelator = uuid;
    }

    public OtpCode(String[] csvLineArgs) {
        this.phoneNumber = csvLineArgs[0];
        this.code = csvLineArgs[1];
        this.xCorrelator = csvLineArgs[2];
    }
}
