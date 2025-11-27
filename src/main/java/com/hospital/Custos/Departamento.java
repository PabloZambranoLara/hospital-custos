package com.hospital.Custos;

import java.util.ArrayList;
import java.util.List;

public class Departamento {

    private String nome;
    private List<GastoHospitalar> gastos;

    public Departamento() {
        this("Sem Nome"); // chamando o outro construtor
    }

    public Departamento(String nome) {
        this.nome = nome;
        this.gastos = new ArrayList<>();
    }

    public void adicionarGasto(GastoHospitalar gasto) {
        gastos.add(gasto);
    }

    public double totalGastos() {
        return gastos.stream()
                .mapToDouble(GastoHospitalar::getValor)
                .sum();
    }

    public double totalGastos(boolean incluirImpostos) {
        if (!incluirImpostos) {
            return totalGastos();
        }

        double total = 0;
        for (GastoHospitalar g : gastos) {
            total += g.calcularImposto();
        }

        return total;
    }

    @Override
    public String toString() {
        return "\nDepartamento: " + nome +
                "\nGastos registrados: " + gastos.size() +
                "\nTotal sem impostos: R$ " + totalGastos() +
                "\nTotal com impostos: R$ " + totalGastos(true);
    }

    public String getNome() { return nome; }
    public List<GastoHospitalar> getGastos() { return gastos; }
}
