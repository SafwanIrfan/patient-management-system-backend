package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    //anytime we make call to billing service using blockingStub then
    //execution will wait for the response from the billing service
    //before we continue (Grpc request)
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    // localhost:9091/BillingService/CreatePatientAccount
    // aws.grpc:123123/BillingService/CreatePatientAccount
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9091}") int serverPort
    ) {
        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort);

        // it takes serverAddress and serverPort and creates a managed channel
        // usePlainText disables encryption to make ease for test local
        //development and testing
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name,
                                                String email){
        BillingRequest billingRequest =
                BillingRequest.newBuilder().setPatientId(patientId)
                        .setName(name).setEmail(email).build();

        BillingResponse billingResponse = blockingStub.createBillingAccount(billingRequest);
        log.info("Received response from Billing Service via GRPC: {}",  billingResponse);
        return billingResponse;
    }
}
