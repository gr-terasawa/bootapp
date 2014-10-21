package bootapp.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).favorParameter(true);
    }

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setFallbackPageable(new PageRequest(0, 5));
		resolver.setOneIndexedParameters(true);
		argumentResolvers.add(resolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    if (!registry.hasMappingForPattern("/webjars/**")) {
	        registry.addResourceHandler("/webjars/**").addResourceLocations(
	                "classpath:/META-INF/resources/webjars/");
	    }
//	    if (!registry.hasMappingForPattern("/**")) {
//	        registry.addResourceHandler("/**").addResourceLocations(
//	                RESOURCE_LOCATIONS);
//	    }
	}

	@Bean
	public FilterRegistrationBean getFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CharacterEncodingFilter());
		return filterRegistrationBean;
	}

	/**
	 * リクエストのエンコーディングをUTF-8にします。
	 */
	private static class CharacterEncodingFilter implements Filter {
		protected String encoding;

		public void init(FilterConfig filterConfig) throws ServletException {
			encoding = "UTF-8";
		}

		public void doFilter(ServletRequest servletRequest,
				ServletResponse servletResponse, FilterChain filterChain)
				throws IOException, ServletException {

			HttpServletRequest request = (HttpServletRequest) servletRequest;
			request.setCharacterEncoding(encoding);
			filterChain.doFilter(servletRequest, servletResponse);
		}

		public void destroy() {
			encoding = null;
		}
	}

}
