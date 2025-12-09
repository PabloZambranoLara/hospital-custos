package com.hospital.Custos;

import java.util.*;

public class HospitalCustosApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<CategoriaGasto, Departamento> departamentos = new EnumMap<>(CategoriaGasto.class);

        List<GastoHospitalar> gastosCarregados = GastoRepository.carregar();
        for (GastoHospitalar g : gastosCarregados) {
            CategoriaGasto cat = g.getCategoria();
            if (cat == null) {

                departamentos
                        .computeIfAbsent(CategoriaGasto.SERVICO, k -> new Departamento("Sem Categoria"))
                        .adicionarGasto(g);
            } else {
                departamentos
                        .computeIfAbsent(cat, k -> new Departamento(k.name()))
                        .adicionarGasto(g);
            }
        }

        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE CUSTOS HOSPITALARES ===");
            System.out.println("1 - Cadastrar novo gasto");
            System.out.println("2 - Revisar gastos e relatórios");
            System.out.println("3 - Listar todos os gastos do arquivo");
            System.out.println("4 - Reescrever arquivo com estado atual (salvarTodos)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String linha = scanner.nextLine().trim();
            if (linha.isEmpty()) {
                System.out.println("Opção inválida.");
                continue;
            }
            try {
                opcao = Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    try {
                        System.out.print("\nDigite o ID do gasto (inteiro): ");
                        int id = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Digite a descrição do gasto: ");
                        String descricao = scanner.nextLine().trim();

                        System.out.print("Digite a categoria (INTERNACAO, MATERIAL, PROCESSO, SERVICO): ");
                        String categoriaDigitada = scanner.nextLine().trim().toUpperCase();
                        CategoriaGasto categoria;
                        try {
                            categoria = CategoriaGasto.valueOf(categoriaDigitada);
                        } catch (IllegalArgumentException iae) {
                            System.out.println("Categoria inválida! Cadastro cancelado.");
                            break;
                        }

                        System.out.print("Digite o valor do gasto (ex: 1500.50): R$ ");
                        String valorStr = scanner.nextLine().trim().replace(",", ".");
                        double valor;
                        try {
                            valor = Double.parseDouble(valorStr);
                        } catch (NumberFormatException nfe) {
                            System.out.println("Valor inválido! Cadastro cancelado.");
                            break;
                        }

                        String resposta;
                        do {
                            System.out.print("O gasto necessita aprovação? (s/n): ");
                            resposta = scanner.nextLine().trim().toLowerCase();
                        } while (!resposta.equals("s") && !resposta.equals("n"));

                        boolean necessita = resposta.equals("s");

                        GastoHospitalar gasto = new GastoHospitalar(id, descricao, categoria, valor, necessita);

                        // Processa e exibe resumo
                        gasto.processarGasto();
                        gasto.resumoGastos();

                        GastoRepository.salvar(gasto);

                        // Adiciona ao departamento correspondente
                        departamentos
                                .computeIfAbsent(categoria, k -> new Departamento(k.name()))
                                .adicionarGasto(gasto);

                        System.out.println("\nGasto salvo no arquivo com sucesso!");
                    } catch (ValorInvalidoException vie) {
                        System.out.println("Erro de validação: " + vie.getMessage());
                    } catch (Exception e) {
                        System.out.println("Erro inesperado: " + e.getMessage());
                    }
                }

                case 2 -> {
                    if (departamentos.isEmpty()) {
                        System.out.println("\nNenhum gasto registrado ainda.");
                    } else {
                        System.out.println("\n==== RELATÓRIO DE GASTOS POR DEPARTAMENTO ====");
                        for (Map.Entry<CategoriaGasto, Departamento> entry : departamentos.entrySet()) {
                            Departamento dep = entry.getValue();
                            System.out.println(dep);

                            List<GastoHospitalar> lista = dep.getGastos();
                            for (int i = 0; i < lista.size() && i < 3; i++) {
                                GastoHospitalar g = lista.get(i);
                                if (g.getValor() < 10) continue;
                                System.out.println(" - " + g.getDescricao() + " | R$ " + g.getValor());
                            }
                        }
                    }
                }

                case 3 -> {
                    System.out.println("\n=== LISTA DE GASTOS DO ARQUIVO ===");
                    List<GastoHospitalar> gastosArquivo = GastoRepository.carregar();
                    if (gastosArquivo.isEmpty()) {
                        System.out.println("Arquivo vazio ou não existe.");
                    } else {
                        gastosArquivo.forEach(System.out::println);
                    }
                }

                case 4 -> {
                    List<GastoHospitalar> todos = new ArrayList<>();
                    for (Departamento d : departamentos.values()) {
                        todos.addAll(d.getGastos());
                    }
                    GastoRepository.salvarTodos(todos);
                    System.out.println("Arquivo reescrito com sucesso!");
                }

                case 0 -> System.out.println("Encerrando o sistema...");

                default -> System.out.println("Opção inválida! Tente novamente.");
            }

        } while (true);
    }
}