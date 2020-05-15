package com.pozpl.nerannotator.service.ner.text;

import com.pozpl.nerannotator.service.exceptions.NerServiceException;
import com.pozpl.nerannotator.service.ner.annotation.NerAnnotationTextDto;

import java.util.List;

public interface IRawTextPreprocessorForNer {

	List<TaggedTermDto> process(String text) throws NerServiceException;

}
