package modelo;

import processarImagem.PanelDaImagem;

public class Op_Matematicas extends PanelDaImagem {

	// Aplica Soma entre imagens
	public void soma(int largura1, int altura1, int[][] matrizImagem1, int largura2, int altura2,
			int[][] matrizImagem2) {

		int altura = defineAlturaLargura(altura1, largura1, altura2, largura2)[0];
		int largura = defineAlturaLargura(altura1, largura1, altura2, largura2)[1];
		
		int[][] matriz_resultado = new int[altura][largura];

		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {
				int valor1 = matrizImagem1[y][x];
				int valor2 = matrizImagem2[y][x];
				int res_soma = valor1 + valor2;
				matriz_resultado[y][x] = res_soma;
			}
		}
		
		exibir(matriz_resultado);
	}

	// Aplica Subtração entre imagens
	public void subtracao(int largura1, int altura1, int[][] matrizImagem1, int largura2, int altura2,
			int[][] matrizImagem2) {
		int altura = defineAlturaLargura(altura1, largura1, altura2, largura2)[0];
		int largura = defineAlturaLargura(altura1, largura1, altura2, largura2)[1];

		int[][] matriz_resultado = new int[largura][altura];

		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				int valor1 = matrizImagem1[y][x];
				int valor2 = matrizImagem2[y][x];
				int res_subtracao = valor1 - valor2;
				matriz_resultado[y][x] = res_subtracao;
			}
		}

		exibir(matriz_resultado);
	}

	// Aplica Multiplicação entre imagens
	public void multiplicacao(int largura1, int altura1, int[][] matrizImagem1, int largura2, int altura2,
			int[][] matrizImagem2) {
		int altura = defineAlturaLargura(altura1, largura1, altura2, largura2)[0];
		int largura = defineAlturaLargura(altura1, largura1, altura2, largura2)[1];
		
		int[][] matriz_resultado = new int[largura][altura];

		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				int valor1 = matrizImagem1[y][x];
				int valor2 = matrizImagem2[y][x];
				int res_multiplicacao = valor1 * valor2;
				matriz_resultado[y][x] = res_multiplicacao;
			}
		}
		exibir(matriz_resultado);
	}

	// Aplica de Divisão entre imagens
	public void divisao(int largura1, int altura1, int[][] matrizImagem1, int largura2, int altura2,
			int[][] matrizImagem2) {
		int altura = defineAlturaLargura(altura1, largura1, altura2, largura2)[0];
		int largura = defineAlturaLargura(altura1, largura1, altura2, largura2)[1];
		
		int[][] matriz_resultado = new int[largura][altura];

		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				int valor1 = matrizImagem1[y][x];
				int valor2 = matrizImagem2[y][x];
				int divisao_pixel;

				// Se for uma divisão por 0, já seta para 0
				if (valor2 == 0) {
					divisao_pixel = 0;
				} else {
					divisao_pixel = Math.round(valor1 / valor2);
				}

				matriz_resultado[y][x] = divisao_pixel;
			}
		}
		exibir(matriz_resultado);
	}
}
