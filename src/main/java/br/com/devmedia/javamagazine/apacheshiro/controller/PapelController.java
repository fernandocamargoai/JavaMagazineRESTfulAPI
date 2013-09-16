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
import br.com.devmedia.javamagazine.apacheshiro.security.Permissoes;

@RequestMapping("/papeis")
@Controller
public class PapelController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PAPEL+":"+Permissoes.Operacao.CRIAR)
    public String create(@Valid Papel papel, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, papel);
            return "papeis/create";
        }
        uiModel.asMap().clear();
        papel.persist();
        return "redirect:/papeis/" + encodeUrlPathSegment(papel.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PAPEL+":"+Permissoes.Operacao.CRIAR)
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Papel());
        return "papeis/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PAPEL+":"+Permissoes.Operacao.LER)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("papel", Papel.findPapel(id));
        uiModel.addAttribute("itemId", id);
        return "papeis/show";
    }

	@RequestMapping(produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PAPEL+":"+Permissoes.Operacao.LER)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("papels", Papel.findPapelEntries(firstResult, sizeNo));
            float nrOfPages = (float) Papel.countPapels() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("papels", Papel.findAllPapels());
        }
        return "papeis/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PAPEL+":"+Permissoes.Operacao.ATUALIZAR)
    public String update(@Valid Papel papel, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, papel);
            return "papeis/update";
        }
        uiModel.asMap().clear();
        papel.merge();
        return "redirect:/papeis/" + encodeUrlPathSegment(papel.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PAPEL+":"+Permissoes.Operacao.ATUALIZAR)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Papel.findPapel(id));
        return "papeis/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PAPEL+":"+Permissoes.Operacao.DELETAR)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Papel papel = Papel.findPapel(id);
        papel.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/papeis";
    }

	void populateEditForm(Model uiModel, Papel papel) {
        uiModel.addAttribute("papel", papel);
        uiModel.addAttribute("permissaos", Permissao.findAllPermissaos());
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
