package com.pozpl.nerannotator.ner.annotation.textprocess;

import com.pozpl.nerannotator.shared.exceptions.NerServiceException;

import java.util.List;

public interface IRawTextPreprocessorForNer {

	List<TaggedTermDto> process(String text) throws NerServiceException;

}
