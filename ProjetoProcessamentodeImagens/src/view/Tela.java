package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modelo.Efeitos;
import modelo.Histograma;
import modelo.Morfologia;
import modelo.Op_Logicas;
import modelo.Op_Matematicas;
import processarImagem.PanelDaImagem;

public class Tela extends JFrame {

	private static JPanel painelPrincipal;

	public static PanelDaImagem panelDaImagem1 = new PanelDaImagem(); // painel da primeira imagem
	public static PanelDaImagem panelDaImagem2 = new PanelDaImagem(); // painel da segunda imagem
	public static PanelDaImagem panelDaImagem3 = new PanelDaImagem(); // painel da quarta imagem
	public static PanelDaImagem panelDaImagem4 = new PanelDaImagem(); // painel da quarta imagem
	public static JPanel panelMascara = new JPanel(new GridLayout(3, 3));
	public static JLabel lblMenorValor = new JLabel("");
	public static JLabel lblMaiorValor = new JLabel("");
	
	Efeitos panelDaImagem_ef = new Efeitos(); //menu de efeitos (filtros)
	Op_Logicas panelDaImagem_ol = new Op_Logicas(); // menu de operacoes logicas (and, or e xor)
	Op_Matematicas panelDaImagem_om = new Op_Matematicas(); // menu operacoes mat (soma, subtracao, multip e div)
	Histograma panelDaImagem_h = new Histograma(); // menu histograma
	Morfologia panelDaImagem_mor = new Morfologia(); // menu operadores morfologicos

	public static JPanel panel_2 = new JPanel();
	public static PanelDaImagem panel_Pontos_1 = new PanelDaImagem();
	public static PanelDaImagem panel_Pontos_2 = new PanelDaImagem();
	public static JPanel panel_Pontos_3 = new JPanel(new GridLayout(9, 8));
	public static JPanel panel_Pontos_4 = new JPanel(new GridLayout(9, 8));
	public static JPanel panel_4 = new JPanel();
	public static JLabel lblResultadoNormalizado;
	public static JLabel lblNewLabel = new JLabel("");
	JLabel lblDica = new JLabel("");
	JLabel lblFiltro = new JLabel("");

	private boolean[][] CROSS = { { false, true, false }, { true, true, true }, { false, true, false } };

	JPanel painel3; // painel das imagens resultado
	// condições booleanas para ativar e desativar a visibilidade do painel3(de resultados)
	boolean logico = false;
	boolean aritmeticos = false;
	public static boolean efeitos1 = false;
	boolean his = false;
	public static boolean mor = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1323, 865);
		setTitle("Processamento de Imagens - Mayara Medeiros e Matheus Cavalcante");

		JMenuBar menuBar = new JMenuBar(); // barra de menu
		setJMenuBar(menuBar);

		JMenu mnEfeitos = new JMenu("Efeitos"); // menu efeitos
		menuBar.add(mnEfeitos);

		painelPrincipal = new JPanel();
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JPanel panel_3 = new JPanel();// painel resultados
		panel_3.setLayout(null);
		panel_3.setBounds(681, 122, 256, 256);
		panel_3.setBorder(BorderFactory.createEtchedBorder());
		painelPrincipal.add(panel_3);

		JMenuItem mntmTransformacao = new JMenuItem("Morfismo  dependente  do  tempo"); // botao para aplicar morfismo
		mnEfeitos.add(mntmTransformacao);
		mntmTransformacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				try {
					panelDaImagem_ef.transformacao();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JMenuItem mntmNegativo = new JMenuItem("Negativo"); // Botão efeito negativo
		mnEfeitos.add(mntmNegativo);
		mntmNegativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito negativo e retorna a imagem resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.negativo(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);
				
				lblFiltro.setText("Negativo");
				lblDica.setText(
						"<html>INFORMAÇÃO: Útil em situações onde a imagem original é escura. Um exemplo bastante comum desta técnica são as imagens médicas.<br/>Matematicamente, o negativo de uma imagem define-se por: s= L-1-r</html>");
				
			}
		});

		JMenuItem media = new JMenuItem("Média"); // Botão efeito Média
		mnEfeitos.add(media);
		media.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// mascara 3x3 com todos os elementos iguais a 9
				// fator de normalizacao = 9
				String[] mascara = { "1/9", "1/9", "1/9", "1/9", "1/9", "1/9", "1/9", "1/9", "1/9" };
				plotaMascara(mascara);
				// chamada do método para aplicar o efeito média e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.media(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Filtro de Média");
				lblDica.setText(
						"INFORMAÇÃO: Suavização de imagens. Produz borramento da imagem para eliminar detalhes ou remoção de ruídos (Gaussiano).");
			}

		});

		JMenuItem mediana = new JMenuItem("Mediana"); // Botão mediana
		mnEfeitos.add(mediana);
		mediana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito mediana e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.mediana(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Filtro de Mediana");
				lblDica.setText(
						"INFORMAÇÃO: Suavização de imagens. Produz borramento da imagem para eliminar detalhes ou remoção de ruídos (Sal e Pimenta).");
			}

		});

		JMenuItem passa_alta = new JMenuItem("Passa alta Básico"); // Botão Passa Alta
		mnEfeitos.add(passa_alta);
		passa_alta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito passa alta e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.passa_alta(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				// elemento central positivo e restante negativo. Soma de tudo é zero, portanto em regioes homogeneas
				// o resutado sera 0 ou próximo a 0.
				String[] mascara = { "-1", "-1", "-1", "-1", "8", "-1", "-1", "-1", "-1" };
				plotaMascara(mascara);

				lblFiltro.setText("Passa Alta Básico");
				lblDica.setText("INFORMAÇÃO: Utilizado para detalhar detalhes finos na imagem.");
			}

		});

		JMenuItem alto_reforco = new JMenuItem("Alto Reforço"); // Botão Alto Reforco
		alto_reforco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// Fator auxiliar manipulado pelo usúario para aumentar ou diminuir o efeito
				// aplicado pela equação (W = 9A -1)
				double A = 1.2;
				A = Double.parseDouble(JOptionPane.showInputDialog("Valor para 'A', sendo A>=1: ", A));

				// chamada do método para aplicar o efeito alto reforço e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.alto_reforco(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem,
						A);

				lblFiltro.setText("Alto Reforço");
				lblDica.setText("INFORMAÇÃO: O valor de A influencia diretamente na nitidez e detalhamento da imagem.");
			}

		});
		mnEfeitos.add(alto_reforco);

		JMenuItem prewittX = new JMenuItem("Prewitt - Em X"); // Botão prewitt em X
		mnEfeitos.add(prewittX);
		prewittX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito prewitt e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.prewitt_em_x(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem);

				lblFiltro.setText("Prewitt em X");
				lblDica.setText("INFORMAÇÃO: A  diferença  entre  a  terceira  e  a  primeira  linha,  aproxima  a  derivada  na  direção  x.");
				String[] mascara = { "-1", "0", "1", "-1", "0", "1", "-1", "0", "1" };
				plotaMascara(mascara);
			}
		});

		JMenuItem prewittY = new JMenuItem("Prewitt - Em Y"); // Botão prewitt em Y
		mnEfeitos.add(prewittY);
		prewittY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito prewitt e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.prewitt_em_y(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem);

				lblFiltro.setText("Prewitt em Y");
				lblDica.setText("INFORMAÇÃO: A  diferença  entre  a  terceira  e  primeira  coluna,  aproxima  a  derivada  na  direção  y.");

				String[] mascara = { "-1", "-1", "-1", "0", "0", "0", "1", "1", "1" };
				plotaMascara(mascara);
			}
		});

		JMenuItem prewitt = new JMenuItem("Prewitt - Magnitude Final"); // Botão prewitt
		mnEfeitos.add(prewitt);
		prewitt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito prewitt e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.prewitt(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Prewitt - Magnitude Final");
				lblDica.setText("<html>INFORMAÇÃO: A soma do módulo em X e Y. O filtro de Prewitt tem o mesmo conceito do de Sobel (sem o\r\n" + 
						"peso para o pixel mais central) <br/>e de Roberts (sua máscara\r\n" + 
						"abrange uma área de 3 x 3)</html>");
			}

		});

		JMenuItem sobelEmX = new JMenuItem("Sobel - Em X");// Botão operador de Sobel
		mnEfeitos.add(sobelEmX);
		sobelEmX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito sobel em x e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.sobelEmX(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Sobel em X");
				lblDica.setText("");
				String[] mascara = { "-1", "0", "1", "-2", "0", "2", "-1", "0", "1" };
				plotaMascara(mascara);
			}

		});

		JMenuItem sobelEmY = new JMenuItem("Sobel - Em Y");// Botão operador de Sobel
		mnEfeitos.add(sobelEmY);
		sobelEmY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito sobel em y e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.sobelEmY(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Sobel em Y");
				lblDica.setText("");
				String[] mascara = { "-1", "-2", "-1", "0", "0", "0", "1", "2", "1" };
				plotaMascara(mascara);
			}

		});

		JMenuItem sobel = new JMenuItem("Sobel - Magnitude Final");// Botão operador de Sobel
		mnEfeitos.add(sobel);
		sobel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o sobel magnitude final e retorna a imagem resultado
				// para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.sobel(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Sobel - Magnitude Final");
				lblDica.setText("INFORMAÇÃO: Soma dos módulos Sobel em X e Y. Melhora  a  diferenciação  e  suaviza  os  efeitos.");
			}

		});

		JMenuItem gradienteEmX = new JMenuItem("Operador de Roberts - Em X"); // Botão operador de Roberts (gradiente)
		mnEfeitos.add(gradienteEmX);
		gradienteEmX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito gradiente e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.gradienteEmX(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Gradiente / Operador de Roberts em X");
				lblDica.setText("INFORMAÇÃO: Método de diferenciação mais usado para realce de imagens.");
			
				String[] mascara = { "1", "0", "-1", "0" };
				plotaMascara(mascara);
			}

		});
		
		JMenuItem gradienteEmY = new JMenuItem("Operador de Roberts - Em Y"); // Botão operador de Roberts (gradiente)
		mnEfeitos.add(gradienteEmY);
		gradienteEmY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito gradiente e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.gradienteEmY(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Gradiente / Operador de Roberts em Y");
				lblDica.setText("INFORMAÇÃO: Método de diferenciação mais usado para realce de imagens.");
			
				String[] mascara = { "1", "-1", "0", "0" };
				plotaMascara(mascara);
			}

		});
		
		JMenuItem gradiente = new JMenuItem("Operador de Roberts - Magnitude Final"); // Botão operador de Roberts (gradiente)
		mnEfeitos.add(gradiente);
		gradiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito gradiente e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.gradiente(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem);

				lblFiltro.setText("Gradiente / Operador de Roberts");
				lblDica.setText("INFORMAÇÃO: Soma do operador de Roberts em X e Y. Método de diferenciação mais usado para realce de imagens.");
			}

		});

		JMenuItem mntmGradienteCruzadoEmX = new JMenuItem("Operador de Roberts Cruzado - Em X"); // Botão Gradiente Cruzado
		mnEfeitos.add(mntmGradienteCruzadoEmX);
		mntmGradienteCruzadoEmX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito gradiente cruzado e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.gradienteCruzadoEmX(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem);

				lblFiltro.setText("Gradiente Cruzado / Roberts Cruzado em X");
				lblDica.setText("");
				
				String[] mascara = { "1", "0", "0", "-1" };
				plotaMascara(mascara);
			}

		});
		
		JMenuItem mntmGradienteCruzadoEmY = new JMenuItem("Operador de Roberts Cruzado - Em Y"); // Botão Gradiente Cruzado
		mnEfeitos.add(mntmGradienteCruzadoEmY);
		mntmGradienteCruzadoEmY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito gradiente cruzado e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.gradienteCruzadoEmY(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem);

				lblFiltro.setText("Gradiente Cruzado / Roberts Cruzado em Y");
				lblDica.setText("");
				
				String[] mascara = { "0", "1", "-1", "0" };
				plotaMascara(mascara);
			}

		});
		
		JMenuItem mntmGradienteCruzado = new JMenuItem("Operador de Roberts Cruzado - Magnitude Final"); // Botão Gradiente Cruzado
		mnEfeitos.add(mntmGradienteCruzado);
		mntmGradienteCruzado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);

				// chamada do método para aplicar o efeito gradiente cruzado e retorna a imagem
				// resultado para o "panelDaImagem_ef"
				// podendo assim ser exibida
				panelDaImagem_ef.gradienteCruzado(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem);

				lblFiltro.setText("Gradiente Cruzado / Roberts Cruzado");
				lblDica.setText("INFORMAÇÃO: Soma do operador de Roberts cruzado em X e Y.");
				
			}

		});

		JMenu mnOperaesMatemticas = new JMenu("Operações Matemáticas"); // Menu das operações matématicas
		menuBar.add(mnOperaesMatemticas);

		JMenuItem soma = new JMenuItem("Soma"); // Botão para aplicar efeito de somas as duas imagens
		mnOperaesMatemticas.add(soma);
		soma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				aritmeticos = true;
				panelDaImagem_om.setBounds(0, 0, 256, 256);
				panelDaImagem_om.setVisible(aritmeticos);
				panel_3.add(panelDaImagem_om);

				try {
					// chamada do método para aplicar o efeito de somar duas imagens e retorna a
					// imagem resultado para o "panelDaImagem_om"
					// podendo assim ser exibida
					panelDaImagem_om.soma(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem,
							panelDaImagem2.largura, panelDaImagem2.altura, panelDaImagem2.matrizImagem);
					
					lblFiltro.setText("Operação de Soma");
					lblDica.setText(
							"INFORMAÇÃO: Utilizar imagens mais claras. Utilizado para normalização do brilho e remoção de ruídos.");
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		});

		JMenuItem subtracao = new JMenuItem("Subtração"); // Botão de subtração das duas imagens
		mnOperaesMatemticas.add(subtracao);
		subtracao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				aritmeticos = true;
				panelDaImagem_om.setBounds(0, 0, 256, 256);
				panelDaImagem_om.setVisible(aritmeticos);
				panel_3.add(panelDaImagem_om);

				// chamada do método para aplicar o efeito de subtrair as duas imagens e retorna
				// a imagem resultado para o "panelDaImagem_om"
				// podendo assim ser exibida
				panelDaImagem_om.subtracao(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem,
						panelDaImagem2.largura, panelDaImagem2.altura, panelDaImagem2.matrizImagem);

				lblFiltro.setText("Operação de Subtração");
				lblDica.setText(
						"INFORMAÇÃO: Utilizar imagens escuras. Utilizado para detectar diferenças entre as duas imagens.");
			}

		});

		JMenuItem multiplicacao = new JMenuItem("Multiplicação"); // Botão para multiplicar as duas imagens
		mnOperaesMatemticas.add(multiplicacao);
		multiplicacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				aritmeticos = true;
				panelDaImagem_om.setBounds(0, 0, 256, 256);
				panelDaImagem_om.setVisible(aritmeticos);
				panel_3.add(panelDaImagem_om);

				// chamada do método para aplicar o efeito de multiplicar duas imagens e retorna
				// a imagem resultado para o "panelDaImagem_om"
				// podendo assim ser exibida
				panelDaImagem_om.multiplicacao(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem, panelDaImagem2.largura, panelDaImagem2.altura,
						panelDaImagem2.matrizImagem);

				lblFiltro.setText("Operação de Multiplicação");
				lblDica.setText("INFORMAÇÃO: Utilizado para calibração do brilho.");
			}

		});

		JMenuItem mntmDiviso = new JMenuItem("Divisão"); // Botão divisão
		mnOperaesMatemticas.add(mntmDiviso);
		mntmDiviso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				aritmeticos = true;
				panelDaImagem_om.setBounds(0, 0, 256, 256);
				panelDaImagem_om.setVisible(aritmeticos);
				panel_3.add(panelDaImagem_om);

				// chamada do método para aplicar o efeito de dividir duas imagens e retorna a
				// imagem resultado para o "panelDaImagem_om"
				// podendo assim ser exibida
				panelDaImagem_om.divisao(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem,
						panelDaImagem2.largura, panelDaImagem2.altura, panelDaImagem2.matrizImagem);

				lblFiltro.setText("Operação de Divisão");
				lblDica.setText("INFORMAÇÃO: Utilizado para normalizar o brilho.");
			}

		});

		JMenu mnOperaesLgicas = new JMenu("Operações Lógicas");// Menu das operações lógicas
		menuBar.add(mnOperaesLgicas);

		JMenuItem and = new JMenuItem("And"); // Botão para aplicar um "And" entre duas imagens
		mnOperaesLgicas.add(and);
		and.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				logico = true;
				panelDaImagem_ol.setBounds(0, 0, 256, 256);
				panelDaImagem_ol.setVisible(logico);
				panel_3.add(panelDaImagem_ol);

				// chamada do método para aplicar o efeito de "and" duas imagens e retorna a
				// imagem resultado para o "panelDaImagem_ol"
				// podendo assim ser exibida
				panelDaImagem_ol.logica_and(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem,
						panelDaImagem2.largura, panelDaImagem2.altura, panelDaImagem2.matrizImagem);

				lblFiltro.setText("AND Lógico");
			}

		});

		JMenuItem or = new JMenuItem("Or"); // Botão para aplicar um "or" entre duas imagens
		mnOperaesLgicas.add(or);
		or.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				logico = true;
				panelDaImagem_ol.setBounds(0, 0, 256, 256);
				panelDaImagem_ol.setVisible(logico);
				panel_3.add(panelDaImagem_ol);

				// chamada do método para aplicar o efeito de "or" duas imagens e retorna a
				// imagem resultado para o "panelDaImagem_ol"
				// podendo assim ser exibida
				panelDaImagem_ol.logica_or(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem,
						panelDaImagem2.largura, panelDaImagem2.altura, panelDaImagem2.matrizImagem);

				lblFiltro.setText("OR Lógico");
			}

		});

		JMenuItem xor = new JMenuItem("Xor");// Botão para aplicar "xor" entre duas imagens
		mnOperaesLgicas.add(xor);
		xor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				logico = true;
				panelDaImagem_ol.setBounds(0, 0, 256, 256);
				panelDaImagem_ol.setVisible(logico);
				panel_3.add(panelDaImagem_ol);

				// chamada do método para aplicar o efeito de "xor" duas imagens e retorna a
				// imagem resultado para o "panelDaImagem_ol"
				// podendo assim ser exibida
				panelDaImagem_ol.logica_xor(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem,
						panelDaImagem2.largura, panelDaImagem2.altura, panelDaImagem2.matrizImagem);

				lblFiltro.setText("XOR Lógico");
			}

		});
		
		JMenu transformacoes = new JMenu("Transformações"); // menu efeito de transformações
		menuBar.add(transformacoes);

		JMenuItem gamma = new JMenuItem("Gamma"); // botao transformacao gamma
		transformacoes.add(gamma);
		gamma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);
				double gamma = 10;
				gamma = Double.parseDouble(JOptionPane.showInputDialog("Gamma: ", gamma));

				panelDaImagem_ef.negativo_gamma(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem, gamma);

				lblFiltro.setText("Gamma");
			}

		});

		JMenuItem logaritmica = new JMenuItem("Logarítmica"); // botao transformacao logaritmica
		transformacoes.add(logaritmica);
		logaritmica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);
				double a = 2;
				//a eh constante
				a = Double.parseDouble(JOptionPane.showInputDialog("Constante: ", a));
				panelDaImagem_ef.negativo_logaritmico(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem, a);

				lblFiltro.setText("Logarítmica");
			}

		});

		JMenuItem itf_sigmoide = new JMenuItem("ITF Sigmoide"); // botao da  função de transferência de intensidade geral
		transformacoes.add(itf_sigmoide);
		itf_sigmoide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);
				// centro dos valores de cinza
				int w = Integer.parseInt(JOptionPane.showInputDialog("Preencha com o valor w", "127"));
				// e 𝜎 a largura da janela
				int sigma = Integer.parseInt(JOptionPane.showInputDialog("Preencha com o valor 𝜎 ", "25"));
				panelDaImagem_ef.itf_sigmoide(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem,w,sigma);

				lblFiltro.setText("ITF Sigmoide - Transferência de Intensidade Geral");
			}

		});

		JMenuItem faixa_dinamica = new JMenuItem("Faixa Dinamica"); // botao da função de transferência faixa dinâmica
		transformacoes.add(faixa_dinamica);
		faixa_dinamica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);
				int w = 255;
				w = Integer.parseInt(JOptionPane.showInputDialog("w_target: ", w));
				panelDaImagem_ef.faixa_dinamica(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem, w);

				lblFiltro.setText("Transferência de Faixa Dinâmica");
				lblDica.setText("<html>INFORMAÇÃO: Para a imagem ser melhorada Tmin deveria estar 1 e tom máximo, Tmax, em 255. Isto é, ao invés de apenas ser representada no pequeno intervalo (Tmax-Tmin) deveria cobrir todo o intervalo 255.</html>");
			}

		});

		JMenuItem transformacao_linear = new JMenuItem("Transferência Linear"); //botao da função de tranferencia linear
		transformacoes.add(transformacao_linear);
		transformacao_linear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				efeitos1 = true;
				panelDaImagem_ef.setBounds(0, 0, 256, 256);
				panelDaImagem_ef.setVisible(efeitos1);
				panel_3.add(panelDaImagem_ef);
				int contraste = 1;
				int brilho = 1;
				contraste = Integer
						.parseInt(JOptionPane.showInputDialog("Adicione um valor para o contraste: ", contraste));
				brilho = Integer.parseInt(JOptionPane.showInputDialog("Adicione um valor para o brilho: ", brilho));
				panelDaImagem_ef.transformacao_linear(panelDaImagem1.largura, panelDaImagem1.altura,
						panelDaImagem1.matrizImagem, contraste, brilho);

				lblFiltro.setText("Transferência Linear");
			}
		});

		JMenu morfologia = new JMenu("Morfologia");
		menuBar.add(morfologia);
		
		JMenu mmnNC = new JMenu("Nível de Cinza"); //Menu das Operaçoes morf em imagem NC
		morfologia.add(mmnNC);
		
		
		JMenuItem dilatar = new JMenuItem("Dilatação");
		mmnNC.add(dilatar);
		dilatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					String times = JOptionPane.showInputDialog("Preencha com o número de vezes que deseja aplicar", "2");
					int timesInt = Integer.parseInt(times);
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					parcial = panelDaImagem_mor.dilate(parcial, timesInt, CROSS);
					
					String[] mascara = {"0", "1", "0", "1", "1", "1", "0", "1", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Dilatar");
					lblDica.setText("INFORMAÇÃO: Utilizado para preencher buracos. Permite conectar componentes próximos.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "O valor de entrada deve ser um inteiro!");
				}
			}
		});
		
		JMenuItem erodir = new JMenuItem("Erosão");
		mmnNC.add(erodir);
		erodir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setVisible(mor);
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panel_3.add(panelDaImagem_mor);

				try {
					String times = JOptionPane.showInputDialog("Preencha com o número de vezes que deseja aplicar", "2");
					int timesInt = Integer.parseInt(times);
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					parcial = panelDaImagem_mor.erode(parcial, timesInt, CROSS);
					
					String[] mascara = {"0", "1", "0", "1", "1", "1", "0", "1", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Erodir");
					lblDica.setText("INFORMAÇÃO: Utilizado para aumentar buracos. Permite a separação de componentes conectados.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "O valor de entrada deve ser um inteiro!");
				}
			}
		});
		
		JMenuItem gradienteMor = new JMenuItem("Gradiente Morfológico");
		mmnNC.add(gradienteMor);
		gradienteMor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setVisible(mor);
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panel_3.add(panelDaImagem_mor);

				try {
					BufferedImage erosao = converterBuffered(panelDaImagem1.matrizImagem);
					erosao = panelDaImagem_mor.erodir(erosao, CROSS);
					
					BufferedImage dilatacao = converterBuffered(panelDaImagem1.matrizImagem);
					dilatacao = panelDaImagem_mor.dilatar(dilatacao, CROSS);
					
					panelDaImagem_mor.subtract(dilatacao, erosao);
					
					lblFiltro.setText("Gradiente Morfologico");
					lblDica.setText("<html>INFORMAÇÃO: Enfatiza as transições marcadas nos níveis de cinza da imagem de entrada. Tendem a depender menos da direção das bordas em comparação com Sobel.</html>");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro inesperado!");
				}
			}
		});
		
		JMenuItem abertura = new JMenuItem("Abertura");
		mmnNC.add(abertura);
		abertura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setVisible(mor);
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panel_3.add(panelDaImagem_mor);

				try {
					String times = JOptionPane.showInputDialog("Preencha com o número de vezes que deseja aplicar", "2");
					int timesInt = Integer.parseInt(times);
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					parcial = panelDaImagem_mor.abertura(parcial, timesInt, CROSS);
					
					lblFiltro.setText("Abertura");
					lblDica.setText("<html>INFORMAÇÃO: A abertura elimina pequenos componentes e suaviza o contorno. Deste modo, a abertura de A por B consiste na erosão de A por B\r\n" + 
							"seguida da dilatação do resultado por B.<html>");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "O valor de entrada deve ser um inteiro!");
				}
			}
		});
		
		JMenuItem fechamento = new JMenuItem("Fechamento");
		mmnNC.add(fechamento);
		fechamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setVisible(mor);
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panel_3.add(panelDaImagem_mor);

				try {
					String times = JOptionPane.showInputDialog("Preencha com o número de vezes que deseja aplicar", "2");
					int timesInt = Integer.parseInt(times);
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					parcial = panelDaImagem_mor.fechamento(parcial,timesInt,CROSS);
					
					lblFiltro.setText("Fechamento");
					lblDica.setText("<html>INFORMAÇÃO: O fechamento fecha pequenos buracos e conecta componentes. Deste modo, o fechamento de A por B consiste na dilatação de A por\r\n" + 
							"B seguida da erosão do resultado por B.</html>");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "O valor de entrada deve ser um inteiro!");
				}
			}
		});
		
		JMenuItem topHat = new JMenuItem("Top-Hat"); // Botao top hat
		mmnNC.add(topHat);
		topHat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setVisible(mor);
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panel_3.add(panelDaImagem_mor);

				try {
					BufferedImage imagem = converterBuffered(panelDaImagem1.matrizImagem);
					BufferedImage abertura = converterBuffered(panelDaImagem1.matrizImagem);

					String times = JOptionPane.showInputDialog("Preencha com o número de vezes que deseja aplicar", "1");
					int timesInt = Integer.parseInt(times);
					
					abertura = panelDaImagem_mor.abertura(abertura, timesInt, CROSS);
					panelDaImagem_mor.subtract(imagem, abertura);
					
					lblFiltro.setText("Top-Hat");
					lblDica.setText("INFORMAÇÃO: Enfatizar  o  detalhe  na  presença  desombreamento.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "O valor de entrada deve ser um inteiro!");
				}
			}
		});
		
		JMenuItem bottomHat = new JMenuItem("Bottom-Hat");
		mmnNC.add(bottomHat);
		bottomHat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setVisible(mor);
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panel_3.add(panelDaImagem_mor);

				try {
					BufferedImage imagem = converterBuffered(panelDaImagem1.matrizImagem);
					BufferedImage fechamento = converterBuffered(panelDaImagem1.matrizImagem);

					String times = JOptionPane.showInputDialog("Preencha com o número de vezes que deseja aplicar", "2");
					int timesInt = Integer.parseInt(times);
					
					fechamento = panelDaImagem_mor.abertura(fechamento, timesInt, CROSS);
					panelDaImagem_mor.subtract(fechamento, imagem);
					
					lblFiltro.setText("Bottom-Hat");
					lblDica.setText("INFORMAÇÃO: Enfatizar  o  detalhe  na  presença  desombreamento.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "O valor de entrada deve ser um inteiro!");
				}
			}
		});
		
		JMenu mmnBinario = new JMenu("Binária"); //Menu das Operaçoes morf em imagem binaria
		morfologia.add(mmnBinario);
		
		JMenuItem dilatarMascara = new JMenuItem("Dilatação");
		mmnBinario.add(dilatarMascara);
		dilatarMascara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para dilatar
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.dilatarBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Dilatação");
					lblDica.setText("INFORMAÇÃO: Utilizado para preencher buracos. Permite conectar componentes próximos.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		JMenuItem erodirMascara = new JMenuItem("Erosão");
		mmnBinario.add(erodirMascara);
		erodirMascara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para erodir
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.erodirBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Erosão");
					lblDica.setText("INFORMAÇÃO: Tende a aumentar buracos. Permite a separação de componentes conectados.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		JMenuItem fechamentoB = new JMenuItem("Fechamento");//Botão para aplicar fechamento na imagem binaria
		mmnBinario.add(fechamentoB);
		fechamentoB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para fechamento
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.fechamentoBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Fechamento");
					lblDica.setText("<html>INFORMAÇÃO: O fechamento fecha pequenos buracos e conecta componentes. Deste modo, o fechamento de A por B consiste na dilatação de A por\r\n" + 
							"B seguida da erosão do resultado por B.</html>");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		JMenuItem aberturaB = new JMenuItem("Abertura");//Botão para aplicar abertura na imagem binaria
		mmnBinario.add(aberturaB);
		aberturaB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para abertura
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.aberturaBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Abertura");
					lblDica.setText("<html>INFORMAÇÃO: A abertura elimina pequenos componentes e suaviza o contorno. Deste modo, a abertura de A por B consiste na erosão de A por B\r\n" + 
							"seguida da dilatação do resultado por B.<html>");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		
		JMenuItem hitormissB = new JMenuItem("Hit-or-Miss");//Botão para aplicar hitormiss na imagem binaria
		mmnBinario.add(hitormissB);
		hitormissB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para hitormiss
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.hitormissBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Hit-Or-Miss");
					lblDica.setText("INFORMAÇÃO: ");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		
		JMenuItem contExtB = new JMenuItem("Contorno Externo");//Botão para aplicar contorno externo na imagem binaria
		mmnBinario.add(contExtB);
		contExtB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para contorno externo
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.contExtBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Contorno Externo");
					lblDica.setText("INFORMAÇÃO: Exibe a borda externa da imagem após uma dilatação.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		JMenuItem contIntB = new JMenuItem("Contorno Interno");//Botão para aplicar contorno interno na imagem binaria
		mmnBinario.add(contIntB);
		contIntB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para contorno interno
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.contIntBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Contorno Interno");
					lblDica.setText("INFORMAÇÃO: Exibe a borda real da imagem retirando o resultado da erosão.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		JMenuItem gradienteB = new JMenuItem("Gradiente Morfológico");//Botão para aplicar gradiente Morfológico na imagem binaria
		mmnBinario.add(gradienteB);
		gradienteB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				mor = true;
				panelDaImagem_mor.setBounds(0, 0, 256, 256);
				panelDaImagem_mor.setVisible(mor);
				panel_3.add(panelDaImagem_mor);


				try {
					//Elemento estruturante para o gradiente
					int[][] elementoEstruturante = new int[3][2];
					elementoEstruturante[0][0] = 0;
					elementoEstruturante[0][1] = -1;
					elementoEstruturante[1][0] = 0;
					elementoEstruturante[1][1] = 0;
					elementoEstruturante[2][0] = 0;
					elementoEstruturante[2][1] = 1;
					
					BufferedImage parcial = converterBuffered(panelDaImagem1.matrizImagem);
					panelDaImagem_mor.gradMorfBin(panelDaImagem1.largura, panelDaImagem1.altura, panelDaImagem1.matrizImagem, elementoEstruturante);
					
					String[] mascara = { "0", "0", "0", "1", "X", "1", "0", "0", "0"};
					plotaMascara(mascara);
					
					lblFiltro.setText("Gradiente Morfológico");
					lblDica.setText("<html>INFORMAÇÃO: Enfatiza as transições marcadas da imagem de entrada.</html>");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Estouro no array!");
				}
			}
		});
		
		JMenu histograma = new JMenu("Histograma");
		menuBar.add(histograma);

		JMenuItem equalizar = new JMenuItem("Equalizar");
		histograma.add(equalizar);
		equalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();
				apresenta2Imagem();
				removeDaTelaNormalizado(true);
				
				panel_Pontos_1.setVisible(false);
				panel_Pontos_3.setVisible(false);
				panel_Pontos_4.setVisible(false);
				lblMenorValor.setVisible(false);
				lblMaiorValor.setVisible(false);
				

				his = true;
				panelDaImagem_h.setBounds(0, 0, 256, 256);
				panelDaImagem_h.setVisible(his);
				panel_3.add(panelDaImagem_h);
			try {
				panelDaImagem_h.recebeImagem(panelDaImagem1.largura, panelDaImagem1.altura,panelDaImagem1.matrizImagem);
				
				lblFiltro.setText("Equalizar Histograma");
				lblDica.setText("<html>DICA: A equalização de histograma é uma ação para mudar a distribuição dos valores de ocorrência em um histograma permitindo uma redução das diferenças acentuadas e assim, particularmente em imagens, acentuando detalhes não visíveis anteriormente.</html>");
			} catch (Exception IllegalArgumentException) {
				JOptionPane.showMessageDialog(null, "É necessário adicionar imagem.");
			}
			}

		});

		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);

		JMenuItem mntmEquippe = new JMenuItem("Equipe");
		mnAjuda.add(mntmEquippe);
		mntmEquippe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpaInformacoes();

				String team = "Mayara Medeiros\n" + "Matheus Cavalcante";
				JOptionPane.showMessageDialog(null, team);
			}
		});

		JPanel painel1 = new JPanel();
		painelPrincipal.add(painel1);
		painel1.setLayout(null);

		panelDaImagem1.setBounds(30, 30, 256, 256);
		panelDaImagem1.setVisible(true);
		painel1.add(panelDaImagem1);

		JButton btAbrirImagem1 = new JButton("Abrir Imagem");// Botão abrir imagem
		btAbrirImagem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					// Instanciacao de fileChooser e alteracao do diretorio para buscar a imagem
					final JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("src/"));

					// Verificacao do fileChooser
					if (fileChooser.showOpenDialog(btAbrirImagem1) == JFileChooser.APPROVE_OPTION) {

						// Cria um file onde eh armazenada a imagem
						File file = fileChooser.getSelectedFile();

						apresenta2Imagem();
						panelDaImagem1.colocaImagemNoPainel(file.getPath());
						panelDaImagem1.plotaPixels(panel_Pontos_1);
					}

				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Não foi possivel carregar a imagem.");

				}
			}
		});

		btAbrirImagem1.setBounds(150, 403, 131, 33);
		painelPrincipal.add(btAbrirImagem1);

		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_2.setBounds(384, 122, 256, 256);
		painelPrincipal.add(panel_2);
		panel_2.setLayout(null);

		panelDaImagem2.setBounds(0, 0, 256, 256);
		panelDaImagem2.setVisible(true);
		panel_2.add(panelDaImagem2);

		panelDaImagem3.setBounds(0, 0, 256, 256);
		panelDaImagem3.setVisible(true);
		panel_3.add(panelDaImagem3);

		panelDaImagem4.setBounds(0, 0, 256, 256);
		panelDaImagem4.setVisible(true);
		panel_4.add(panelDaImagem4);

		JButton btAbrirImagem2 = new JButton("Abrir Imagem");
		btAbrirImagem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					// Instanciacao de fileChooser e alteracao do diretorio para buscar a imagem
					final JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("src/"));

					// Verificacao do fileChooser
					if (fileChooser.showOpenDialog(btAbrirImagem2) == JFileChooser.APPROVE_OPTION) {
						// Cria um file onde eh armazenada a imagem
						File file = fileChooser.getSelectedFile();

						panelDaImagem2.colocaImagemNoPainel(file.getPath());
						panelDaImagem2.plotaPixels(panel_Pontos_2);
						apresenta2Imagem();
						repaint();
						validate();
					}

				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Não foi possivel carregar a imagem.");
				}
			}
		});

		btAbrirImagem2.setBounds(450, 403, 131, 33);
		painelPrincipal.add(btAbrirImagem2);

		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(743, 406, 163, 24);
		painelPrincipal.add(lblNewLabel);
		panelDaImagem1.setBounds(86, 122, 256, 256);

		panel_Pontos_1.setBounds(87, 456, 261, 261);
		panel_Pontos_2.setBounds(384, 456, 261, 261);
		panel_Pontos_3.setBounds(681, 456, 261, 261);
		painelPrincipal.add(panel_Pontos_1);
		painelPrincipal.add(panel_Pontos_2);
		painelPrincipal.add(panel_Pontos_3);

		panel_4.setLayout(null);
		panel_4.setBorder(BorderFactory.createEtchedBorder());
		panel_4.setBounds(973, 122, 256, 256);
		panel_4.setVisible(false);
		painelPrincipal.add(panel_4);

		lblResultadoNormalizado = new JLabel("Resultado Normalizado");
		lblResultadoNormalizado.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoNormalizado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResultadoNormalizado.setBounds(1024, 406, 163, 24);
		lblResultadoNormalizado.setVisible(false);
		painelPrincipal.add(lblResultadoNormalizado);

		panel_Pontos_4.setBounds(973, 456, 261, 261);
		panel_Pontos_4.setVisible(false);
		painelPrincipal.add(panel_Pontos_4);
		lblDica.setHorizontalAlignment(SwingConstants.CENTER);

		lblDica.setBounds(384, 51, 845, 46);
		painelPrincipal.add(lblDica);

		panelMascara.setBounds(168, 28, 74, 80);
		painelPrincipal.add(panelMascara);
		lblMenorValor.setHorizontalAlignment(SwingConstants.CENTER);

		lblMenorValor.setBounds(973, 730, 261, 16);
		painelPrincipal.add(lblMenorValor);
		lblMaiorValor.setHorizontalAlignment(SwingConstants.CENTER);

		lblMaiorValor.setBounds(973, 755, 261, 16);
		painelPrincipal.add(lblMaiorValor);
		
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFiltro.setHorizontalAlignment(SwingConstants.CENTER);
		lblFiltro.setBounds(549, 13, 482, 25);
		painelPrincipal.add(lblFiltro);
		
		painelPrincipal.add(panelDaImagem1);
		panelDaImagem1.setVisible(true);
	}

	/**
	 * Remove a 2 imagem e/ou a parte da imagem normalizada
	 */
	public static void removeElementos() {
		if (efeitos1 || mor) {
			panel_Pontos_2.setVisible(false);
			panel_2.setVisible(false);
			panelDaImagem2.setVisible(false);

		} else {
			apresenta2Imagem();
		}
	}

	public static BufferedImage converterBuffered(int[][] mtzImg) {
		// matriz que vc passa como parâmetro definindo a largura da imagem
		int largura = mtzImg.length,
				// matriz que vc passa como parâmetro definindo a altura da imagem
				altura = mtzImg[0].length;

		// criando uma objeto BufferedImage a partir das dimensões da imagem
		// representada pela matriz
		BufferedImage image = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = image.getRaster();
		for (int h = 0; h < largura; h++) {
			for (int w = 0; w < altura; w++) {
				raster.setSample(h, w, 0, mtzImg[h][w]);
			}
		}

		return image;
	}

	public int corPixel(int corRGB) {
		Color cor = new Color(corRGB, corRGB, corRGB);
		return cor.getRGB();
	}

	/**
	 * Oculta o painel da imagem/pontos normalizados Será utilizado para o histograma
	 *  
	 * @param tf - True para apresentar. False para ocultar
	 */
	public static void removeDaTelaNormalizado(boolean tf) {
		lblResultadoNormalizado.setText("Resultado Normalizado");
		panel_4.setVisible(tf);
		lblResultadoNormalizado.setVisible(tf);
		panel_Pontos_4.setVisible(tf);
	}

	/**
	 * Apresenta a segunda imagem Não apresenta para operações que utilizam apenas 1
	 * imagem, como os efeitos
	 */
	public static void apresenta2Imagem() {
		panel_2.setVisible(true);
		panelDaImagem2.setVisible(true);
		panel_Pontos_2.setVisible(true);
	}

	private void limpaInformacoes() {
		lblDica.setText("");
		lblFiltro.setText("");
		panelMascara.setVisible(false);
		lblNewLabel.setText("Imagem Resultado");
		

		logico = false;
		aritmeticos = false;
		efeitos1 = false;
		his = false;
		mor = false;
		panelDaImagem_h.setVisible(false);
		panelDaImagem_om.setVisible(false);
		panelDaImagem_ol.setVisible(false);
		panelDaImagem_ef.setVisible(false);
		panelDaImagem_mor.setVisible(false);
	}

	public void plotaMascara(String[] mascara) {
		removeElementos();
		panelMascara.removeAll();
		panelMascara.setLayout(new GridLayout(3, 3));
		panelMascara.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panelMascara.setVisible(true);

		for (int i = 0; i < mascara.length; i++) {
			String text = mascara[i];
			JLabel label = new JLabel(text, SwingConstants.CENTER);
			label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			panelMascara.add(label);
		}
		panelMascara.validate();
	}
}
