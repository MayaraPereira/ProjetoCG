package modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import processarImagem.PanelDaImagem;
import view.Tela;
import view.Tela_Resultado;

public class Histograma extends PanelDaImagem{
	
	BufferedImage imagem_histograma;
	BufferedImage imagem_equalizada;
	Tela_Resultado t;
	public Histograma(){
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
		
		Tela.panel_Pontos_2.setVisible(false);
		Tela.panelDaImagem2.exibirResultado(equalize(Tela.panelDaImagem1.imagemOriginal));
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
	private BufferedImage equalize(BufferedImage imagemOriginal) {
        BufferedImage imgFinalEqualizada = new BufferedImage(imagemOriginal.getWidth(), imagemOriginal.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        WritableRaster imagemOrig = imagemOriginal.getRaster();
        WritableRaster er = imgFinalEqualizada.getRaster();
        int totpix = imagemOrig.getHeight() * imagemOrig.getWidth();
        int[] histogram = new int[256];

        //Construcao do Histograma
        for (int x = 0; x < imagemOrig.getWidth(); x++) {
            for (int y = 0; y < imagemOrig.getHeight(); y++) {
                histogram[imagemOrig.getSample(x, y, 0)]++;
            }
        }
        //
        int[] histogramImagem = new int[256];
        histogramImagem[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            histogramImagem[i] = histogramImagem[i-1] + histogram[i];
        }
        //A probabilidade de uma ocorrência de um pixel de nível i na imagem
        float[] arr = new float[256];
        for (int i = 0; i < 256; i++) {
            arr[i] = (float) (histogramImagem[i] * 255.0 / (float)totpix);
        }
        //Construcao da Imagem Equalizada
        for (int x = 0; x < imagemOrig.getWidth(); x++) {
            for (int y = 0; y < imagemOrig.getHeight(); y++) {
                int nVal = (int) arr[imagemOrig.getSample(x,y,0)];
                er.setSample(x,y,0,nVal);
            }
        }
        imgFinalEqualizada.setData(er);
        return imgFinalEqualizada;
    }
}
//FIM