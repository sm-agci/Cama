package cama.otp.mock.codes;

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
    public OtpCode(String[] csvLineArgs) {
        this.phoneNumber = csvLineArgs[0];
        this.code = csvLineArgs[1];
        this.xCorrelator = csvLineArgs[2];
    }
}
