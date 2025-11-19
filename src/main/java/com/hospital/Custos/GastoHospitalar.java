package com.hospital.Custos;

public class GastoHospitalar {

    private int id;
    private String descricao;
    private CategoriaGasto categoria;
    private double valor;
    private boolean necessitaAprovacao;

    public GastoHospitalar() {}

    public GastoHospitalar(int id, String descricao, CategoriaGasto categoria, double valor, boolean necessitaAprovacao){
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.necessitaAprovacao = necessitaAprovacao;
    }

    // Processa o gasto aplicando o imposto
    public void processarGasto() {
        System.out.println("\nProcessando gasto hospitalar...");
        System.out.println("Valor original: R$ " + valor);
        System.out.println("Valor com imposto aplicado: R$ " + calcularImposto());
    }

    private double calcularImposto() {
        if (categoria == null) return valor;
        return switch (categoria) {
            case INTERNACAO -> valor * 1.05;
            case MATERIAL -> valor * 1.02;
            case PROCESSO -> valor * 1.03;
            case SERVICO -> valor * 1.04;
        };
    }

    // Resumo detalhado do gasto
    public void resumoGastos() {
        System.out.println("\nResumo do gasto hospitalar:");
        System.out.println("ID: " + id);
        System.out.println("Descrição: " + descricao);
        System.out.println("Categoria: " + categoria);
        System.out.println("Valor: R$ " + valor);
        System.out.println("Necessita aprovação: " + (necessitaAprovacao ? "Sim" : "Não"));
        System.out.println("Valor com imposto: R$ " + calcularImposto());
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public CategoriaGasto getCategoria() { return categoria; }
    public void setCategoria(CategoriaGasto categoria) { this.categoria = categoria; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public boolean isNecessitaAprovacao() { return necessitaAprovacao; }
    public void setNecessitaAprovacao(boolean necessitaAprovacao) { this.necessitaAprovacao = necessitaAprovacao; }
}