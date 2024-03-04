package com.example.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/***
 * openssl genrsa -out private-key.pem 2048
 * openssl rsa -in private-key.pem -pubout -out public-key.pem
 */
public class Example {

    private static final String PRIVATE_KEY = """
            -----BEGIN PRIVATE KEY-----
            MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDVXbfNbC4OR55X
            UdzqCfNgzh/koPpuM5R8V2hdbhNqxbbucEGadoqrqyDGLKNd5KV6+50o6vPwdzJr
            W4BB8iiosO9K4lXFtFLVM1hZq2+zcG3uxV1TG//tU0teG94OTCMam92o5tPA/1Dh
            6L8yyD6k3SZMpr0PYmqvwNELpFsL1d7qcXsz0dk2L95rcGGQOZRj4JjB0p93AWBf
            Y3m6/B2eUWYnhGj8/VU9ZjM6ES5enZbLuLwZZb1BxILQNl5CkBf1SuU26mdJdEv5
            xpAEp6zrzdxmCpoZy7bF84JRgEfnnt7hNcQWT720tkDstpwzfP78b0x9vb3vYyf4
            hpNkVIg7AgMBAAECggEAAtgEbqL9/Ce9Nwm6yY3HZidpKmlw3Q4n8P5TQEeKz7YB
            6ayqWy4wS/h0b9/Bb6tv/zMiVZzIEpDsJZ0rGPXqz9DH57YbkY6FOh08lLlhvEEH
            3hJYW5a4cwfUrdTuQT+Hij/AlRM6iEx+3fv1rG4Z06W08HmadH2J0Xze7GpM4lv5
            pK4GV5KNafLj6SsbtYlA95lGhfF1yWfE6+biwUpQMZknSkbfaF2ccEnEXtLB6amE
            JooDA78ppA+WPFzJJTuPqEKi2zS2mbMRZ8oCYbTQf6vzL+/CTNCy3NVv6UMG7Dq7
            4nQ4x6w4S3cFyrvVeRCbvzwe2vAzSQRUWmlSuhTKrQKBgQDYtoi50o7rTSRfAXqy
            yxioRaqlWxs7ZDTssvhUp1xK0J7O7ASgzxzLep7Kyy4z6lu/ROV02JJbpny81t0+
            bwgjxTtCHEOCoxRNcW4u4Rid6zSAm2t1zg+nTL3+V26EDWMQ2kalMCR9FDAlKqqY
            XUo+2CKvmE1xrMsTE2VVxJxfJwKBgQD8C9r5eZ4UTXroK60JSZvdp3HZQOor/T3G
            kDGCg/+ESSBhLkNayvWM3h79lM1eRPJTxhTpP07x4UIeND/q68uvqSk/NlO3isbV
            GsK3F8dEyROcmBUI3yav7AGR1usr9w4yyhcOhJNNnVqC/cwQd/OAcbWnSHs5AAhd
            8kQ49na6zQKBgQDLYjPbQXMD/ff+eeLvXZAcjJVkbqaWDKtm4WgR+qexRy1jwTlu
            ujGp/cB9TkZkTrQgQ8ac7IwfG5bBm100NPwMphPBLclmFyr8q1HfCT4TJyS09ifZ
            /bPPLUS6KM/9eprYvCHE8fOst3+fwfUs3ZDKfgm/hk5YOvSzSGa4IbcnzQKBgAMY
            ji8ETGnJbdoON0jm65A0b1SQ1I+Hw8t9l+4Xevr//vLSo6co2Q1K6SyKOF+RDzzy
            MO2QhoK4FpzSXXqgPNdCh6wsGakmfjy94GhpVVxF8AS7ZX6/ZvVAO+CzuB3JIhaD
            nvbhsAGDDkqPnYMSRh5sxyNv43uWwYON50Kf3GN9AoGAGKaZDPlRmsne1aoeTwi3
            VkSAcFVF5iuzROXrziPGNR4r2DrZleH56p49qKv9ANNsJy9gvOv/C/6p6GT0zwXp
            lFJA5ke6cxEjemvQzubM+FqtCQXvZwMp7BLGQlsBeweYvdFl3Bj+DN04i+0uhGEq
            upkOmXvFqUxY0U0gClHWibA=
            -----END PRIVATE KEY-----
            """;

    private static final String PUBLIC_KEY = """
            -----BEGIN PUBLIC KEY-----
            MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1V23zWwuDkeeV1Hc6gnz
            YM4f5KD6bjOUfFdoXW4TasW27nBBmnaKq6sgxiyjXeSlevudKOrz8Hcya1uAQfIo
            qLDvSuJVxbRS1TNYWatvs3Bt7sVdUxv/7VNLXhveDkwjGpvdqObTwP9Q4ei/Msg+
            pN0mTKa9D2Jqr8DRC6RbC9Xe6nF7M9HZNi/ea3BhkDmUY+CYwdKfdwFgX2N5uvwd
            nlFmJ4Ro/P1VPWYzOhEuXp2Wy7i8GWW9QcSC0DZeQpAX9UrlNupnSXRL+caQBKes
            683cZgqaGcu2xfOCUYBH557e4TXEFk+9tLZA7LacM3z+/G9Mfb2972Mn+IaTZFSI
            OwIDAQAB
            -----END PUBLIC KEY-----
            """;

    public static void main(String[] args) throws JOSEException {
        Instant timestamp = Instant.now();

        RSAKey privateKey = JWK.parseFromPEMEncodedObjects(PRIVATE_KEY).toRSAKey();
        RSAKey publicKey = JWK.parseFromPEMEncodedObjects(PUBLIC_KEY).toRSAKey();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .issuer("http://localhost")
                .subject("ivan")
                .issueTime(Date.from(timestamp))
                .expirationTime(Date.from(timestamp.plus(Duration.ofHours(1))))
                .build();

        SignedJWT signedJwt = new SignedJWT(header, claimSet);
        signedJwt.sign(new RSASSASigner(privateKey));

        System.out.println(signedJwt.serialize());
        System.out.println();
        System.out.println(publicKey);
    }

}
