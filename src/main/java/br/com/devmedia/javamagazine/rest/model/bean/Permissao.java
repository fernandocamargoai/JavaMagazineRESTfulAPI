package br.com.devmedia.javamagazine.rest.model.bean;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Permissao {

	@NotNull
    private String nome;
    @NotNull
    private String dominios;
    @NotNull
    private String operacoes;

    public Set<String> getDominiosSet(){
    	if(dominios == null){
    		return Collections.emptySet();
    	}
    	return new HashSet<String>(Arrays.asList(dominios.split(",")));
    }
    
    public void setDominiosSet(Set<String> dominiosSet){
    	StringBuilder stringBuilder = new StringBuilder();
		for(String dominio : dominiosSet){
			stringBuilder.append(dominio.toString());
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		dominios = stringBuilder.toString();
    }
    
    public Set<String> getOperacoesSet(){
    	if(operacoes == null){
    		return Collections.emptySet();
    	}
    	return new HashSet<String>(Arrays.asList(operacoes.split(",")));
    }
    
    public void setOperacoesSet(Set<String> operacoesSet){
    	StringBuilder stringBuilder = new StringBuilder();
		for(String operacao : operacoesSet){
			stringBuilder.append(operacao.toString());
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		operacoes = stringBuilder.toString();
    }
    
    public String getStringPermissao(){
    	return String.format("%s:%s", dominios, operacoes);
    }
    
	@Override
	public String toString() {
		return nome;
	}
    
}
