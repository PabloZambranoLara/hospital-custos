package com.hospital.Custos;

import java.util.Scanner;

public class HospitalCustosApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        Departamento diretoria = new Departamento("Diretoria");
        Departamento financeiro = new Departamento("Financeiro");
        Departamento automaticos = new Departamento("Aprovados Automaticamente");

        while (continuar) {
            System.out.println("\n==== SISTEMA DE GASTOS HOSPITALARES ====");
            System.out.println("1 - Cadastrar novo gasto");
            System.out.println("2 - Revisar gastos e relatórios");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarGasto(scanner, diretoria, financeiro, automaticos);
                case 2 -> mostrarRelatorios(diretoria, financeiro, automaticos);
                case 3 -> {
                    continuar = false;
                    System.out.println("\nEncerrando o sistema...");
                }
                default -> System.out.println("\nOpção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void cadastrarGasto(Scanner scanner, Departamento diretoria, Departamento financeiro, Departamento automaticos) {
        GastoHospitalar gasto = new GastoHospitalar();

        System.out.print("\nDigite o ID do gasto: ");
        gasto.setId(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Digite a descrição do gasto: ");
        gasto.setDescricao(scanner.nextLine());

        System.out.print("Digite a categoria (INTERNACAO, MATERIAL, PROCESSO, SERVICO): ");
        String categoriaDigitada = scanner.nextLine().toUpperCase().trim();
        try {
            gasto.setCategoria(CategoriaGasto.valueOf(categoriaDigitada));
        } catch (IllegalArgumentException e) {
            System.out.println("\nCategoria inválida! Cadastro cancelado.");
            return;
        }

        System.out.print("Digite o valor do gasto: R$ ");
        gasto.setValor(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("O gasto necessita aprovação? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        gasto.setNecessitaAprovacao(resposta.equals("s"));

        gasto.processarGasto();
        gasto.resumoGastos();

        if (gasto.getValor() > 10000) {
            System.out.println("Aprovado pela DIRETORIA.");
            diretoria.adicionarGasto(gasto);
        } else if (gasto.getValor() >= 1000) {
            System.out.println("Aprovado pelo GERENTE FINANCEIRO.");
            financeiro.adicionarGasto(gasto);
        } else {
            System.out.println("Gasto pequeno, aprovado automaticamente.");
            automaticos.adicionarGasto(gasto);
        }
    }

    private static void mostrarRelatorios(Departamento diretoria, Departamento financeiro, Departamento automaticos) {
        int totalGastos = diretoria.getGastos().size() + financeiro.getGastos().size() + automaticos.getGastos().size();
        if (totalGastos == 0) {
            System.out.println("\nNenhum gasto cadastrado ainda.");
            return;
        }

        System.out.println("\n==== RELATÓRIO DE GASTOS ====");
        System.out.println(diretoria);
        System.out.println(financeiro);
        System.out.println(automaticos);
    }
}