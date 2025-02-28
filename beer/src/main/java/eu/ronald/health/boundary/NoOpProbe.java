package eu.ronald.health.boundary;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Readiness;

@Liveness
@Readiness
@ApplicationScoped
public class NoOpProbe implements HealthCheck {

  @Override
  public HealthCheckResponse call() {
    return HealthCheckResponse
        .up("Basic Availability");
  }

}
