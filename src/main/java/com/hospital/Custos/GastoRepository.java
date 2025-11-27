package com.hospital.Custos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GastoRepository {

    private static final String ARQUIVO = "gastos.txt";

    public static void salvar(GastoHospitalar gasto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            bw.write(gasto.toRecord());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar gasto: " + e.getMessage());
        }
    }

    public static List<GastoHospitalar> carregar() {
        List<GastoHospitalar> lista = new ArrayList<>();

        if (!Files.exists(Paths.get(ARQUIVO))) {
            return lista; // se o arquivo não existe ainda
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            int linhaNum = 0;

            while ((linha = br.readLine()) != null) {
                linhaNum++;

                GastoHospitalar g = GastoHospitalar.fromRecord(linha);

                if (g != null) {
                    lista.add(g);
                } else {
                    System.err.println("Linha inválida em '" + ARQUIVO + "' (ignorada): " +
                            linhaNum + " -> " + linha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro lendo arquivo: " + e.getMessage());
        }

        return lista;
    }

    public static void salvarTodos(List<GastoHospitalar> gastos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, false))) {
            for (GastoHospitalar g : gastos) {
                bw.write(g.toRecord());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao sobrescrever arquivo: " + e.getMessage());
        }
    }
}
