package com.br.ibm;

import jakarta.persistence.Id;

import java.util.Arrays;

public class AvaliacaoIBMSubArray {

    public static int calculaSomaMaximaSubArray(int[] arr) {
        int somaMaxGeral = 0;
        int somaMaxAtual = 0;

        for (int i: arr) {
            somaMaxAtual = somaMaxAtual + i;
            somaMaxAtual = Integer.max(somaMaxAtual, 0);

            somaMaxGeral = Integer.max(somaMaxGeral, somaMaxAtual);
        }

        return somaMaxGeral;
    }

    public static int[] capturaSubArray(int[] arr) {
        int maxGeral = 0, maxAtual = 0, inicioSomaMax = 0, fimSomaMax = 0, inicioSomaPositiva = 0;

        if (arr.length <= 1) {
            return arr;
        }

        for (int n = 0; n < arr.length; n++) {
            maxAtual = maxAtual + arr[n];
            if (maxAtual < arr[n]) {
                maxAtual = arr[n];
                inicioSomaPositiva = n;
            }
            if (maxGeral < maxAtual) {
                maxGeral = maxAtual;
                inicioSomaMax = inicioSomaPositiva;
                fimSomaMax = n;
            }
        }

        int[] subArray = Arrays.copyOfRange(arr, inicioSomaMax, fimSomaMax + 1);

        return subArray;

    }

    public static void main(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        System.out.println("O subarray " + Arrays.toString(capturaSubArray(arr)) + " possui a soma máxima de " +
                +calculaSomaMaximaSubArray(arr));
    }
}
