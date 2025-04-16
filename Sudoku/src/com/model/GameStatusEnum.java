package com.model;

public enum GameStatusEnum {
    NON_STARTED("Não iniciado!"),
    INCOMPLETE("Jogo incolmpleto!"),
    COMPLETE("Jogo completo!");

    private String label;

    GameStatusEnum(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
