package br.com.devmedia.javamagazine.apacheshiro.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Usuario;
import br.com.devmedia.javamagazine.apacheshiro.service.UsuarioService;

@Component("usuarioCorrenteInterceptor")
public class UsuarioCorrenteInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private UsuarioService usuarioService;
	
	@Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // Add the current user into the request
        Usuario usuarioCorrente = usuarioService.getUsuarioCorrente();
        if( usuarioCorrente != null ) {
            httpServletRequest.setAttribute(UsuarioService.USUARIO_CORRENTE, usuarioCorrente );
        }
    }
	
}
