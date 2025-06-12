package tr.edu.ogu.birfaturainvoiceintegration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.web.client.RestClient;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentApiRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentUserRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.ApiResponse;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.SendDocumentResult;
import tr.edu.ogu.birfaturainvoiceintegration.util.FileUtil;
import tr.edu.ogu.birfaturainvoiceintegration.util.XmlUtil;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SendDocumentServiceUserRequestTest {

    private RestClient restClientMock;
    private SendDocumentService serviceSpy;

    @BeforeEach
    void setUp() {
        restClientMock = mock(RestClient.class);
        SendDocumentService realService = new SendDocumentService(restClientMock);
        serviceSpy = Mockito.spy(realService);
    }

    @Test
    void testSendDocument_withUserRequest_success() throws Exception {
        // Arrange
        SendDocumentUserRequest userRequest = new SendDocumentUserRequest();
        userRequest.setSupplierVkn("1234567890");
        userRequest.setSupplierName("Test Ltd.");
        userRequest.setCustomerTckn("11111111111");
        userRequest.setCustomerFirstName("Ali");
        userRequest.setCustomerLastName("Veli");
        userRequest.setItemName("Kalem");
        userRequest.setQuantity("1.000");
        userRequest.setUnitPrice("100.0");
        userRequest.setTaxPercent(18);
        userRequest.setCurrencyCode("TRY");
        userRequest.setInvoiceTypeCode("SATIS");

        File fakeXmlFile = new File("fake.xml");


        try (
                MockedStatic<XmlUtil> xmlUtilMock = mockStatic(XmlUtil.class);
                MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class)
        ) {
            xmlUtilMock
                    .when(() -> XmlUtil.createInvoiceXml(
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(String.class),
                            any(int.class),
                            any(String.class)
                    )).thenReturn(fakeXmlFile);

            fileUtilMock.when(() -> FileUtil.zipAndEncode(fakeXmlFile))
                    .thenReturn("base64zippedcontent");

            ApiResponse<SendDocumentResult> fakeResponse = new ApiResponse<>();
            fakeResponse.setSuccess(true);
            SendDocumentResult result = new SendDocumentResult();
            result.setInvoiceNo("ARS2024000000001");
            result.setZipped("base64zippedcontent");
            fakeResponse.setResult(result);

            doReturn(fakeResponse).when(serviceSpy).sendDocument(any(SendDocumentApiRequest.class));

            // Act
            ApiResponse<SendDocumentResult> response = serviceSpy.sendDocument(userRequest);

            // Assert
            assertTrue(response.isSuccess());
            assertEquals("ARS2024000000001", response.getResult().getInvoiceNo());
        }
    }
}
