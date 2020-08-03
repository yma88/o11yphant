package org.commonjava.o11yphant.metrics.impl;

import org.commonjava.o11yphant.api.Meter;

public class MeterImpl
                implements Meter
{
    private com.codahale.metrics.Meter codahaleMeter;

    public MeterImpl( com.codahale.metrics.Meter meter )
    {
        codahaleMeter = meter;
    }

    @Override
    public long getCount()
    {
        return codahaleMeter.getCount();
    }

    @Override
    public double getFifteenMinuteRate()
    {
        return codahaleMeter.getFifteenMinuteRate();
    }

    @Override
    public double getFiveMinuteRate()
    {
        return codahaleMeter.getFiveMinuteRate();
    }

    @Override
    public double getMeanRate()
    {
        return codahaleMeter.getMeanRate();
    }

    @Override
    public double getOneMinuteRate()
    {
        return codahaleMeter.getOneMinuteRate();
    }

    @Override
    public void mark()
    {
        codahaleMeter.mark();
    }

    @Override
    public void mark( long n )
    {
        codahaleMeter.mark( n );
    }

}
