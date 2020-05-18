package com.pozpl.nerannotator.service.ner.text;

import com.pozpl.nerannotator.service.ner.annotation.BLIUOScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaggedTermDto {

	private String token;
	private String label;
	private BLIUOScheme position;
	private SpecialTextMark specialTextMark;

}
