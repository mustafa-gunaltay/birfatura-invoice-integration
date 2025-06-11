package tr.edu.ogu.birfaturainvoiceintegration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentApiRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentUserRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.ApiResponse;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.SendDocumentResult;
import tr.edu.ogu.birfaturainvoiceintegration.service.SendDocumentService;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class SendDocumentController {

    private final SendDocumentService sendDocumentService;

    @PostMapping("/send-direct")
    public ResponseEntity<ApiResponse<SendDocumentResult>> sendDocument(@RequestBody SendDocumentApiRequest request) {

        ApiResponse<SendDocumentResult> response = sendDocumentService.sendDocument(request);

        return ResponseEntity
                .status(response.isSuccess() ? 200 : 400)
                .body(response);
    }


    @PostMapping("/send-document")
    public ResponseEntity<ApiResponse<SendDocumentResult>> sendDocument( @RequestBody SendDocumentUserRequest request) {

        ApiResponse<SendDocumentResult> response = sendDocumentService.sendDocument(request);

        return ResponseEntity
                .status(response.isSuccess() ? 200 : 400)
                .body(response);
    }

}

