package com.pozpl.nerannotator.service.ner.text;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTextEditStatusDto {

	private JobTextDto jobTextDto;

	private boolean error;

}
