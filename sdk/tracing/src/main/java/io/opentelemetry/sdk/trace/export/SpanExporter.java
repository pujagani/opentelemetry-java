/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.sdk.trace.export;

import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.TracerSdkManagement;
import io.opentelemetry.sdk.trace.data.SpanData;
import java.util.Collection;

/**
 * An interface that allows different tracing services to export recorded data for sampled spans in
 * their own format.
 *
 * <p>To export data this MUST be register to the {@code TracerSdk} using a {@link
 * SimpleSpanProcessor} or a {@code BatchSampledSpansProcessor}.
 */
public interface SpanExporter {

  /**
   * Called to export sampled {@code Span}s. Note that export operations can be performed
   * simultaneously depending on the type of span processor being used. However, the {@link
   * BatchSpanProcessor} will ensure that only one export can occur at a time.
   *
   * @param spans the collection of sampled Spans to be exported.
   * @return the result of the export, which is often an asynchronous operation.
   */
  CompletableResultCode export(Collection<SpanData> spans);

  /**
   * Exports the collection of sampled {@code Span}s that have not yet been exported. Note that
   * export operations can be performed simultaneously depending on the type of span processor being
   * used. However, the {@link BatchSpanProcessor} will ensure that only one export can occur at a
   * time.
   *
   * @return the result of the flush, which is often an asynchronous operation.
   */
  CompletableResultCode flush();

  /**
   * Called when {@link TracerSdkManagement#shutdown()} is called, if this {@code SpanExporter} is
   * registered to a {@code TracerSdkManagement} object.
   *
   * @return a {@link CompletableResultCode} which is completed when shutdown completes.
   */
  CompletableResultCode shutdown();
}
