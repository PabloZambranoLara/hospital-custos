package com.hospital.Custos;

import java.util.ArrayList;
import java.util.List;

public class Departamento {

    private String nome;
    private List<GastoHospitalar> gastos;

    public Departamento() {
        this.gastos = new ArrayList<>();
    }

    public Departamento(String nome) {
        this.nome = nome;
        this.gastos = new ArrayList<>();
    }

    public void adicionarGasto(GastoHospitalar gasto) {
        gastos.add(gasto);
    }

    public double totalGastos() {
        return gastos.stream().mapToDouble(GastoHospitalar::getValor).sum();
    }

    @Override
    public String toString() {
        return "Departamento: " + nome +
                "\nTotal de Gastos: R$ " + totalGastos() +
                "\nQuantidade de Gastos: " + gastos.size() + "\n";
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<GastoHospitalar> getGastos() { return gastos; }
}