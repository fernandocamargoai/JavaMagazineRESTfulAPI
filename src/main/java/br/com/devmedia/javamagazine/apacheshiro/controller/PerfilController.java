package br.com.devmedia.javamagazine.apacheshiro.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Usuario;
import br.com.devmedia.javamagazine.apacheshiro.service.UsuarioService;

@RequestMapping("/perfil")
@Controller
public class PerfilController {

	@Inject
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(Usuario usuario, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
            return createForm(uiModel);
        }
        uiModel.asMap().clear();
        
        usuarioService.cadastre(usuario);
        return "redirect:/perfil?formlogin";
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
		Usuario usuarioCorrente = usuarioService.getUsuarioCorrente();
		if(usuarioCorrente == null){
			usuarioCorrente = new Usuario();
		}
        populateEditForm(uiModel, usuarioCorrente);
        return "perfil/create";
    }
	
	@RequestMapping(value="/login", method = RequestMethod.POST, produces = "text/html")
    public String login(Usuario usuario, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		if (bindingResult.hasErrors()) {
            return loginForm(uiModel);
        }
        uiModel.asMap().clear();
        
        try{
        	UsernamePasswordToken token = new UsernamePasswordToken(usuario.getLogin(), usuario.getSenha());
            SecurityUtils.getSubject().login(token);
            
            try {
				org.apache.shiro.web.util.WebUtils.redirectToSavedRequest(httpServletRequest, httpServletResponse, "/perfil");
			} catch (IOException e) {
				return "redirect:/perfil";
			}
            return null; //tells Spring MVC you've handled the response, and not to render a view
        }
        catch(AuthenticationException e){
        	e.printStackTrace();
        	return loginForm(uiModel);
        }
    }
	
	@RequestMapping(params = "formlogin", produces = "text/html")
	public String loginForm(Model uiModel){
		populateLoginForm(uiModel, new Usuario());
		return "perfil/login";
	}
	
	@RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
	
	@RequestMapping("/nao_autorizado")
	public String naoAutorizado(){
		return "perfil/nao_autorizado";
	}

	@RequestMapping(produces = "text/html")
	@RequiresAuthentication
    public String show(Model uiModel) {
        return "perfil/show";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Usuario usuario, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateLoginForm(uiModel, usuario);
            return "perfil/update";
        }
        uiModel.asMap().clear();
        usuario.merge();
        return "redirect:/perfil/" + encodeUrlPathSegment(usuario.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Usuario.findUsuario(id));
        return "perfil/update";
    }

	void populateEditForm(Model uiModel, Usuario usuario) {
        uiModel.addAttribute("usuario", usuario);
    }
	
	void populateLoginForm(Model uiModel, Usuario usuario) {
        uiModel.addAttribute("usuario", usuario);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
