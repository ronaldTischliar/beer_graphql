package eu.ronald.delay.boundary;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class DelayProvider implements ContainerRequestFilter {

  long delay;

  @Inject
  @ConfigProperty(name = "delay-header-name", defaultValue = "delay-in-ms")
  String delayHeaderName;

  @Inject
  @ConfigProperty(name = "delay-in-ms", defaultValue = "0")
  Long delayInMs;

  public static long convert(String value) {
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException ex) {
      return 0;
    }
  }


  @Override
  public void filter(ContainerRequestContext requestContext) {
    this.delay = delayInMs;
    var headerName = this.delayHeaderName;
    System.out.println("requestContext = " + requestContext.getHeaders());
    String delayConfig = requestContext.getHeaderString(headerName);
    if (delayConfig != null) {
      this.delay = convert(delayConfig);
    }
    if (delay > 0) {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException ex) {
        throw new IllegalStateException(ex);
      }
    }
  }
}
