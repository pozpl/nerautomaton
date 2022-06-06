package com.pozpl.nerannotator.ner.impl.results.spacy;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/ner/results")
public class SpacySimpleTrainFormatExportController {

    public final ISpacyFormatNerAnnotatedExportService exportService;

    @Autowired
    public SpacySimpleTrainFormatExportController(ISpacyFormatNerAnnotatedExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/export/to/spacy/compatible/json/file")
    public void exportToFile(@RequestParam(name = "jobId") Integer jobId,
                             UserIntDto user,
                             HttpServletResponse response){
        try {
            response.addHeader("Content-disposition", "attachment;filename=annotated_text.csv");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            ServletOutputStream outputStream = response.getOutputStream();
            this.exportService.writeOwnerResultsToStream(jobId, user, outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (NerServiceException | IOException e) {
            throw new NerWebException(e);
        }
    }
}
