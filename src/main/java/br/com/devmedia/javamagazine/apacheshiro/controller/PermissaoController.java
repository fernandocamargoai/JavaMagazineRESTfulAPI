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

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Permissao;
import br.com.devmedia.javamagazine.apacheshiro.security.Permissoes;
import br.com.devmedia.javamagazine.apacheshiro.security.Permissoes.Dominio;
import br.com.devmedia.javamagazine.apacheshiro.security.Permissoes.Operacao;

@RequestMapping("/permissoes")
@Controller
public class PermissaoController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PERMISSAO+":"+Permissoes.Operacao.CRIAR)
    public String create(@Valid Permissao permissao, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, permissao);
            return "permissoes/create";
        }
        uiModel.asMap().clear();
        permissao.persist();
        return "redirect:/permissoes/" + encodeUrlPathSegment(permissao.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PERMISSAO+":"+Permissoes.Operacao.CRIAR)
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Permissao());
        return "permissoes/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PERMISSAO+":"+Permissoes.Operacao.LER)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("permissao", Permissao.findPermissao(id));
        uiModel.addAttribute("itemId", id);
        return "permissoes/show";
    }

	@RequestMapping(produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PERMISSAO+":"+Permissoes.Operacao.LER)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("permissaos", Permissao.findPermissaoEntries(firstResult, sizeNo));
            float nrOfPages = (float) Permissao.countPermissaos() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("permissaos", Permissao.findAllPermissaos());
        }
        return "permissoes/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PERMISSAO+":"+Permissoes.Operacao.ATUALIZAR)
    public String update(@Valid Permissao permissao, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, permissao);
            return "permissoes/update";
        }
        uiModel.asMap().clear();
        permissao.merge();
        return "redirect:/permissoes/" + encodeUrlPathSegment(permissao.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PERMISSAO+":"+Permissoes.Operacao.ATUALIZAR)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Permissao.findPermissao(id));
        return "permissoes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	@RequiresPermissions(Permissoes.Dominio.PERMISSAO+":"+Permissoes.Operacao.DELETAR)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Permissao permissao = Permissao.findPermissao(id);
        permissao.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/permissoes";
    }

	void populateEditForm(Model uiModel, Permissao permissao) {
        uiModel.addAttribute("permissao", permissao);
        uiModel.addAttribute("dominiosDisponiveis", Dominio.dominios);
        uiModel.addAttribute("operacoesDisponiveis", Operacao.operacoes);
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
