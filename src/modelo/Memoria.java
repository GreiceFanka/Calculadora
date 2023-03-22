package modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando{
		ZERAR,NUMERO,SUB,DIV,MULTI,SOMA,PORCENTAGEM,IGUAL,VIRGULA;
	};
	
	private static Memoria instancia = new Memoria();
	private String textoAtual = "";
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	
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
		textoAtual += texto;
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
		System.out.println(tipoComando);
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
			}else if("%".equals(texto)) {
				return TipoComando.PORCENTAGEM;
			}else if(",".equals(texto)) {
				return TipoComando.VIRGULA;
			}
		}
		return null;
	}
}
