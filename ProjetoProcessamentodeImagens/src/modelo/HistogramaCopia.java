package modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import processarImagem.PanelDaImagem;
import view.Tela;
import view.Tela_Resultado;

public class HistogramaCopia extends PanelDaImagem{
	
	BufferedImage imagem_histograma;
	BufferedImage imagem_equalizada;
	Tela_Resultado t;
	public HistogramaCopia(){
	}
	
	public void recebeImagem(int largura, int altura, int [][]matrizImagem){
		
		int histograma_k [] = new int [256]; // histograma de frequencia de cada nível de cinza
		
		//obtem através do metodo histograma, o histograma da imagem passada 
		histograma_k = histograma(largura, altura, matrizImagem);
				
		//array que armazena a probabilidade de cada nivel de pixel
		double r_k [] = new double [256]; 
		
		r_k = r_k();
		
		int soma_frequencia = 0; 
		for (int i = 0; i < r_k.length; i++) {
			soma_frequencia += histograma_k[i]; //realiza somatorio da frequencia dos pixel
			
		}
		
		//array que armazena a probabilidade da frequencia de cada nivel de pixel
		double p_r_k [] = new double [256]; // probabilidade
		double aux;
		for (int i = 0; i < p_r_k.length; i++) {
			aux = histograma_k[i];
			p_r_k [i] = aux/soma_frequencia;
		}
		
		double s_k [] = new double [256]; // probabilidade acumulativa
		double acumulativo = 0;
		
		for (int i = 0; i < s_k.length; i++) {
			acumulativo += p_r_k[i]; 
			s_k [i] += acumulativo;
		}
		
		double m_s_k [] = new double [256]; //niveis de cinza
		
		for (int i = 0; i < m_s_k.length; i++) {
			m_s_k [i] = Math.round(255 * s_k[i]);
		}
		 
		double m_r_k [] = new double [256]; //
		
		for (int i = 0; i < m_r_k.length; i++) {
			m_r_k [i] = Math.round(255 * r_k[i]);
		}
		
		//obtem histograma equalizado com auxilio do array m_s_k [i] que guarda os niveis de cinza
		int [] histograma_equalizado = new int [256];
		int aux2;
		for (int i = 0; i < histograma_equalizado.length; i++) {
			aux2 = (int)m_s_k [i];
			histograma_equalizado[aux2] += histograma_k[i];  
		}
		
		imagem_histograma = new BufferedImage(altura, largura, BufferedImage.TYPE_INT_RGB);
		imagem_equalizada = new BufferedImage(altura, largura, BufferedImage.TYPE_INT_RGB);
		
		int maior = 0;
        for (int i = 0; i < histograma_k.length; i++) {
			if (maior < histograma_k[i]) {
				maior = histograma_k[i];
			}
		}
		//histograma inicial
		for (int i = 0; i < largura-1; i++) {
        	int funcao = (100*histograma_k[i])/maior;
			for (int j = 0; j < funcao; j++) {
				try {
					imagem_histograma.setRGB(i, altura -1 - j, Color.WHITE.getRGB());
				} catch (Exception e) {
					System.out.print("ERRO ");
				}				
			}
		}
		
		repaint();
		Tela.panelDaImagem3.exibirResultado(imagem_histograma);
		Tela.lblNewLabel.setText("Histograma da Imagem");
		repaint();
		validate();
		
		int maior_e = 0;
        for (int i = 0; i < histograma_k.length; i++) {
			if (maior_e < histograma_k[i]) {
				maior_e = histograma_k[i];
			}
		}
		
		for (int i = 0; i < largura-1; i++) {
        	int funcao = (100*histograma_equalizado[i])/maior_e;
			for (int j = 0; j < funcao; j++) {
				try {
					imagem_equalizada.setRGB(i, altura -1 - j, Color.WHITE.getRGB());
				} catch (Exception e) {
					System.out.print("ERRO ");
				}				
			}
		}
		Tela.panelDaImagem2.exibirResultado(equalizacao(Tela.panelDaImagem1.imagemOriginal, histograma_equalizado));
		Tela.panelDaImagem4.exibirResultado(imagem_equalizada);
		Tela.lblResultadoNormalizado.setText("Histograma Equalizado");
		repaint();
		validate();
	}
	
	
	public int [] histograma (int largura, int altura, int imagem [][]){
		int histograma_imagem[] = new int [256];
		
		for (int i = 0; i < largura; i++) {
			for (int j = 0; j < altura; j++) {
				int pixel = imagem[i][j];
				histograma_imagem[pixel]++; // obtenho frequencia de cada pixel
				
			}
		}
		return histograma_imagem;
	}
	
	public double[] r_k (){
		double []r_k = new double [256];
		double aux=0;
		double r;
		for (int i = 0; i < r_k.length; i++) {
			r_k [i] = (aux/255);
			aux++;
			
		}
		return r_k;
	}

	public int[] calculaHistogramaAcumulado(int[] histograma) {
        int[] acumulado = new int[256];
        acumulado[0] = histograma[0];
        for(int i=1;i < histograma.length;i++) {

            acumulado[i] = histograma[i] + acumulado[i-1];
        }
        return acumulado;
    }
	
	private int[] calculaMapadeCores(int[] histograma, int pixels) {
        int[] mapaDeCores = new int[256];
        int[] acumulado = calculaHistogramaAcumulado(histograma);
        float menor = menorValor(histograma);
        for(int i=0; i < histograma.length; i++) {
            mapaDeCores[i] = Math.round(((acumulado[i] - menor) / (pixels - menor)) * 255);
        }
        return mapaDeCores;
    }

	public BufferedImage equalizacao(BufferedImage img, int[] histograma_equalidado) {
        //2.1- calcular histograma
        int[] histograma = histograma_equalidado;
        //2.2- calcular novo mapa de cores
        int[] mapaDeCores = calculaMapadeCores(histograma, img.getWidth() * img.getHeight());
        int[] pontosMatriz;
        //2.3- atualizar palheta de cores da imagem
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x, y));
                int tom = color.getRed();
                int newTom = mapaDeCores[tom];
                
                if (y < 10 && x < 9) {
					matrizPontosImagem[y][x] = newTom;
				}
                
                Color newColor = new Color(newTom, newTom, newTom);
                out.setRGB(x, y, newColor.getRGB());
            }
        }
        PanelDaImagem.plotaPixels(Tela.panel_Pontos_2);
        return out;
    }
	private int menorValor(int[] histograma) {
        for(int i=0; i <histograma.length; i++) {
            if(histograma[i] != 0){
                return histograma[i];
            }
        }
        return 0;
    }
	
	public double trunca(double r){
	    
	    int decimalPlaces = 3;
	    BigDecimal bd = new BigDecimal(r);

	    // setScale is immutable
	    bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
	    r = bd.doubleValue();
	    return r;
	}

}
