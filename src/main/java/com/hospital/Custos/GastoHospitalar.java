package com.hospital.Custos;

import java.util.Objects;

public class GastoHospitalar extends Entidade implements Persistable {

    private String descricao;
    private CategoriaGasto categoria;
    private double valor;
    private boolean necessitaAprovacao;


    public static final double FATOR_IMPOSTO_INTERNACAO = 1.05;
    public static final double FATOR_IMPOSTO_MATERIAL   = 1.02;
    public static final double FATOR_IMPOSTO_PROCESSO   = 1.03;
    public static final double FATOR_IMPOSTO_SERVICO    = 1.04;

    public GastoHospitalar() {}

    public GastoHospitalar(int id, String descricao, CategoriaGasto categoria, double valor, boolean necessitaAprovacao) {
        super(id);
        setDescricao(descricao);
        setCategoria(categoria);
        setValor(valor);
        this.necessitaAprovacao = necessitaAprovacao;
    }

    public void processarGasto() {
        System.out.println("\nProcessando gasto hospitalar...");

        if (valor > 500 && categoria == CategoriaGasto.MATERIAL) {
            System.out.println("Verificação extra aplicada: MATERIAL acima de R$ 500.");
        }

        if (valor < 50 || !necessitaAprovacao) {
            System.out.println("ℹ Gasto pequeno ou que não requer aprovação.");
        }

        System.out.println("Valor original: R$ " + valor);
        System.out.println("Valor com imposto aplicado: R$ " + calcularImposto());
    }

    public double calcularImposto() {
        if (categoria == null) return valor;

        return switch (categoria) {
            case INTERNACAO -> valor * FATOR_IMPOSTO_INTERNACAO;
            case MATERIAL   -> valor * FATOR_IMPOSTO_MATERIAL;
            case PROCESSO   -> valor * FATOR_IMPOSTO_PROCESSO;
            case SERVICO    -> valor * FATOR_IMPOSTO_SERVICO;
        };
    }

    public void resumoGastos() {
        System.out.println("\nResumo do gasto hospitalar:");
        System.out.println(this);
    }


    public void resumoGastos(boolean detalhado) {
        if (!detalhado) {
            resumoGastos();
            return;
        }
        System.out.println("\nResumo detalhado do gasto:");
        System.out.println("ID: " + getId());
        System.out.println("Descrição: " + descricao);
        System.out.println("Categoria: " + categoria);
        System.out.println("Valor: R$ " + valor);
        System.out.println("Necessita aprovação: " + (necessitaAprovacao ? "Sim" : "Não"));
        System.out.println("Valor com imposto: R$ " + calcularImposto());
    }

    @Override
    public String toString() {
        return "ID=" + getId() +
                " | Descrição='" + descricao + '\'' +
                " | Categoria=" + categoria +
                " | Valor=R$ " + valor +
                " | Aprovacao=" + (necessitaAprovacao ? "Sim" : "Não");
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new ValorInvalidoException("Descrição não pode ser vazia.");
        }
        this.descricao = descricao.trim();
    }

    public CategoriaGasto getCategoria() { return categoria; }
    public void setCategoria(CategoriaGasto categoria) { this.categoria = categoria; }

    public double getValor() { return valor; }
    public void setValor(double valor) {
        if (valor < 0) throw new ValorInvalidoException("Valor do gasto não pode ser negativo: " + valor);
        this.valor = valor;
    }

    public boolean isNecessitaAprovacao() { return necessitaAprovacao; }
    public void setNecessitaAprovacao(boolean necessitaAprovacao) { this.necessitaAprovacao = necessitaAprovacao; }

    @Override
    public String toRecord() {
        String safeDescricao = descricao == null ? "" : descricao.replace(";", ",");
        return String.format("%d;%s;%s;%.2f;%b",
                getId(),
                safeDescricao,
                categoria == null ? "" : categoria.name(),
                valor,
                necessitaAprovacao);
    }

    public static GastoHospitalar fromRecord(String recordLine) {

        if (recordLine == null || recordLine.trim().isEmpty()) return null;
        String[] parts = recordLine.split(";", -1);
        if (parts.length < 5) return null;

        try {
            int id = Integer.parseInt(parts[0].trim());
            String descricao = parts[1].trim();
            CategoriaGasto categoria = parts[2].trim().isEmpty() ? null : CategoriaGasto.valueOf(parts[2].trim());
            double valor = Double.parseDouble(parts[3].trim().replace(",", "."));
            boolean necessita = Boolean.parseBoolean(parts[4].trim());

            return new GastoHospitalar(id, descricao, categoria, valor, necessita);
        } catch (Exception e) {
            return null;
        }
    }
}
