package com.example.aws;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.ListAccountAliasesResponse;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

@RestController
public class RootController {

  @GetMapping("/")
  Map<String, Object> whoAmI() {
    ProfileCredentialsProvider  creds = ProfileCredentialsProvider.create("default");
    StsClient sts = StsClient.builder().credentialsProvider(creds).build();
    GetCallerIdentityResponse callerIdentity = sts.getCallerIdentity();

    IamClient iam = IamClient.builder().credentialsProvider(creds).build();
    ListAccountAliasesResponse aliases = iam.listAccountAliases();

    return Map.of("account", callerIdentity.account(),
        "ARN", callerIdentity.arn(),
        "userId", callerIdentity.userId(),
        "accountAliases",aliases.accountAliases());

  }
}
