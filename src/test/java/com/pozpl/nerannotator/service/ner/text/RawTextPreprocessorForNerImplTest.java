package com.pozpl.nerannotator.service.ner.text;

import com.pozpl.nerannotator.ner.impl.annotation.textprocess.RawTextPreprocessorForNerImpl;
import com.pozpl.nerannotator.ner.impl.annotation.textprocess.SpecialTextMark;
import com.pozpl.nerannotator.ner.impl.annotation.textprocess.TaggedTermDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RawTextPreprocessorForNerImplTest {

	private RawTextPreprocessorForNerImpl preprocessorForNer;

	@BeforeEach
	public void setUp() throws Exception {
		preprocessorForNer = new RawTextPreprocessorForNerImpl();

	}

	@Test
	public void process() throws Exception{

		final List<TaggedTermDto> tokensForAnn = this.preprocessorForNer.process("This is the last day, and I went home.");
		assertNotNull(tokensForAnn);
		assertEquals(12, tokensForAnn.size());
		assertEquals("This", tokensForAnn.get(0).getToken());
		assertEquals(SpecialTextMark.SENTENCE_END, tokensForAnn.get(11).getSpecialTextMark() );
	}
}