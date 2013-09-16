package br.com.devmedia.javamagazine.apacheshiro.controller;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
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

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Comentario;
import br.com.devmedia.javamagazine.apacheshiro.service.UsuarioService;
import br.com.devmedia.javamagazine.apacheshiro.security.Permissoes;

@RequestMapping("/comentarios")
@Controller
public class ComentarioController {

	@Inject
	private UsuarioService usuarioService;
	
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.COMENTARIO+":"+Permissoes.Operacao.CRIAR)
    public String create(@Valid Comentario comentario, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, comentario);
            return "comentarios/create";
        }
        comentario.setAutor(usuarioService.getUsuarioCorrente());
        uiModel.asMap().clear();
        comentario.persist();
        return "redirect:/comentarios/" + encodeUrlPathSegment(comentario.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.COMENTARIO+":"+Permissoes.Operacao.CRIAR)
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Comentario());
        return "comentarios/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.COMENTARIO+":"+Permissoes.Operacao.LER)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("comentario", Comentario.findComentario(id));
        uiModel.addAttribute("itemId", id);
        return "comentarios/show";
    }

    @RequestMapping(produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.COMENTARIO+":"+Permissoes.Operacao.LER)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("comentarios", Comentario.findComentarioEntries(firstResult, sizeNo));
            float nrOfPages = (float) Comentario.countComentarios() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("comentarios", Comentario.findAllComentarios());
        }
        return "comentarios/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @RequiresPermissions(Permissoes.Dominio.COMENTARIO+":"+Permissoes.Operacao.DELETAR)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Comentario comentario = Comentario.findComentario(id);
        comentario.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/comentarios";
    }

    void populateEditForm(Model uiModel, Comentario comentario) {
        uiModel.addAttribute("comentario", comentario);
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
