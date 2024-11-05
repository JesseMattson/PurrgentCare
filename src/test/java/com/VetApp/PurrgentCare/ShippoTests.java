package com.VetApp.PurrgentCare;

import com.shippo.sdk.Shippo;
import com.shippo.sdk.models.components.AddressCreateRequest;
import com.shippo.sdk.models.errors.SDKError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShippoTests {

    private final String apiKey = System.getenv("SHIPPO_API_KEY");
    private Shippo sdk;

    @BeforeEach
    public void setup() {
        sdk = Shippo.builder()
                .apiKeyHeader(apiKey)
                .build();
    }

    @Test
    public void listAddressesTest() throws Exception {
        try {
            final var response = sdk.addresses().list()
                    .page(1L)
                    .results(5L)
                    .call();

            System.out.println(response);
        } catch (SDKError e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void createAddressTest() throws Exception {
        final var response = sdk.addresses().create()
                .shippoApiVersion("2018-02-08")
                .addressCreateRequest(AddressCreateRequest.builder()
                        .country("US")
                        .name("Shwan Ippotle")
                        .company("Shippo")
                        .street1("215 Clayton St.")
                        .street3("")
                        .streetNo("")
                        .city("San Francisco")
                        .state("CA")
                        .zip("94117")
                        .phone("+1 555 341 9393")
                        .email("shippotle@shippo.com")
                        .isResidential(true)
                        .metadata("Customer ID 123456")
                        .validate(true)
                        .build())
                .call();
        System.out.println(response);
    }

    @Test
    public void getAddressTest() throws Exception {
        final var response = sdk.addresses().get()
                .addressId("1")
                .shippoApiVersion("2018-02-08")
                .call();
        System.out.println(response);
    }

    @Test
    public void validateAddressTest() throws Exception {
        final var response = sdk.addresses().validate()
                .addressId("1")
                .shippoApiVersion("2018-02-08")
                .call();
        System.out.println(response);
    }
}
