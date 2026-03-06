package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseStreamObserver){

        log.info("createBillingAccount request received {} ", billingRequest.toString());

        // Business logic - e.g save to database, perform calculations etc

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("ACTIVE")
                .build();

        // to send response from grpc service (billing service) back to the
        // client (patient service)
        responseStreamObserver.onNext(billingResponse);
        // response is completed, and we are ready to end the cycle for this response
        responseStreamObserver.onCompleted();
    }
}
