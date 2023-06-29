package com.mateus.batalha.naval;

import java.util.Scanner;

public class BatalhaNavalUI {
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        char modo = ' ';
        int[] tiro = new int[2];
        int resultado = 0;
        String ganhador = "";
        char alocacao = ' ';
        
        //verifica status do jogo
        boolean vez = true;
        boolean fim = false;
        boolean continua = false;
        boolean prox = false;
        
        // Criação das matrizes para o jogador 1 e jogador 2
        char[][] matriz1 = BatalhaNavalGame.geraMatriz();
        char[][] ataque1 = BatalhaNavalGame.geraMatriz(); 
        char[][] matriz2 = BatalhaNavalGame.geraMatriz(); 
        char[][] ataque2 = BatalhaNavalGame.geraMatriz(); 
        
        // Imprime a arte do jogo
        System.out.println("             __________");
        System.out.println("            |      ___/");
        System.out.println("            |  ___/");
        System.out.println("            | /");
        System.out.println("    ____ ___|/________");
        System.out.println("   \\  batalha naval /");
        System.out.println("    \\______________/");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("| [1] Modo Vs computador   |");
        System.out.println("| [2] Modo disputa         |");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        do {
            // Lê a opção de modo escolhida pelo jogador
            modo = ler.next().charAt(0);
            prox = BatalhaNavalGame.conferirOpcao(modo);
        } while (!prox); 

        if (modo == '1') { 
            matriz2 = BatalhaNavalGame.auto(matriz2);

            // Define a forma de alocação dos navios para o jogador 1
            alocacao = BatalhaNavalGame.alocacao(); 
            if (alocacao == '1') { 
                matriz1 = BatalhaNavalGame.auto(matriz1);
            }
            if (alocacao == '2') { 
                matriz1 = BatalhaNavalGame.manual(matriz1);
            }
        }

        if (modo == '2') { 
            // Define a forma de alocação dos navios para o jogador 1
            alocacao = BatalhaNavalGame.alocacao();
            if (alocacao == '1') { 
                matriz1 = BatalhaNavalGame.auto(matriz1);
            }
            if (alocacao == '2') {
                matriz1 = BatalhaNavalGame.manual(matriz1);
            }

            // Define a forma de alocação dos navios para o jogador 2
            alocacao = BatalhaNavalGame.alocacao();
            if (alocacao == '1') {
                matriz2 = BatalhaNavalGame.auto(matriz2);
            }
            if (alocacao == '2') {
                matriz2 = BatalhaNavalGame.manual(matriz2);
            }
        }

        do {
            if (vez) {
                System.out.println("\nVez do jogador 1:  ");

                do {
                    BatalhaNavalGame.mostrarTabuleiro(ataque2);

                    System.out.print("aonde deseja atirar capitão?: ");

                    do {
                        // Converte a posição do tiro para coordenadas na matriz
                        String pos = ler.next(); 
                        tiro = BatalhaNavalGame.converteLetra(pos); 
                        prox = BatalhaNavalGame.conferirTiro(tiro); 
                        if (!prox)
                            System.out.print("ops, digite novamente: ");
                    } while (!prox);

                    // Verifica o resultado do tiro
                    resultado = BatalhaNavalGame.shotatt(tiro, matriz2);
                    ataque2 = BatalhaNavalGame.atirar(tiro, ataque2, resultado);
                    matriz2 = BatalhaNavalGame.atirar(tiro, matriz2, resultado);

                    switch (resultado) {
                        case 1 -> {
                            System.out.println("\n BARCOOO!!!");
                            vez = true;
                        }
                        case 2 -> {
                            System.out.println("\n denovo ai não amigão, tenta denovo: ");
                            vez = true;
                        }
                        case 3 -> {
                            System.out.println("\n AGUA!!!");
                            vez = false;
                        }
                    }

                    // Verifica se todos os navios foram afundados
                    fim = BatalhaNavalGame.contar(ataque2); 
                    if (fim)
                        ganhador = "Jogador 1";
                } while (vez && !fim);
            } else { 
                System.out.println("\nVez do jogador 2");

                do {
                    if (modo == '1') { 
                        // Gera uma coordenada de tiro aleatória para o jogador 2
                        tiro = BatalhaNavalGame.gerarTiro(matriz1); 
                    }

                    if (modo == '2') { 
                        BatalhaNavalGame.mostrarTabuleiro(ataque1);
                        System.out.print("aonde deseja atirar capitão?");
                        do {
                            // Converte a posição do tiro para coordenadas na matriz
                            String pos = ler.next();
                            tiro = BatalhaNavalGame.converteLetra(pos);
                            prox = BatalhaNavalGame.conferirTiro(tiro);

                            if (!prox)
                                System.out.print("ops, digite novamente: ");
                        } while (!prox);
                    }

                    // Verifica o resultado do tiro
                    resultado = BatalhaNavalGame.shotatt(tiro, matriz1);
                    ataque1 = BatalhaNavalGame.atirar(tiro, ataque1, resultado);
                    matriz1 = BatalhaNavalGame.atirar(tiro, matriz1, resultado);

                    if (modo == '2')
                        BatalhaNavalGame.mostrarTabuleiro(ataque1);

                    switch (resultado) {
                        case 1 -> {
                            System.out.println("\n BARCOOO!");
                            vez = false;
                        }
                        case 2 -> {
                            System.out.println("\n no mesmo lugar denovo? tenta denovo:");
                            vez = false;
                        }
                        case 3 -> {
                            System.out.println("\n AGUA!!!");
                            vez = true;
                        }
                    }

                    // Verifica se todos os navios foram afundados
                    fim = BatalhaNavalGame.contar(ataque1);
                    if (fim)
                        ganhador = "Jogador 2";
                } while (!vez && !fim);
            }
        } while (!fim);

        System.out.println("\n" + ganhador + " ganhou o jogo!!!\n");
    }
}
