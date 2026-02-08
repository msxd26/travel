package pe.jsaire.springtravel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.springtravel.services.abstract_service.IReportService;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final IReportService reportService;

    private final MediaType FORCE_DOWNLOAD = new MediaType("application", "force-download");
    private final String FORCE_DOWNLOAD_HEADER = "attachment; filename=report.xlsx";

    @GetMapping
    public ResponseEntity<Resource> get() {
        var headers = new HttpHeaders();
        headers.setContentType(FORCE_DOWNLOAD);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, FORCE_DOWNLOAD_HEADER);

        var fileInByte = this.reportService.getReport();
        ByteArrayResource resource = new ByteArrayResource(fileInByte);
        return ResponseEntity.ok().headers(headers)
                .contentLength(fileInByte.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
