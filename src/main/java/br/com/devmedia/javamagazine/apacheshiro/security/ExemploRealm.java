package br.com.devmedia.javamagazine.apacheshiro.security;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Papel;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Permissao;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Usuario;

public class ExemploRealm extends AuthorizingRealm {

	@Override
	public boolean supports(AuthenticationToken token) {
		// Suporta apenas tokens do tipo UsernamePasswordToken
		return token instanceof UsernamePasswordToken;
	}

	/**
	 * Método responsável por autenticar um usuário de acordo com o token
	 * submetido.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		Usuario usuario = null;
		try{
			// Busca o usuário pelo seu login através do username submetido
			usuario = Usuario.findUsuariosByLoginEquals(token.getUsername()).getSingleResult();
		}
		catch(EmptyResultDataAccessException e){
			throw new UnknownAccountException();
		}
		
		
		if(usuario != null){
			/*
			 * Caso o usuário seja encontrado, retorna uma instância de
			 * SimpleAuthenticationInfo com o id do usuário como principal,
			 * a senha do usuário (que será comparada com a senha submetida),
			 * o salt usado no hash da senha do usuário e o nome do usuário.
			 */
			return new SimpleAuthenticationInfo(usuario.getId(), usuario.getSenha(), ByteSource.Util.bytes(Base64.decode(usuario.getSalt())), usuario.getNome());
		}
		// Caso nenhum usuário seja encontrado, retorna null.
		return null;
	}
	
	/**
	 * Método responsável por recuperar os papéis e as permissões de dado
	 * usuário (que foi previamente autenticado).
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// Obtem o id do usuário que foi armazenado como principal
		Long idUsuario = (Long) principalCollection.getPrimaryPrincipal();
		// Obtem o usuário
		Usuario usuario = Usuario.findUsuario(idUsuario);
		
		if(usuario != null){
			// Cria uma instância de SimpleAuthorizationInfo a ser preenchida
			// com as permissões e papéis do usuário.
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			
			// Adiciona as permissões vinculadas diretamente ao usuário.
			adicionePermissoes(usuario.getPermissoes(), info);
			
			// Percorre a lista de papéis, adicionando-os e adicionando as
			// permissões vinculadas a eles.
			for(Papel papel : usuario.getPapeis()){
				adicionePermissoes(papel.getPermissoes(), info);
				info.addRole(papel.getNome());
			}
			
			return info;
		}
		
		return null;
	}

	/**
	 * Método utilitário que percorre a lista de permissões e adiciona
	 * a String da permissão no formato esperado pelo Apache Shiro.
	 */
	private void adicionePermissoes(Collection<Permissao> permissoes,
			SimpleAuthorizationInfo info) {
		for(Permissao permissao : permissoes){
			info.addStringPermission(permissao.getStringPermissao());
		}
	}

}
