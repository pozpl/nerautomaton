package com.pozpl.nerannotator.ner.impl.annotation.edit;

import com.pozpl.nerannotator.ner.impl.annotation.textprocess.TaggedTermDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NerTextAnnotationDto {
	private Integer id;
	private List<TaggedTermDto> tokens;
	private String text;
}
