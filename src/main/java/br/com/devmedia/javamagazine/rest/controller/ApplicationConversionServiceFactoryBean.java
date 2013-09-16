package br.com.devmedia.javamagazine.rest.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import br.com.devmedia.javamagazine.rest.model.bean.Papel;
import br.com.devmedia.javamagazine.rest.model.bean.Permissao;
import br.com.devmedia.javamagazine.rest.model.bean.Usuario;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Permissao, String> getPermissaoToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<br.com.devmedia.javamagazine.rest.model.bean.Permissao, java.lang.String>() {
            public String convert(Permissao permissao) {
                return new StringBuilder().append(permissao.getNome()).toString();
            }
        };
    }

	public Converter<Long, Permissao> getIdToPermissaoConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, br.com.devmedia.javamagazine.rest.model.bean.Permissao>() {
            public br.com.devmedia.javamagazine.rest.model.bean.Permissao convert(java.lang.Long id) {
                return Permissao.findPermissao(id);
            }
        };
    }

	public Converter<String, Permissao> getStringToPermissaoConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, br.com.devmedia.javamagazine.rest.model.bean.Permissao>() {
            public br.com.devmedia.javamagazine.rest.model.bean.Permissao convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Permissao.class);
            }
        };
    }
	
	public Converter<Collection<?>, String> getCollectionToStringConverter(){
		return new Converter<Collection<?>, String>() {

			@Override
			public String convert(Collection<?> collection) {
				StringBuilder stringBuilder = new StringBuilder();
				for(Object object : collection){
					stringBuilder.append(object.toString());
					stringBuilder.append("; ");
				}
				stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length()-1);
				return stringBuilder.toString();
			}
			
		};
	}

	public Converter<Papel, String> getPapelToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<br.com.devmedia.javamagazine.rest.model.bean.Papel, java.lang.String>() {
            public String convert(Papel regra) {
                return new StringBuilder().append(regra.getNome()).toString();
            }
        };
    }

	public Converter<Long, Papel> getIdToPapelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, br.com.devmedia.javamagazine.rest.model.bean.Papel>() {
            public br.com.devmedia.javamagazine.rest.model.bean.Papel convert(java.lang.Long id) {
                return Papel.findPapel(id);
            }
        };
    }

	public Converter<String, Papel> getStringToPapelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, br.com.devmedia.javamagazine.rest.model.bean.Papel>() {
            public br.com.devmedia.javamagazine.rest.model.bean.Papel convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Papel.class);
            }
        };
    }

	public Converter<Usuario, String> getUsuarioToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<br.com.devmedia.javamagazine.rest.model.bean.Usuario, java.lang.String>() {
            public String convert(Usuario usuario) {
                return usuario.toString();
            }
        };
    }

	public Converter<Long, Usuario> getIdToUsuarioConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, br.com.devmedia.javamagazine.rest.model.bean.Usuario>() {
            public br.com.devmedia.javamagazine.rest.model.bean.Usuario convert(java.lang.Long id) {
                return Usuario.findUsuario(id);
            }
        };
    }

	public Converter<String, Usuario> getStringToUsuarioConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, br.com.devmedia.javamagazine.rest.model.bean.Usuario>() {
            public br.com.devmedia.javamagazine.rest.model.bean.Usuario convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Usuario.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getPermissaoToStringConverter());
        registry.addConverter(getIdToPermissaoConverter());
        registry.addConverter(getStringToPermissaoConverter());
        registry.addConverter(getPapelToStringConverter());
        registry.addConverter(getIdToPapelConverter());
        registry.addConverter(getStringToPapelConverter());
        registry.addConverter(getUsuarioToStringConverter());
        registry.addConverter(getIdToUsuarioConverter());
        registry.addConverter(getStringToUsuarioConverter());
        registry.addConverter(getCollectionToStringConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
