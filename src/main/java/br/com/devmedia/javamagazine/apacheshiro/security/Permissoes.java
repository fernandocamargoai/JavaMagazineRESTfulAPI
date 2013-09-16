package br.com.devmedia.javamagazine.apacheshiro.security;

import java.util.Arrays;
import java.util.List;

public final class Permissoes {

	public static final class Dominio {
		public static final String TUDO = "*";
		public static final String USUARIO = "usuario";
		public static final String COMENTARIO = "comentario";
		public static final String PAPEL = "papel";
		public static final String PERMISSAO = "permissao";
		
		public static final List<String> dominios;
		
		static{
			dominios = Arrays.asList(new String[]{
				TUDO,
				USUARIO,
				COMENTARIO,
				PAPEL,
				PERMISSAO
			});
		}
	}
	
	public static final class Operacao {
		public static final String TUDO = "*";
		public static final String CRIAR = "criar";
		public static final String ATUALIZAR = "atualizar";
		public static final String DELETAR = "deletar";
		public static final String LER = "ler";
		
		public static final List<String> operacoes;
		
		static{
			operacoes = Arrays.asList(new String[]{
				TUDO,
				CRIAR,
				ATUALIZAR,
				DELETAR,
				LER
			});
		}
	}
}
