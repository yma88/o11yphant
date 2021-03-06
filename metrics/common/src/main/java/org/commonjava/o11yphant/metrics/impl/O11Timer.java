/**
 * Copyright (C) 2020 Red Hat, Inc. (nos-devel@redhat.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.o11yphant.metrics.impl;

import org.commonjava.o11yphant.metrics.api.Snapshot;
import org.commonjava.o11yphant.metrics.api.Timer;

import java.util.concurrent.TimeUnit;

public class O11Timer
                implements Timer
{
    private com.codahale.metrics.Timer codahaleTimer;

    public O11Timer()
    {
        codahaleTimer = new com.codahale.metrics.Timer();
    }

    public O11Timer( com.codahale.metrics.Timer timer )
    {
        codahaleTimer = timer;
    }

    @Override
    public long getCount()
    {
        return codahaleTimer.getCount();
    }

    @Override
    public double getFifteenMinuteRate()
    {
        return codahaleTimer.getFifteenMinuteRate();
    }

    @Override
    public double getFiveMinuteRate()
    {
        return codahaleTimer.getFiveMinuteRate();
    }

    @Override
    public double getMeanRate()
    {
        return codahaleTimer.getMeanRate();
    }

    @Override
    public double getOneMinuteRate()
    {
        return codahaleTimer.getOneMinuteRate();
    }

    @Override
    public Context time()
    {
        return new O11Context( codahaleTimer.time() );
    }

    @Override
    public void update( long duration, TimeUnit timeUnit )
    {
        codahaleTimer.update( duration, timeUnit );
    }

    @Override
    public Snapshot getSnapshot()
    {
        final com.codahale.metrics.Snapshot codehaleSnapshot = codahaleTimer.getSnapshot();
        return new Snapshot()
        {
            @Override
            public double getValue( double var1 )
            {
                return codehaleSnapshot.getValue( var1 );
            }

            @Override
            public long[] getValues()
            {
                return codehaleSnapshot.getValues();
            }

            @Override
            public int size()
            {
                return codehaleSnapshot.size();
            }

            @Override
            public long getMax()
            {
                return codehaleSnapshot.getMax();
            }

            @Override
            public double getMean()
            {
                return codehaleSnapshot.getMean();
            }

            @Override
            public long getMin()
            {
                return codehaleSnapshot.getMin();
            }

            @Override
            public double getStdDev()
            {
                return codehaleSnapshot.getStdDev();
            }
        };
    }

    public class O11Context
                    implements Context
    {
        private com.codahale.metrics.Timer.Context codahaleContext;

        public O11Context( com.codahale.metrics.Timer.Context context )
        {
            this.codahaleContext = context;
        }

        @Override
        public long stop()
        {
            return codahaleContext.stop();
        }

        @Override
        public void close() throws Exception
        {
            stop();
        }
    }

    public com.codahale.metrics.Timer getCodahaleTimer()
    {
        return codahaleTimer;
    }
}
