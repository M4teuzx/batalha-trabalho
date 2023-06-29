package com.mateus.batalha.naval;

import java.util.Scanner;
import java.util.Random;

public class BatalhaNavalGame {

    
    //gerar uma matriz vazia
    public static char[][] geraMatriz() {
    char[][] matriz = new char[11][11];
    matriz[0] = new char[]{'*', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    for (int i = 1; i < matriz.length; i++) {
        matriz[i][0] = (char) ('0' + (i - 1));
        for (int j = 1; j < matriz[i].length; j++) {
            matriz[i][j] = ' ';
        }
    }
    return matriz;
}
    
     // Método para escolher o modo de alocação dos barcos (automático ou manual)
    public static char alocacao(){
      Scanner ler = new Scanner(System.in);
      System.out.println("\nEscolha o modo de alocação dos barcos: ");
      System.out.print("Automáticamente: digite 1 \n");
      System.out.print("Manualmente: digite 2:\n");
      boolean prox = false;
      char modo = ' ';
        
      do{
         modo = ler.next().charAt(0);
         prox = BatalhaNavalGame.conferirOpcao(modo);
     }while(!prox);
     return modo; 
   }
    
    //converter letra em numero
    public static int[] converteLetra(String shot){ 
        if(shot.length()!=2) 
            shot = "x"; 
        int[] nshot = {shot.toLowerCase().charAt(0)-97+1, shot.charAt(1)-48+1}; 

        return nshot;
    }
        
    //conferir se o tiro é valido
    public static boolean conferirTiro(int[] tiro) {
        if (tiro.length != 2) { 
            return false; 
        } else {
            int coluna = tiro[0];
            int linha = tiro[1];

            if (coluna >= 1 && coluna <= 10 && linha >= 1 && linha <= 10) { 
                return true; 
            }

            return false;
        }
    }
    //conferir se a opção é valida
    public static boolean conferirOpcao(char opcao){ 
        if(opcao=='1'||opcao=='2'){
            return true;
        }else{
            System.out.println("Insira uma opção válida!!!");
            return false; 
        }
    }
    
    //mostrar o tabuleiro
    public static void mostrarTabuleiro(char matriz[][]) {
        System.out.println(" +----+----+----+----+----+----+----+----+----+");
        for (char[] matriz1 : matriz) {
            for (int j = 0; j < matriz1.length; j++) {
                System.out.printf("| %c ", matriz1[j]);
            }
            System.out.print("|");
            System.out.println("\n +----+----+----+----+----+----+----+----+----+");
        }
}
    
    //conferir se a posição é valida
    public static boolean conferirPosicao(int tamanho, char matriz[][], int[] shot, boolean sentido){ 
        if(shot[0]<1||shot[0]>10||shot[1]<1||shot[1]>10){ 
            return false;
        }
        if(sentido){ 
            int fimBarco = shot[1]+tamanho-1; 
            if(fimBarco>10) { 
                return false; 
            }else{
                for(int i=shot[1];i<=fimBarco;i++){ 
                    if(matriz[i][shot[0]]=='B'){
                        return false;
                    }
                }
                return true;
            }   
        }else{ 
            int fimBarco = shot[0]+tamanho-1; 
            if(fimBarco>10) { 
                return false;
            }else{
                for(int j=shot[0];j<=fimBarco;j++){
                    if(matriz[shot[1]][j]=='B'){
                        return false;
                    }
                }
                return true;
            }   
        }      
    }
    
    
    //inserir um navio na matriz
    public static char[][] insereNavio(int tamanho, char matriz[][], int[] shot, boolean sentido){ 
        if(sentido){
            int fimBarco = shot[1]+tamanho-1;
            for(int i=shot[1];i<=fimBarco;i++){
                matriz[i][shot[0]] = 'B';
            }
        }else{ 
            int fimBarco = shot[0]+tamanho-1;
            for(int j=shot[0];j<=fimBarco;j++){ 
                matriz[shot[1]][j] = 'B';
            }
        }    
        return matriz;
    }
    
    //gerar cordenada de tiro aleatória 
        public static int[] gerar() {
            Random aleatorio = new Random();
            boolean proximo = false;
            int[] tiro = new int[2];

            do {
                tiro[0] = aleatorio.nextInt(10) + 1;
                tiro[1] = aleatorio.nextInt(10) + 1;

                proximo = conferirTiro(tiro);
            } while (!proximo);

            return tiro;
        }
    
        // Método para verificar o status de um tiro (acertou, errou ou repetiu)
    public static int shotatt(int[] shot, char[][] matriz){
        return switch (matriz[shot[1]][shot[0]]) {
            case 'B' -> 1;
            case 'X', 'O' -> 2;
            default -> 3;
        };
    }

    //Método para gerar uma coordenada de tiro aleatória, evitando tiros repetidos
    public static int[] gerarTiro(char matriz[][]) {
        Random aleatorio = new Random();
        boolean proximo = false;
        int[] tiro = new int[2];

        do {
            do {
                tiro[0] = aleatorio.nextInt(10) + 1;
                tiro[1] = aleatorio.nextInt(10) + 1;

                if (matriz[tiro[1]][tiro[0]] == 'X' || matriz[tiro[1]][tiro[0]] == 'A') {
                    proximo = false;
                } else {
                    proximo = true;
                }
            } while (!proximo);

            proximo = conferirTiro(tiro);
        } while (!proximo);

        return tiro;
    }

    //escolher orientação do barco
    public static boolean posicao(){
        Scanner ler = new Scanner(System.in);
        System.out.print("\ndeseja inserir o navio na:");
        System.out.print("[1] - Vertical[1]\n");
        System.out.print("[2] - Horizontal\n");
        boolean prox = false;
        char orientacao = 0;
        boolean orientacaoBoolean = false;
        do{
            orientacao = ler.next().charAt(0); 
            prox = BatalhaNavalGame.conferirOpcao(orientacao);
        }while(!prox);
        if(orientacao=='1')
            orientacaoBoolean = true;
        else
            orientacaoBoolean = false;
        return orientacaoBoolean;
    }
    
        public static char[][] autoinsertNavios(char[][] matriz, int num, int tam) {
            Random aleatorio = new Random();
            boolean prox = false;
            int barcosInseridos = 0;

            while (barcosInseridos < num) {
                int[] shot = gerar();
                boolean orientacao = aleatorio.nextBoolean();
                if (conferirPosicao(tam, matriz, shot, orientacao)) {
                    matriz = insereNavio(tam, matriz, shot, orientacao);
                    barcosInseridos++;
                }
            }

            return matriz;
        }
    
        
    // Método para atualizar a matriz após um tiro
    public static char[][] atirar(int[] shot, char[][] matriz, int shotatt){
        if(shotatt==1){
            matriz[shot[1]][shot[0]] = 'X';        
        }else if(shotatt==3){
            matriz[shot[1]][shot[0]] = 'O'; 
        }
        return matriz;
    }
    
    
    // Método para inserir os navios manualmente na matriz
    public static char[][] manualinsertNavios(int tam, char[][] matriz){ 
        Scanner ler = new Scanner(System.in);
        int[] shot = new int[2];
        boolean orientacao = false;
        boolean prox = false;
        do{
            switch(tam){
                case 1 -> System.out.print("\nInsira a coordenada para um navio(1): ");
                case 2 -> System.out.print("\nInsira a coordenada para um navio(2): ");
                case 3 -> System.out.print("\nInsira a coordenada para um navio(3): ");
                case 4 -> System.out.print("\nInsira a coordenada para um navio(4): ");
            }
            String pos = ler.next();
            shot = BatalhaNavalGame.converteLetra(pos);
            prox = BatalhaNavalGame.conferirPosicao(tam, matriz, shot, orientacao);
            if(!prox){
                System.out.print("Posição inválida!!!");
                continue; 
            }
            if(tam!=1)
                orientacao = BatalhaNavalGame.posicao(); 
            prox = BatalhaNavalGame.conferirPosicao(tam, matriz, shot, orientacao);
            if(!prox) 
                System.out.print("Posição inválida!!!");
            else 
                matriz = insereNavio(tam, matriz, shot, orientacao);
        }while(!prox); 
        return matriz;
    }
    
    // Método para inserir os navios manualmente na matriz
    public static char[][] manual(char[][] matriz){ 
        BatalhaNavalGame.mostrarTabuleiro(matriz);
        matriz = BatalhaNavalGame.manualinsertNavios(4, matriz);
            
        for(int i=0; i<2; i++){
            BatalhaNavalGame.mostrarTabuleiro(matriz);                
            matriz = BatalhaNavalGame.manualinsertNavios(3, matriz);
        }
        for(int i=0; i<3; i++){ 
            BatalhaNavalGame.mostrarTabuleiro(matriz);                
            matriz = BatalhaNavalGame.manualinsertNavios(2, matriz);
        }
        for(int i=0; i<4; i++){
            BatalhaNavalGame.mostrarTabuleiro(matriz);                
            matriz = BatalhaNavalGame.manualinsertNavios(1, matriz);
        }
        return matriz;
    }
    // Método para inserir os navios automaticamente na matriz
    public static char[][] auto(char[][] matriz){ 
        matriz = BatalhaNavalGame.autoinsertNavios(matriz, 1, 4);            
        matriz = BatalhaNavalGame.autoinsertNavios(matriz, 2, 3);            
        matriz = BatalhaNavalGame.autoinsertNavios(matriz, 3, 2);            
        matriz = BatalhaNavalGame.autoinsertNavios(matriz, 4, 1);
        return matriz; 
    }

    // Método para contar quantos navios foram atingidos
    public static boolean contar(char[][] matriz){
        int shots = 0;
        for(char[] linha : matriz){
            for(char coluna : linha){
                if(coluna=='X')
                    shots++;
            }
        }
        if(shots==20)
            return true; 
        else
            return false;
    }
}

