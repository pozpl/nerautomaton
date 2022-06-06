package com.pozpl.nerannotator.config;


import com.pozpl.nerannotator.auth.impl.resolvers.LoggedInUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@EnableConfigurationProperties({WebProperties.class})
public class MvcConfig implements WebMvcConfigurer {

	private LoggedInUserResolver loggedInUserResolver;


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loggedInUserResolver);
	}


	static final String[] STATIC_RESOURCES = new String[]{
			"/**/*.css",
			"/**/*.html",
			"/**/*.js",
			"/**/*.json",
			"/**/*.bmp",
			"/**/*.jpeg",
			"/**/*.jpg",
			"/**/*.png",
			"/**/*.ttf",
			"/**/*.eot",
			"/**/*.svg",
			"/**/*.woff",
			"/**/*.woff2"
	};

	@Autowired
	private WebProperties resourceProperties = new WebProperties();

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//Add all static files
//		Integer cachePeriod = Long.valueOf(resourceProperties.getCache().getPeriod().get(ChronoUnit.SECONDS)).intValue();
		registry.addResourceHandler(STATIC_RESOURCES)
				.addResourceLocations(resourceProperties.getResources().getStaticLocations());
				//.setCachePeriod(cachePeriod);

		//Create mapping to index.html for Angular HTML5 mode.
//		String[] indexLocations = getIndexLocations();
//		registry.addResourceHandler("/**")
//				.addResourceLocations(indexLocations)
////				.setCachePeriod(cachePeriod)
//				.resourceChain(true)
//				.addResolver(new PathResourceResolver() {
//					@Override
//					protected Resource getResource(String resourcePath, Resource location) throws IOException {
//						return location.exists() && location.isReadable() ? location : null;
//					}
//				});
	}

//	private String[] getIndexLocations() {
//		return Arrays.stream(resourceProperties.getStaticLocations())
//				.map((location) -> location + "index.html")
//				.toArray(String[]::new);
//	}

}
