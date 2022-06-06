package com.pozpl.nerannotator.ner.impl.annotation.textprocess;

import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

import java.util.List;

public interface INerAnnotatedTextParsingService {

	List<TaggedTermDto> parse(String text) throws NerServiceException;

	String serialise(List<TaggedTermDto> taggedTermDtos, List<String> availableLabels ) throws NerServiceException;
}
