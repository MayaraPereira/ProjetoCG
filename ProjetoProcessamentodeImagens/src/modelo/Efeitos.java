package modelo;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import processarImagem.PanelDaImagem;

public class Efeitos extends PanelDaImagem {

	public void transformacao() throws Exception {
		Transformacao teste = new Transformacao();
	}

	// método que realiza efeito negativo básico S = 255 - nivelDeCinzaPixel
	public void negativo(int largura, int altura, int[][] matrizImagem) {
		// matriz auxiliar
		int[][] matrizImagemNegativa = new int[largura][altura];

		// percorre toda imagem pixel a pixel para realizar efeito (negativo)
		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				// pega o nível de cor de um pixel
				// retornará o inverso do valor do pixel
				matrizImagemNegativa[x][y] = 255 - matrizImagem[x][y];
			}
		}
		exibir(matrizImagemNegativa);

	}

	// Método que aplica o filtro média na imagem
	public void media(int largura, int altura, int[][] matrizImagem) {// recebe como paramêtro a matriz da
																		// imagem para realizar
		int[][] matriz_auxiliar = new int[largura + 1][altura + 1];
		int[][] matrizResultado = new int[largura][altura];

		// copia a imagem recebida em parametro para a matriz da imagem auxiliar, para
		// poder realizar a operação
		for (int i = 1; i < matriz_auxiliar.length; i++) {
			for (int j = 1; j < matriz_auxiliar.length; j++) {
				matriz_auxiliar[j][i] = matrizImagem[j - 1][i - 1];
			}
		}

		// realizando efeito média
		for (int y = 1; y < largura; y++) {
			for (int x = 1; x < altura; x++) {

				// soma todos os 9 pixel correspondente de onde a mascara estar
				float media = matriz_auxiliar[y - 1][x - 1] + matriz_auxiliar[y - 1][x] + matriz_auxiliar[y - 1][x + 1]
						+ matriz_auxiliar[y][x + 1] + matriz_auxiliar[y][x] + matriz_auxiliar[y + 1][x + 1]
						+ matriz_auxiliar[y + 1][x] + matriz_auxiliar[y][x - 1] + matriz_auxiliar[y + 1][x - 1];

				// pega o resultado da soma e faz a média dividindo por 9
				media = media / 9;

				// coloca resultado na imagem de saída
				matrizResultado[y - 1][x - 1] = Math.round(media);
			}
		}

		exibir(matrizResultado);
	}

	// Método que aplica o filtro mediana na imagem
	public void mediana(int largura, int altura, int[][] matrizImagem) {
		int matriz_imagem_auxiliar[][] = new int[largura + 1][altura + 1];
		int matrizResultado[][] = new int[largura][altura];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matriz_imagem_auxiliar.length; i++) {
			for (int j = 1; j < matriz_imagem_auxiliar.length; j++) {

				matriz_imagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}

		for (int y = 1; y < altura; y++) {
			for (int x = 1; x < largura; x++) {

				// coloca em um array todos os valores dos pixel da mascara
				int[] mascara = { matriz_imagem_auxiliar[y - 1][x - 1], matriz_imagem_auxiliar[y - 1][x],
						matriz_imagem_auxiliar[y - 1][x + 1], matriz_imagem_auxiliar[y][x + 1],
						matriz_imagem_auxiliar[y][x], matriz_imagem_auxiliar[y + 1][x + 1],
						matriz_imagem_auxiliar[y + 1][x], matriz_imagem_auxiliar[y][x - 1],
						matriz_imagem_auxiliar[y + 1][x - 1] };
				// ordena
				Arrays.sort(mascara);
				// atribui a variavel mediana a posição do meio do array, obtendo assim a
				// mediana
				int mediana = mascara[4];

				// Atribui a imagem de saída o resultado final
				matrizResultado[y - 1][x - 1] = mediana;
			}
		}

		exibir(matrizResultado);
	}

	// Aplica o filtro passa-alta basico na imagem
	public void passa_alta(int largura, int altura, int[][] matrizImagem) {
		altura = altura + 2;
		largura = largura + 2;
		int[][] matriz_Resultado = new int[altura][largura];
		double w = 8;

		BufferedImage imagem_auxiliar = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
		int matriz_auxiliar[][] = new int[largura][altura];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < imagem_auxiliar.getHeight() - 1; i++) {
			for (int j = 1; j < imagem_auxiliar.getWidth() - 1; j++) {
				
				matriz_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}


		for (int y = 1; y < imagem_auxiliar.getHeight() - 1; y++) {
			for (int x = 1; x < imagem_auxiliar.getWidth() - 1; x++) {

				double passa_alta = -matriz_auxiliar[y - 1][x - 1] - matriz_auxiliar[y - 1][x]
						- matriz_auxiliar[y - 1][x + 1] - matriz_auxiliar[y][x + 1] + (w * matriz_auxiliar[y][x])
						- matriz_auxiliar[y + 1][x + 1] - matriz_auxiliar[y + 1][x] - matriz_auxiliar[y][x - 1]
						- matriz_auxiliar[y + 1][x - 1];
				int passa_alta_aux = (int) Math.round(passa_alta);

				matriz_Resultado[y - 1][x - 1] = matrizImagem[y - 1][x - 1] - passa_alta_aux;

			}
		}

		exibir(matriz_Resultado);
	}

	// Aplica o filtro alto reforço na imagem
	public void alto_reforco(int largura, int altura, int[][] matrizImagem, double a) {
		largura = largura + 2;
		altura = altura + 2;
		int[][] matriz_Resultado = new int[altura][largura];
		int matriz_auxiliar[][] = new int[largura][altura];
		double w = (9 * a) - 1;

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < altura - 1; i++) {
			for (int j = 1; j < largura - 1; j++) {
				matriz_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}

		for (int y = 1; y < altura - 1; y++) {
			for (int x = 1; x < largura - 1; x++) {

				double alto_reforco = -matriz_auxiliar[y - 1][x - 1] - matriz_auxiliar[y - 1][x]
						- matriz_auxiliar[y - 1][x + 1] - matriz_auxiliar[y][x + 1] + (w * matriz_auxiliar[y][x])
						- matriz_auxiliar[y + 1][x + 1] - matriz_auxiliar[y + 1][x] - matriz_auxiliar[y][x - 1]
						- matriz_auxiliar[y + 1][x - 1];
				int alto_reforco_aux = (int) Math.round(alto_reforco);
				matriz_Resultado[y - 1][x - 1] = alto_reforco_aux;
			}
		}

		exibir(matriz_Resultado);
	}

	// Aplica o filtro de Prewitt na imagem
	public void prewitt(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura][largura];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}
		
		int resultadoX, resultadoY, resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultadoX = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-1) * matrizImagem_auxiliar[x-1][y]
						+ (-1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				
				resultadoY = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-1) * matrizImagem_auxiliar[x][y-1]
						+ (-1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (1) * matrizImagem_auxiliar[x][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				resultado = Math.abs(resultadoX) + Math.abs(resultadoY);
				matrizImagem_auxiliar[x - 1][y - 1] = resultado;
			}
		}
		exibir(matrizImagem_auxiliar);
	}

	public void prewitt_em_x(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura][largura];
		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}
		
		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-1) * matrizImagem_auxiliar[x-1][y]
						+ (-1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);
			}
		}

		exibir(matrizImagem_auxiliar);
	}

	public void prewitt_em_y(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura][largura];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}
		
		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-1) * matrizImagem_auxiliar[x][y-1]
						+ (-1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (1) * matrizImagem_auxiliar[x][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);
			}
		}

		exibir(matrizImagem_auxiliar);
	}

	public void sobelEmX(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura][largura];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}
		
		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-1) * matrizImagem_auxiliar[x-1][y]
						+ (-1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);
			}
		}

		exibir(matrizImagem_auxiliar);
	}

	public void sobelEmY(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura][largura];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}
		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-1) * matrizImagem_auxiliar[x][y-1]
						+ (-1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (1) * matrizImagem_auxiliar[x][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);

			}
		}

		exibir(matrizImagem_auxiliar);
	}

	// Aplica o filtro de Sobel na imagem
	public void sobel(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura][largura];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}
		
		int resultadoX, resultadoY, resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultadoX = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-2) * matrizImagem_auxiliar[x-1][y]
						+ (-1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (2) * matrizImagem_auxiliar[x + 1][y]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				
				resultadoY = (-1) * matrizImagem_auxiliar[x - 1][y - 1]
						+ (-2) * matrizImagem_auxiliar[x][y-1]
						+ (-1) * matrizImagem_auxiliar[x + 1][y - 1]
						+ (1) * matrizImagem_auxiliar[x - 1][y + 1]
						+ (2) * matrizImagem_auxiliar[x][y + 1]
						+ (1) * matrizImagem_auxiliar[x + 1][y + 1];
				
				resultado = Math.abs(resultadoX) + Math.abs(resultadoY);
				matrizImagem_auxiliar[x - 1][y - 1] = resultado;
			}
		}

		exibir(matrizImagem_auxiliar);
	}

	public void gradienteEmX(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura + 2][largura + 2];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}
		
		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (1) * matrizImagem_auxiliar[x][y]
						+ (-1) * matrizImagem_auxiliar[x+1][y];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);
			}
		}

		exibir(matrizImagem_auxiliar);

	}
	
	public void gradienteEmY(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura + 2][largura + 2];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {

				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}

		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (1) * matrizImagem_auxiliar[x][y]
						+ (-1) * matrizImagem_auxiliar[x][y+1];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);
			}
		}

		exibir(matrizImagem_auxiliar);
	}
	
	// Aplica o filtro de Gradiente de Roberts na imagem
	public void gradiente(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura + 2][largura + 2];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}

		int resultadoX, resultadoY, resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultadoX = (1) * matrizImagem_auxiliar[x][y]
						+ (-1) * matrizImagem_auxiliar[x+1][y];
				
				resultadoY = (1) * matrizImagem_auxiliar[x][y]
						+ (-1) * matrizImagem_auxiliar[x][y+1];
				
				resultado = Math.abs(resultadoX) + Math.abs(resultadoY);
				matrizImagem_auxiliar[x - 1][y - 1] = resultado;
			}
		}

		exibir(matrizImagem_auxiliar);
	}

	public void gradienteCruzadoEmX(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura + 2][largura + 2];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}

		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (1) * matrizImagem_auxiliar[x][y]
						+ (-1) * matrizImagem_auxiliar[x+1][y+1];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);
			}
		}

		exibir(matrizImagem_auxiliar);

	}
	
	public void gradienteCruzadoEmY(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura + 2][largura + 2];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}

		int resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultado = (1) * matrizImagem_auxiliar[x][y+1]
						+ (-1) * matrizImagem_auxiliar[x+1][y];
				
				matrizImagem_auxiliar[x - 1][y - 1] = Math.abs(resultado);
			}
		}

		exibir(matrizImagem_auxiliar);
	}
	
	// Aplica o filtro de Gradiente Cruzado de Roberts na imagem
	public void gradienteCruzado(int largura, int altura, int[][] matrizImagem) {
		int matrizImagem_auxiliar[][] = new int[altura + 2][largura + 2];

		// inclui em uma imagem auxilar mais uma linha de "zeros" em todas as laterais
		for (int i = 1; i < matrizImagem_auxiliar.length - 1; i++) {
			for (int j = 1; j < matrizImagem_auxiliar.length - 1; j++) {
				matrizImagem_auxiliar[i][j] = matrizImagem[i - 1][j - 1];
			}
		}

		int resultadoX, resultadoY, resultado;
		for (int x = 1; x < matrizImagem_auxiliar.length - 1; x++) {
			for (int y = 1; y < matrizImagem_auxiliar.length - 1; y++) {

				resultadoX = (1) * matrizImagem_auxiliar[x][y]
						+ (-1) * matrizImagem_auxiliar[x+1][y+1];
				
				resultadoY = (1) * matrizImagem_auxiliar[x][y+1]
						+ (-1) * matrizImagem_auxiliar[x+1][y];
				
				resultado = Math.abs(resultadoX) + Math.abs(resultadoY);
				matrizImagem_auxiliar[x - 1][y - 1] = resultado;
			}
		}

		exibir(matrizImagem_auxiliar);
	}

	// S= cr^Y (0<=gamma<=1)
	public void negativo_gamma(int largura, int altura, int[][] r_matrizImagem, double gamma) {
		// Criar uma imagem de Buffer para receber manipulações
		int[][] matrizImagemNegativa = new int[largura][altura];
		int c = 1;
		// percorre toda imagem pixel a pixel para realizar efeito (negativo)
		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				// pega o nivel de cor de um pixel
				// retornará o inverso do valor do pixel

				matrizImagemNegativa[x][y] = (int) Math.round(c * Math.pow(r_matrizImagem[x][y], gamma));
			}
		}

		exibir(matrizImagemNegativa);
	}

	// S = alog(r + 1),
	public void negativo_logaritmico(int largura, int altura, int[][] r_matrizImagem, double fator) {

		// Criar uma imagem de Buffer para receber manipulações
		int[][] matrizImagemNegativa = new int[largura][altura];

		// percorre toda imagem pixel a pixel para realizar efeito (negativo)
		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				// pega o nivel de cor de um pixel
				// retornará o inverso do valor do pixel
				double log = fator * Math.log(r_matrizImagem[x][y] + 1);
				matrizImagemNegativa[x][y] = (int) log;
			}
		}

		exibir(matrizImagemNegativa);
	}

	public void itf_sigmoide(int largura, int altura, int[][] r_matrizImagem, int w, int sigma) {

		// Criar uma imagem de Buffer para receber manipulações
		int[][] matrizImagemNegativa = new int[largura][altura];
		
		// percorre toda imagem pixel a pixel para realizar efeito (negativo)
		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				// pega o nivel de cor de um pixel
				// retornará o inverso do valor do pixel
				double log = Math.round(255 * 1 / (1 + Math.E - ((r_matrizImagem[x][y] - w) / sigma)));
				matrizImagemNegativa[x][y] = (int) log;
			}
		}

		exibir(matrizImagemNegativa);
	}

	public void faixa_dinamica(int largura, int altura, int[][] r_matrizImagem, int w) {
		// Criar uma imagem de Buffer para receber manipulações
		int[][] matrizImagemNegativa = new int[largura][altura];
		int fmin = fMin(r_matrizImagem);
		int fmax = fMax(r_matrizImagem);
		int dif = 256-w;

		// percorre toda imagem pixel a pixel para realizar efeito (negativo)
		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {
				

				// pega o nivel de cor de um pixel
				// retornará o inverso do valor do pixel
				matrizImagemNegativa[y][x] = (r_matrizImagem[y][x]-fmin/fmax- fmin)*dif;
			}
		}

		exibir(matrizImagemNegativa);
	}

	public void transformacao_linear(int largura, int altura, int[][] r_matrizImagem, int contraste, int brilho) {
		// Criar uma imagem de Buffer para receber manipulações
		int[][] matrizImagemNegativa = new int[largura][altura];

		// percorre toda imagem pixel a pixel para realizar efeito (negativo)
		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {

				// pega o nivel de cor de um pixel
				// retornará o inverso do valor do pixel
				matrizImagemNegativa[y][x] = r_matrizImagem[y][x] * contraste + brilho;
			}
		}

		exibir(matrizImagemNegativa);
	}
	
	private int fMax(int[][] matrizImagem) {
		int max = matrizImagem[0][0];
				
				for (int y = 0; y < largura; y++) {
					for (int x = 0; x < altura; x++) {
						if (max < matrizImagem[y][x]) {
							max = matrizImagem[y][x];
						}
					}
				}

		return max;
	}

	private int fMin(int[][] matrizImagem) {
		int min = matrizImagem[0][0];
		
		for (int y = 0; y < largura; y++) {
			for (int x = 0; x < altura; x++) {
				if (min > matrizImagem[y][x]) {
					min = matrizImagem[y][x];
				}
			}
		}

		return min;
	}
}
