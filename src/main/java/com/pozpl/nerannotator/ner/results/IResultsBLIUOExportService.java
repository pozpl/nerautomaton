package com.pozpl.nerannotator.ner.results;

import auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

import java.io.OutputStream;

public interface IResultsBLIUOExportService {

    void writeOwnerResultsToStream(Integer jobId,
                                   User user,
                                   OutputStream stream) throws NerServiceException;
}
