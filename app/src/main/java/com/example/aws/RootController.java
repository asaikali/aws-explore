package com.example.aws;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

@RestController
public class RootController {

  @GetMapping("/")
  Map<String, String> whoAmI() {
    ProfileCredentialsProvider  creds = ProfileCredentialsProvider.create("default");
    StsClient sts = StsClient.builder().credentialsProvider(creds).build();
    GetCallerIdentityResponse callerIdentity = sts.getCallerIdentity();

    return Map.of("account", callerIdentity.account(),
        "ARN", callerIdentity.arn(),
        "userId", callerIdentity.userId());

  }
}
