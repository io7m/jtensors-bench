/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jtensors.bench;

import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixDirectM4x4F;
import com.io7m.jtensors.MatrixM4x4F;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

@State(Scope.Benchmark)
public class MatrixDirectM4x4FCopy
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(MatrixDirectM4x4FCopy.class);
  }

  private Matrix4x4FType m0 = MatrixDirectM4x4F.newMatrix();
  private Matrix4x4FType m1 = MatrixDirectM4x4F.newMatrix();

  public static void main(final String[] args)
    throws RunnerException
  {
    final Options opt = new OptionsBuilder()
      .include(MatrixDirectM4x4FCopy.class.getName())
      .verbosity(VerboseMode.EXTRA)
      .forks(1)
      .build();
    new Runner(opt).run();
  }

  public void stateSetup()
  {
    MatrixDirectM4x4FCopy.LOG.debug("setting up state");

    final SecureRandom rng = new SecureRandom();

    for (int r = 0; r < 4; ++r) {
      for (int c = 0; c < 4; ++c) {
        this.m0.setRowColumnF(r, c, rng.nextFloat());
        this.m1.setRowColumnF(r, c, rng.nextFloat());
      }
    }
  }

  @Benchmark
  public void benchSomething(final Blackhole bh)
  {
    bh.consume(MatrixM4x4F.copy(this.m0, this.m1));
  }
}
