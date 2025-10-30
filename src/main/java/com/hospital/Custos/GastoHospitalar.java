package com.hospital.Custos;

public class GastoHospitalar {

    private int id;
    private String descricao;
    private String categoria; // Internação, Material, Processo
    private double valor;
    private boolean necessitaAprovacao;

    public GastoHospitalar() {}

    public GastoHospitalar(int id, String descricao, String categoria, double valor, boolean necessitaAprovacao){
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.necessitaAprovacao = necessitaAprovacao;
    }


    public void processarGasto() {
        double valorComImposto = calcularImposto();
        System.out.println("\nProcessando gasto hospitalar...");
        System.out.println("Valor original: R$ " + valor);
        System.out.println("Valor com imposto aplicado: R$ " + valorComImposto);
    }


    private double calcularImposto() {

        switch (categoria.toLowerCase()) {
            case "internação":
                return valor * 1.05; // 5% de imposto
            case "material":
                return valor * 1.02; // 2% de imposto
            case "processo":
                return valor * 1.03; // 3% de imposto
            default:
                return valor;
        }
    }

    public void resumoGastos (){
        System.out.println("\nResumo do gasto hospitalar:");
        System.out.println("ID: " + id);
        System.out.println("Descrição: " + descricao);
        System.out.println("Categoria: " + categoria);
        System.out.println("Valor: R$ " + valor);
        System.out.println("Necessita aprovação: " + (necessitaAprovacao ? "Sim" : "Não"));
    }


    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public double getValor() { return valor; }
    public boolean isNecessitaAprovacao() { return necessitaAprovacao; }

    public void setId(int id) { this.id = id; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setValor(double valor) { this.valor = valor; }
    public void setNecessitaAprovacao(boolean necessitaAprovacao) { this.necessitaAprovacao = necessitaAprovacao; }
}
