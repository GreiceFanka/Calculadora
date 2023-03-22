package modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando{
		ZERAR,NUMERO,SUB,DIV,MULTI,SOMA,PORCENTAGEM,IGUAL,VIRGULA;
	};
	
	private static Memoria instancia = new Memoria();
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";
	private TipoComando ultimaOperacao = null;
	private TipoComando ultimaOperacaoBuffer = null;
	
	private Memoria() {
		
	}
	
	public static Memoria getInstancia() {
		return instancia;
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty()? "0" : textoAtual;
	}
	
	public void adicionarObservador(MemoriaObservador obs) {
		observadores.add(obs);
	}
	
	public void processarComando(String texto) {
		TipoComando tipoComando = detectarTipoComando(texto);
		if(tipoComando == null) {
			return;
		}else if(tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}else if(tipoComando == TipoComando.NUMERO || tipoComando == TipoComando.VIRGULA) {
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
		}else if(tipoComando == TipoComando.PORCENTAGEM) {
			try {
				textoAtual = substituir ? texto : textoAtual + texto;
				substituir = false;
				ultimaOperacao = tipoComando;
				textoAtual = obterResultadoOperacao();
				textoBuffer = textoAtual;
			} catch (Exception e) {
				// Erro de formato inserido
				textoAtual = "FormatError!";
			}
		}else {
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
			ultimaOperacaoBuffer = ultimaOperacao;
		}
		
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
		
	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		if(textoBuffer.contains("%") || textoAtual.contains("%")) {
			textoAtual = textoAtual.replace("%", "");
			textoBuffer = textoBuffer.replace("%", "");
		}
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
		double resultado = 0;
		if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		}else if(ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		}else if(ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		}else if(ultimaOperacao == TipoComando.MULTI) {
			resultado = numeroBuffer * numeroAtual;
		}else if(ultimaOperacao == TipoComando.PORCENTAGEM) {
			if(ultimaOperacaoBuffer == TipoComando.SUB) {
				resultado = numeroBuffer - (numeroBuffer * numeroAtual)/100;
			}else if(ultimaOperacaoBuffer == TipoComando.SOMA) {
				resultado = numeroBuffer + (numeroBuffer * numeroAtual /100);
			}else if(ultimaOperacaoBuffer == TipoComando.DIV) {
				resultado = numeroBuffer / (numeroBuffer * numeroAtual /100) * 100;
			}else if(ultimaOperacaoBuffer == TipoComando.MULTI) {
				resultado = (numeroBuffer * numeroAtual /100);
			}
		}
		String resultadoString = Double.toString(resultado).replace(".", ",");
		boolean inteiro = resultadoString.endsWith(",0");
		return inteiro ? resultadoString.replace(",0","") : resultadoString;
	}

	private TipoComando detectarTipoComando(String texto) {
		if(textoAtual.isEmpty() && texto == "0") {
			return null;
		}
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
			
		} catch (NumberFormatException e) {
			if("AC".equals(texto)) {
				return TipoComando.ZERAR;
			}else if("/".equals(texto)) {
				return TipoComando.DIV;
			}else if("+".equals(texto)) {
				return TipoComando.SOMA;
			}else if("-".equals(texto)) {
				return TipoComando.SUB;
			}else if("*".equals(texto)) {
				return TipoComando.MULTI;
			}else if("=".equals(texto)) {
				return TipoComando.IGUAL;
			}else if(texto.contains("%")) {
				return TipoComando.PORCENTAGEM;
			}else if(",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}
		}
		return null;
	}
}
