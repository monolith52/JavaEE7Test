package monolith52.test.arch;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.mvc.Viewable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Provider
public class ThymeleafMessageBodyWriter implements MessageBodyWriter<Viewable> {

	@Context private HttpServletRequest request;
	@Context private HttpServletResponse response;
	@Context private ServletContext servletContext;
	
	protected ServletContextTemplateResolver getTemplateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix("");
		resolver.setTemplateMode("HTML5");
		resolver.setCacheTTLMs(3600000L);
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}

	@Override
	public long getSize(Viewable viewable, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return Viewable.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(Viewable viewable, 
			Class<?> type, 
			Type genericType,
			Annotation[] annotations, 
			MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, 
			OutputStream entityStream)
			throws IOException, WebApplicationException {
	
		WebContext context = new WebContext(request, response, servletContext);
		if (viewable.getModel() instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> model = (Map<String, Object>)viewable.getModel();
			context.setVariables(model);
		}
		
		Writer writer = new OutputStreamWriter(entityStream, "UTF-8");

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(getTemplateResolver());
		templateEngine.process(viewable.getTemplateName(), context, writer);

		writer.flush();
	}
}
