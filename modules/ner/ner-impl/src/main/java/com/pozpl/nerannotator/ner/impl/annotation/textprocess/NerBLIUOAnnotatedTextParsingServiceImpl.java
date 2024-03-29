package com.pozpl.nerannotator.ner.impl.annotation.textprocess;

import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NerBLIUOAnnotatedTextParsingServiceImpl implements INerAnnotatedTextParsingService {

	/**
	 * Parse serialised NER annotated text
	 * Format is as follows:
	 * token	BLIUO	Label
	 * <p>
	 * Example
	 * The	O
	 * C++	U	Skill
	 * Is	O
	 * Java	B	Skill
	 * Script	L	Skill
	 * rival	O
	 *
	 * @param text
	 * @return
	 * @throws NerServiceException
	 */
	@Override
	public List<TaggedTermDto> parse(String text) throws NerServiceException {

		final String[] lines = StringUtils.split(text, "\n");
		final List<TaggedTermDto> taggedTermDtos = new ArrayList<>();
		for (String line : lines) {
			final String[] tokenLabeling = StringUtils.split(line, "\t");
			if (tokenLabeling.length == 1 && StringUtils.equals(tokenLabeling[0], SpecialTextMark.SENTENCE_END.name())) {
				taggedTermDtos.add(TaggedTermDto.builder().specialTextMark(SpecialTextMark.SENTENCE_END).build());
			} else if (tokenLabeling.length == 2 && StringUtils.equals(tokenLabeling[1], BLIUOScheme.OUT.name())) {
				taggedTermDtos.add(TaggedTermDto.builder().token(tokenLabeling[0]).position(BLIUOScheme.OUT).build());
			} else if (tokenLabeling.length == 3) {
				final Optional<BLIUOScheme> labelMarkOpt = this.isKnownMarkForLabel(tokenLabeling[1]);
				labelMarkOpt.ifPresent(position -> taggedTermDtos.add(TaggedTermDto.builder()
						.token(tokenLabeling[0])
						.position(position)
						.label(tokenLabeling[2])
						.build()));
			}
		}
		return taggedTermDtos;
	}

	@Override
	public String serialise(final List<TaggedTermDto> taggedTermDtos,
							final List<String> availableLabels) throws NerServiceException {

		final Set<String> labelsSet = new HashSet<>();
		labelsSet.addAll(availableLabels);

		return io.vavr.collection.List.ofAll(taggedTermDtos)
				.map(term -> this.getSerialiseLabeledTerm(term, labelsSet))
				.foldLeft("", (acc, s2) -> {
					if (!acc.equals("")) {
						return acc + "\n" + s2;
					} else {
						return s2;
					}
				});
		
	}

	private String getSerialiseLabeledTerm(TaggedTermDto taggedTerm, final Set<String> availableLabels) {
		if(StringUtils.isBlank(taggedTerm.getToken())){    //special terms may not have tokens available
			return "";
		}
		if(taggedTerm.getLabel() == null || ! availableLabels.contains(taggedTerm.getLabel()) ){
			return taggedTerm.getToken() + "\t" + BLIUOScheme.OUT.name();
		}
		return taggedTerm.getToken() + "\t" + taggedTerm.getPosition() + "\t" + taggedTerm.getLabel();
	}

	public Optional<BLIUOScheme> isKnownMarkForLabel(String mark) {
		return Arrays.asList(BLIUOScheme.BEGIN, BLIUOScheme.IN, BLIUOScheme.LAST, BLIUOScheme.UNIT).stream()
				.filter(schemaToken -> schemaToken.name().equals(mark))
				.findFirst();
	}

}
