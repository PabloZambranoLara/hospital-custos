package com.hospital.Custos;

public abstract class Entidade {
    private int id;

    public Entidade() {}

    public Entidade(int id) {
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID precisa ser maior que zero.");
        }
        this.id = id;
    }
}
