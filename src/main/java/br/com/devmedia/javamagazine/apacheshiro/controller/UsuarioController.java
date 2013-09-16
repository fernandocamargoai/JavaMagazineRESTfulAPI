package br.com.devmedia.javamagazine.apacheshiro.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Papel;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Permissao;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Usuario;
import br.com.devmedia.javamagazine.apacheshiro.security.Permissoes;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.USUARIO+":"+Permissoes.Operacao.CRIAR)
    public String create(@Valid Usuario usuario, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
    	if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, usuario);
            return "usuarios/create";
        }
        uiModel.asMap().clear();
        usuario.persist();
        return "redirect:/usuarios/" + encodeUrlPathSegment(usuario.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.USUARIO+":"+Permissoes.Operacao.CRIAR)
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Usuario());
        return "usuarios/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.USUARIO+":"+Permissoes.Operacao.LER)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("usuario", Usuario.findUsuario(id));
        uiModel.addAttribute("itemId", id);
        return "usuarios/show";
    }

    @RequestMapping(produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.USUARIO+":"+Permissoes.Operacao.LER)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("usuarios", Usuario.findUsuarioEntries(firstResult, sizeNo));
            float nrOfPages = (float) Usuario.countUsuarios() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("usuarios", Usuario.findAllUsuarios());
        }
        return "usuarios/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.USUARIO+":"+Permissoes.Operacao.ATUALIZAR)
    public String update(@Valid Usuario usuario, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
    	Usuario usuarioPersistido = Usuario.findUsuario(usuario.getId());
    	usuarioPersistido.setNome(usuario.getNome());
    	usuarioPersistido.setLogin(usuario.getLogin());
    	usuarioPersistido.setPapeis(usuario.getPapeis());
    	usuarioPersistido.setPermissoes(usuario.getPermissoes());
        uiModel.asMap().clear();
        usuarioPersistido.merge();
        return "redirect:/usuarios/" + encodeUrlPathSegment(usuario.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.USUARIO+":"+Permissoes.Operacao.ATUALIZAR)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Usuario.findUsuario(id));
        return "usuarios/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.USUARIO+":"+Permissoes.Operacao.DELETAR)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Usuario usuario = Usuario.findUsuario(id);
        usuario.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/usuarios";
    }

    void populateEditForm(Model uiModel, Usuario usuario) {
        uiModel.addAttribute("usuario", usuario);
        uiModel.addAttribute("permissaos", Permissao.findAllPermissaos());
        uiModel.addAttribute("papeis", Papel.findAllPapels());
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
}
