package com.hospital.Custos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class HospitalCustosApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalCustosApplication.class, args);

        Scanner scanner = new Scanner(System.in);

        GastoHospitalar gasto = new GastoHospitalar();

        System.out.print("Digite o ID do gasto: ");
        gasto.setId(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Digite a descrição do gasto: ");
        gasto.setDescricao(scanner.nextLine());

        System.out.print("Digite a categoria (Internação, Material, Processo): ");
        gasto.setCategoria(scanner.nextLine());


        System.out.print("Digite o valor do gasto: R$ ");
        gasto.setValor(scanner.nextDouble());
        scanner.nextLine();


        System.out.print("O gasto necessita aprovação? (true/false): ");
        gasto.setNecessitaAprovacao(scanner.nextBoolean());

        gasto.processarGasto();
        gasto.resumoGastos();

        scanner.close();


	}

}
