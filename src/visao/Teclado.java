package visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Teclado extends JPanel{
	private final Color CINZA_ESCURO = new Color(68,68,68);
	private final Color CINZA_CLARO = new Color(99,99,99);
	private final Color LARANJA = new Color(242,163,60);
	
	public Teclado() {
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		setLayout(layout);
		
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1;
		cons.weighty = 1;
		
		adicionarBotao("AC",CINZA_ESCURO,cons,0,0);
		adicionarBotao("+/-",CINZA_ESCURO,cons,1,0);
		adicionarBotao("%",CINZA_ESCURO,cons,2,0);
		adicionarBotao("/",LARANJA,cons, 3, 0);
		
		
		adicionarBotao("7",CINZA_CLARO,cons, 0, 1);
		adicionarBotao("8",CINZA_CLARO,cons, 1, 1);
		adicionarBotao("9",CINZA_CLARO,cons, 2, 1);
		adicionarBotao("*",LARANJA,cons, 3, 1);
		
		adicionarBotao("4",CINZA_CLARO,cons, 0, 2);
		adicionarBotao("5",CINZA_CLARO,cons, 1, 2);
		adicionarBotao("6",CINZA_CLARO,cons, 2, 2);
		adicionarBotao("-",LARANJA,cons, 3, 2);
		
		adicionarBotao("1",CINZA_CLARO,cons, 0, 3);
		adicionarBotao("2",CINZA_CLARO,cons, 1, 3);
		adicionarBotao("3",CINZA_CLARO,cons, 2, 3);
		adicionarBotao("+",LARANJA,cons, 3, 3);
		cons.gridwidth = 2;
		adicionarBotao("0",CINZA_CLARO,cons, 0, 4);
		cons.gridwidth = 1;
		adicionarBotao(",",CINZA_CLARO,cons, 2, 4);
		adicionarBotao("=",LARANJA,cons, 3, 4);
			
	}
	private void adicionarBotao(String texto, Color cor, GridBagConstraints cons, int x, int y) {
		cons.gridx = x;
		cons.gridy = y;
		Botao botao = new Botao(texto,cor);
		add(botao,cons);
	}
}
