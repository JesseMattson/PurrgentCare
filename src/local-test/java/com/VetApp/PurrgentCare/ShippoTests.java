package com.VetApp.PurrgentCare;

import com.shippo.sdk.Shippo;
import com.shippo.sdk.models.components.*;
import com.shippo.sdk.models.errors.SDKError;
import com.shippo.sdk.models.operations.ListShipmentsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.List;

@EnabledIfEnvironmentVariable(named = "SHIPPO_API_KEY", matches = ".+")
public class ShippoTests {

    private final String apiKey = System.getenv("SHIPPO_API_KEY");
    private final String addressObjectId = "fd14e6dc2fd444128e76f5dbd1f01f21";
    private final String shippoAccountObjectId = "051737b2c2924b2981090464814f60c7";
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
    @Disabled
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
                .addressId(addressObjectId)
                .shippoApiVersion("2018-02-08")
                .call();
        System.out.println(response);
    }

    @Test
    public void validateAddressTest() throws Exception {
        final var response = sdk.addresses().validate()
                .addressId(addressObjectId)
                .shippoApiVersion("2018-02-08")
                .call();
        System.out.println(response);
    }

    @Test
    public void listShippoAccountsTest() throws Exception {
        final var response = sdk.shippoAccounts().list()
                .page(1L)
                .results(25L)
                .shippoApiVersion("2018-02-08")
                .call();
        System.out.println(response);
    }

    @Test
    @Disabled
    public void createShippoAccountTest() throws Exception {
        final var response = sdk.shippoAccounts().create()
                .shippoApiVersion("2018-02-08")
                .shippoAccountUpdateRequest(ShippoAccountUpdateRequest.builder()
                        .email("hippo@shippo.com")
                        .firstName("Shippo")
                        .lastName("Meister")
                        .companyName("Acme")
                        .build())
                .call();
        System.out.println(response);
    }

    @Test
    public void getShippoAccountTest() throws Exception {
        final var response = sdk.shippoAccounts().get()
                .shippoAccountId(shippoAccountObjectId)
                .shippoApiVersion("2018-02-08")
                .call();
        System.out.println(response);
    }

    @Test
    @Disabled
    public void updateShippoAccountTest() throws Exception {
        final var response = sdk.shippoAccounts().update()
                .shippoAccountId(shippoAccountObjectId)
                .shippoApiVersion("2018-02-08")
                .shippoAccountUpdateRequest(ShippoAccountUpdateRequest.builder()
                        .email("hippo@shippo.com")
                        .firstName("Shippo")
                        .lastName("Meister")
                        .companyName("Acme")
                        .build())
                .call();
        System.out.println(response);
    }

    @Test
    @Disabled
    public void createShipmentTest() throws Exception {
        final var response = sdk.shipments().create()
                .shippoApiVersion("2018-02-08")
                .shipmentCreateRequest(ShipmentCreateRequest.builder()
                        .addressFrom(AddressFrom.of(AddressCreateRequest.builder()
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
                                .build()))
                        .addressTo(AddressTo.of(addressObjectId))
                        .parcels(List.of(
                                Parcels.of(ParcelCreateFromTemplateRequest.builder()
                                        .massUnit(WeightUnitEnum.LB)
                                        .weight("1")
                                        .template(ParcelTemplateEnumSet.of(ParcelTemplateFedExEnum.FED_EX_BOX25KG))
                                        .extra(ParcelExtra.builder()
                                                .cod(Cod.builder()
                                                        .amount("5.5")
                                                        .currency("USD")
                                                        .paymentMethod(PaymentMethod.CASH)
                                                        .build())
                                                .insurance(ParcelInsurance.builder()
                                                        .amount("5.5")
                                                        .content("Laptop")
                                                        .currency("USD")
                                                        .provider(ParcelInsuranceProvider.UPS)
                                                        .build())
                                                .build())
                                        .metadata("Customer ID 123456")
                                        .build())))
                        .metadata("Customer ID 123456")
                        .shipmentDate("2021-03-22T12:00:00Z")
                        .addressReturn(AddressReturn.of(addressObjectId))
                        .customsDeclaration(ShipmentCreateRequestCustomsDeclaration.of("adcfdddf8ec64b84ad22772bce3ea37a"))
                        .carrierAccounts(List.of(
                                "065a4a8c10d24a34ab932163a1b87f52",
                                "73f706f4bdb94b54a337563840ce52b0"))
                        .build())
                .call();
        System.out.println(response);
    }

    @Test
    public void listShipmentsTest() throws Exception {
        final var req = ListShipmentsRequest.builder()
                .build();

        final var response = sdk.shipments().list()
                .request(req)
                .call();
        System.out.println(response);
    }
}
