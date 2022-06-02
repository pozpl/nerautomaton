package com.pozpl.nerannotator.ner.results.spacy;

import auth.dao.model.User;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

import java.io.OutputStream;

public interface ISpacyFormatNerAnnotatedExportService {

    void writeOwnerResultsToStream(Integer jobId,
                                   User user,
                                   OutputStream stream) throws NerServiceException;

}
