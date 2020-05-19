package com.pozpl.nerannotator.ner.annotation.textprocess;

import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RawTextPreprocessorForNerImpl implements IRawTextPreprocessorForNer {


	@Override
	public List<TaggedTermDto> process(final String text) throws NerServiceException {

		final Document doc = new Document(text);

		final List<TaggedTermDto> taggedTermDtos = new ArrayList<>();

		final List<Sentence> sentences = doc.sentences();
		for (Sentence sentence : sentences) {
			final List<String> words = sentence.words();
			for (String word : words) {
				final TaggedTermDto tokenDto = TaggedTermDto.builder()
						.token(word)
						.build();
				taggedTermDtos.add(tokenDto);
			}

			final TaggedTermDto sentenceEndTerm = TaggedTermDto.builder().specialTextMark(SpecialTextMark.SENTENCE_END).build();
			taggedTermDtos.add(sentenceEndTerm);
		}

		return taggedTermDtos;
	}
}
