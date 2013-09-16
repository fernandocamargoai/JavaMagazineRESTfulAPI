package br.com.devmedia.javamagazine.apacheshiro.service;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Usuario;

@Service("usuarioService")
public class UsuarioService {

	public static final String USUARIO_CORRENTE = "usuarioCorrente";
	
	@Value("${criptografia.numeroDeIteracoesDeHash}")
	private int numeroDeIteracoesDeHash;
	
	@Inject
	private RandomNumberGenerator randomNumberGenerator;
	
	public void cadastre(Usuario usuario){
		ByteSource salt = randomNumberGenerator.nextBytes();

        String hashedPasswordBase64 = new Sha512Hash(usuario.getSenha(), salt, numeroDeIteracoesDeHash).toBase64();
        usuario.setSenha(hashedPasswordBase64);
        usuario.setSalt(salt.toBase64());
        usuario.persist();
	}
	
	public Usuario getUsuarioCorrente(){
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			return null;
		}
		Usuario usuario = (Usuario) subject.getSession().getAttribute(USUARIO_CORRENTE);
		if(usuario == null){
			Long usuarioId = (Long) subject.getPrincipal();
			usuario = Usuario.findUsuario(usuarioId);
			subject.getSession().setAttribute(USUARIO_CORRENTE, usuario);
		}
		return usuario;
	}
	
}
