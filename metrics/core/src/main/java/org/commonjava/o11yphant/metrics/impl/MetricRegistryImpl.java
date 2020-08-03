package org.commonjava.o11yphant.metrics.impl;

import com.codahale.metrics.health.HealthCheckRegistry;
import org.commonjava.o11yphant.api.Gauge;
import org.commonjava.o11yphant.api.HealthCheck;
import org.commonjava.o11yphant.api.Metric;
import org.commonjava.o11yphant.api.MetricRegistry;

import javax.inject.Inject;

public class MetricRegistryImpl
                implements MetricRegistry
{
    private final com.codahale.metrics.MetricRegistry registry;

    private final HealthCheckRegistry healthCheckRegistry;

    @Inject
    public MetricRegistryImpl( com.codahale.metrics.MetricRegistry registry, HealthCheckRegistry healthCheckRegistry )
    {
        this.registry = registry;
        this.healthCheckRegistry = healthCheckRegistry;
    }

    @Override
    public <T extends Metric> T register( String name, T metric )
    {
        if ( metric instanceof Gauge )
        {
            Gauge gauge = (Gauge) metric;
            registry.register( name, (com.codahale.metrics.Gauge) gauge::getValue );
        }
        return null;
    }

    @Override
    public void registerHealthCheck( String name, HealthCheck healthCheck )
    {
        healthCheckRegistry.register( name, new com.codahale.metrics.health.HealthCheck()
        {
            @Override
            protected Result check() throws Exception
            {
                HealthCheck.Result ret = healthCheck.check();
                ResultBuilder builder = Result.builder();
                if ( ret.isHealthy() )
                {
                    builder.healthy();
                }
                else
                {
                    builder.unhealthy( ret.getError() );
                    builder.withMessage( ret.getMessage() );
                }
                return builder.build();
            }
        } );
    }
}
