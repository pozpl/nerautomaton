package com.pozpl.nerannotator.ner.annotation.textprocess;

/**
 * BEGIN	The first token of a multi-token entity.
 * IN	An inner token of a multi-token entity.
 * LAST	The final token of a multi-token entity.
 * UNIT	A single-token entity.
 * OUT	A non-entity token.
 */
public enum BLIUOScheme {
	BEGIN,
	IN,
	LAST,
	UNIT,
	OUT,
}
