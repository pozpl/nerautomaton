package com.pozpl.nerannotator.service.ner.text;

import com.pozpl.nerannotator.service.exceptions.NerServiceException;

import java.util.List;

public interface INerAnnotatedTextParsingService {

	List<TaggedTermDto> parse(String text) throws NerServiceException;
}
