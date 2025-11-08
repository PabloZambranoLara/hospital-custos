package com.hospital.Custos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class HospitalCustosApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalCustosApplication.class, args);

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        List<GastoHospitalar> gastosDiretoria = new ArrayList<>();
        List<GastoHospitalar> gastosFinanceiro = new ArrayList<>();
        List<GastoHospitalar> gastosAutomaticos = new ArrayList<>();

        do {
            System.out.println("\n==== SISTEMA DE GASTOS HOSPITALARES ====");
            System.out.println("1 - Cadastrar novo gasto");
            System.out.println("2 - Revisar gastos e relatórios");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    GastoHospitalar gasto = new GastoHospitalar();

                    System.out.print("\nDigite o ID do gasto: ");
                    gasto.setId(scanner.nextInt());
                    scanner.nextLine();

                    System.out.print("Digite a descrição do gasto: ");
                    gasto.setDescricao(scanner.nextLine());

                    System.out.print("Digite a categoria (Internação, Material, Processo, Serviço): ");
                    gasto.setCategoria(scanner.nextLine());

                    System.out.print("Digite o valor do gasto: R$ ");
                    gasto.setValor(scanner.nextDouble());
                    scanner.nextLine();

                    System.out.print("O gasto necessita aprovação? (true/false): ");
                    gasto.setNecessitaAprovacao(scanner.nextBoolean());

                    gasto.processarGasto();
                    gasto.resumoGastos();

                    // Classificação de acordo com o valor
                    if (gasto.getValor() > 10000) {
                        System.out.println("⚠ Aprovado pela DIRETORIA.");
                        gastosDiretoria.add(gasto);
                    } else if (gasto.getValor() >= 5000) {
                        System.out.println("🟠 Aprovado pelo GERENTE FINANCEIRO.");
                        gastosFinanceiro.add(gasto);
                    } else {
                        System.out.println("🟢 Gasto pequeno, aprovado automaticamente.");
                        gastosAutomaticos.add(gasto);
                    }
                    break;

                case 2:
                    int totalGastos = gastosDiretoria.size() + gastosFinanceiro.size() + gastosAutomaticos.size();
                    if (totalGastos == 0) {
                        System.out.println("\nNenhum gasto cadastrado ainda.");
                        break;
                    }

                    double totalValorDiretoria = gastosDiretoria.stream().mapToDouble(GastoHospitalar::getValor).sum();
                    double totalValorFinanceiro = gastosFinanceiro.stream().mapToDouble(GastoHospitalar::getValor).sum();
                    double totalValorAutomatico = gastosAutomaticos.stream().mapToDouble(GastoHospitalar::getValor).sum();
                    double totalGeral = totalValorDiretoria + totalValorFinanceiro + totalValorAutomatico;

                    System.out.println("\n==== RELATÓRIO DE GASTOS ====");
                    System.out.printf("Total de gastos cadastrados: %d%n", totalGastos);
                    System.out.printf("Valor total geral: R$ %.2f%n", totalGeral);

                    System.out.printf("\nDiretoria: %d gastos (%.2f%%) | Total: R$ %.2f%n",
                            gastosDiretoria.size(),
                            (gastosDiretoria.size() * 100.0 / totalGastos),
                            totalValorDiretoria);

                    System.out.printf("Financeiro: %d gastos (%.2f%%) | Total: R$ %.2f%n",
                            gastosFinanceiro.size(),
                            (gastosFinanceiro.size() * 100.0 / totalGastos),
                            totalValorFinanceiro);

                    System.out.printf("Aprovados automaticamente: %d gastos (%.2f%%) | Total: R$ %.2f%n",
                            gastosAutomaticos.size(),
                            (gastosAutomaticos.size() * 100.0 / totalGastos),
                            totalValorAutomatico);

                    System.out.println("\n--- Gastos aprovados pela DIRETORIA ---");
                    if (gastosDiretoria.isEmpty()) {
                        System.out.println("Nenhum gasto nesta categoria.");
                    } else {
                        for (GastoHospitalar g : gastosDiretoria) {
                            System.out.printf("ID: %d | Descrição: %s | Categoria: %s | Valor: R$ %.2f%n",
                                    g.getId(), g.getDescricao(), g.getCategoria(), g.getValor());
                        }
                    }

                    System.out.println("\n--- Gastos aprovados pelo FINANCEIRO ---");
                    if (gastosFinanceiro.isEmpty()) {
                        System.out.println("Nenhum gasto nesta categoria.");
                    } else {
                        for (GastoHospitalar g : gastosFinanceiro) {
                            System.out.printf("ID: %d | Descrição: %s | Categoria: %s | Valor: R$ %.2f%n",
                                    g.getId(), g.getDescricao(), g.getCategoria(), g.getValor());
                        }
                    }

                    System.out.println("\n--- Gastos aprovados AUTOMATICAMENTE ---");
                    if (gastosAutomaticos.isEmpty()) {
                        System.out.println("Nenhum gasto nesta categoria.");
                    } else {
                        for (GastoHospitalar g : gastosAutomaticos) {
                            System.out.printf("ID: %d | Descrição: %s | Categoria: %s | Valor: R$ %.2f%n",
                                    g.getId(), g.getDescricao(), g.getCategoria(), g.getValor());
                        }
                    }

                    break;

                case 3:
                    continuar = false;
                    System.out.println("\nEncerrando o sistema...");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }

        } while (continuar);

        scanner.close();
    }
}
