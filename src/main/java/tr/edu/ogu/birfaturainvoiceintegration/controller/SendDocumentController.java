package tr.edu.ogu.birfaturainvoiceintegration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentApiRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.SendDocumentUserRequest;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.ApiResponse;
import tr.edu.ogu.birfaturainvoiceintegration.dto.response.SendDocumentResult;
import tr.edu.ogu.birfaturainvoiceintegration.service.SendDocumentService;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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

        System.out.println(response);

        return ResponseEntity
                .status(response.isSuccess() ? 200 : 400)
                .body(response);
    }

}

