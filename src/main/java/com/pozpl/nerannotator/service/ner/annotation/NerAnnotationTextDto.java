package com.pozpl.nerannotator.service.ner.annotation;

import com.pozpl.nerannotator.service.ner.text.TaggedTermDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NerAnnotationTextDto {
	private Integer id;
	private List<TaggedTermDto> tokens;
}
