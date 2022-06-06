package com.pozpl.nerannotator.ner.impl.results;

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
public class ResultsBLIOUExportController {

    private final IResultsBLIUOExportService resultsExportService;

    @Autowired
    public ResultsBLIOUExportController(IResultsBLIUOExportService resultsExportService) {
        this.resultsExportService = resultsExportService;
    }

    @GetMapping("/export/to/file")
    public void exportToFile(@RequestParam(name = "jobId") Integer jobId,
                             UserIntDto user,
                             HttpServletResponse response){
        try {
            response.addHeader("Content-disposition", "attachment;filename=annotated_text.csv");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            ServletOutputStream outputStream = response.getOutputStream();
            this.resultsExportService.writeOwnerResultsToStream(jobId, user, outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (NerServiceException | IOException e) {
            throw new NerWebException(e);
        }
    }
}
