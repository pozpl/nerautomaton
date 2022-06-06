package com.pozpl.nerannotator.ner.impl.results.spacy;

import java.util.List;

public class SpacySimpleTrainingNerFormat {

    private String text;
    private List<Entity> entities;

    public SpacySimpleTrainingNerFormat(String text,
                                        List<Entity> entities) {
        this.text = text;
        this.entities = entities;
    }

    public String getText() {
        return text;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    static class Entity {
        private int begin;
        private int end;
        private String label;

        public Entity(int begin, int end, String label) {
            this.begin = begin;
            this.end = end;
            this.label = label;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }

        public String getLabel() {
            return label;
        }
    }
}