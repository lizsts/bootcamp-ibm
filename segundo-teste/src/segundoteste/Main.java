package segundoteste;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Segundo sistemaRH = new Segundo();

        Scanner scanner = new Scanner(System.in);

        boolean executando = true;

        while (executando) {

            exibirMenu();

            String escolha = scanner.nextLine();


            switch (escolha) {

                case "1":

                    System.out.println("Digite o nome do candidato:");

                    String nomeCandidato = scanner.nextLine();

                    try {

                        int idCandidato = sistemaRH.iniciarProcesso(nomeCandidato);

                        System.out.println("Candidato registrado com sucesso. ID: " + idCandidato);

                    } catch (Exception e) {

                        System.out.println("Erro: " + e.getMessage());

                    }

                    break;

                case "2":

                    System.out.println("Digite o ID do candidato:");

                    int idMarcarEntrevista = Integer.parseInt(scanner.nextLine());

                    try {
                        sistemaRH.marcarEntrevista(idMarcarEntrevista);

                        System.out.println("Entrevista marcada para o candidato.");

                    } catch (Exception e) {

                        System.out.println("Erro: " + e.getMessage());

                    }

                    break;

                case "3":

                    System.out.println("Digite o ID do candidato:");

                    int idDesqualificar = Integer.parseInt(scanner.nextLine());

                    try {
                        sistemaRH.desqualificarCandidato(idDesqualificar);

                        System.out.println("Candidato desqualificado com sucesso.");

                    } catch (Exception e) {

                        System.out.println("Erro: " + e.getMessage());

                    }

                    break;

                case "4":

                    System.out.println("Digite o ID do candidato:");

                    int idVerificarStatus = Integer.parseInt(scanner.nextLine());

                    try {

                        String statusCandidato = sistemaRH.verificarStatusCandidato(idVerificarStatus);

                        System.out.println("Status do candidato: " + statusCandidato);

                    } catch (Exception e) {

                        System.out.println("Erro: " + e.getMessage());

                    }

                    break;

                case "5":

                    System.out.println("Digite o ID do candidato:");

                    int idAprovarCandidato = Integer.parseInt(scanner.nextLine());

                    try {

                        sistemaRH.aprovarCandidato(idAprovarCandidato);

                        System.out.println("Candidato aprovado com sucesso.");

                    } catch (Exception e) {

                        System.out.println("Erro: " + e.getMessage());

                    }

                    break;

                case "6":

                    System.out.println("Lista de candidatos aprovados:");

                    sistemaRH.obterAprovados().forEach(System.out::println);

                    break;

                case "0":

                    executando = false;

                    System.out.println("Saindo do sistema...");

                    break;

                default:

                    System.out.println("Opção inválida. Tente novamente.");

                    break;

            }

        }

        scanner.close();

    }

    private static void exibirMenu() {

        System.out.println("==== Sistema de RH - Controle de Candidatos ====");

        System.out.println("Selecione uma opção:");

        System.out.println("1. Registrar candidato");

        System.out.println("2. Marcar entrevista para candidato");

        System.out.println("3. Desqualificar candidato");

        System.out.println("4. Verificar status do candidato");

        System.out.println("5. Aprovar candidato");

        System.out.println("6. Listar candidatos aprovados");

        System.out.println("0. Sair do sistema");

        System.out.print("Opção: ");

    }


}