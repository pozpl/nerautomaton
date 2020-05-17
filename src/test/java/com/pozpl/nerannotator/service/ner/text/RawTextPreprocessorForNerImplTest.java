package com.pozpl.nerannotator.service.ner.text;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RawTextPreprocessorForNerImplTest {

	private RawTextPreprocessorForNerImpl preprocessorForNer;

	@Before
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