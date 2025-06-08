package tr.edu.ogu.birfaturainvoiceintegration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.ApiResponse;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.SendDocumentResult;
import tr.edu.ogu.birfaturainvoiceintegration.service.SendDocumentService;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class SendDocumentController {

    private final SendDocumentService sendDocumentService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<SendDocumentResult>> sendDocument(@RequestBody SendDocumentRequest request) {
        ApiResponse<SendDocumentResult> response = sendDocumentService.sendDocument(request);

//        System.out.println("Code: " + response.getCode());
//        System.out.println("Zipped: " + response.getResult().getZipped());
//        System.out.println("PDF link: " + response.getResult().getPdfLink());
        System.out.println(response);

        return ResponseEntity
                .status(response.isSuccess() ? 200 : 400)
                .body(response);
    }
}

