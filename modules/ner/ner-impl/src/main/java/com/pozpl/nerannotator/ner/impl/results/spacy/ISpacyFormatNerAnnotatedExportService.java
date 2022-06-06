package com.pozpl.nerannotator.ner.impl.results.spacy;

import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

import java.io.OutputStream;

public interface ISpacyFormatNerAnnotatedExportService {

    void writeOwnerResultsToStream(Integer jobId,
                                   UserIntDto user,
                                   OutputStream stream) throws NerServiceException;

}
