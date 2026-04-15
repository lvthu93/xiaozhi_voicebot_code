package com.google.android.exoplayer2.text;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

public final class TextRenderer extends BaseRenderer implements Handler.Callback {
    @Nullable
    public SubtitleOutputBuffer aa;
    @Nullable
    public SubtitleOutputBuffer ab;
    public int ac;
    public long ad;
    @Nullable
    public final Handler p;
    public final TextOutput q;
    public final SubtitleDecoderFactory r;
    public final FormatHolder s;
    public boolean t;
    public boolean u;
    public boolean v;
    public int w;
    @Nullable
    public Format x;
    @Nullable
    public SubtitleDecoder y;
    @Nullable
    public SubtitleInputBuffer z;

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper) {
        this(textOutput, looper, SubtitleDecoderFactory.a);
    }

    public final void c() {
        this.x = null;
        this.ad = -9223372036854775807L;
        List emptyList = Collections.emptyList();
        Handler handler = this.p;
        if (handler != null) {
            handler.obtainMessage(0, emptyList).sendToTarget();
        } else {
            this.q.onCues(emptyList);
        }
        m();
        ((SubtitleDecoder) Assertions.checkNotNull(this.y)).release();
        this.y = null;
        this.w = 0;
    }

    public final void e(long j, boolean z2) {
        List emptyList = Collections.emptyList();
        Handler handler = this.p;
        if (handler != null) {
            handler.obtainMessage(0, emptyList).sendToTarget();
        } else {
            this.q.onCues(emptyList);
        }
        this.t = false;
        this.u = false;
        this.ad = -9223372036854775807L;
        if (this.w != 0) {
            m();
            ((SubtitleDecoder) Assertions.checkNotNull(this.y)).release();
            this.y = null;
            this.w = 0;
            this.v = true;
            this.y = this.r.createDecoder((Format) Assertions.checkNotNull(this.x));
            return;
        }
        m();
        ((SubtitleDecoder) Assertions.checkNotNull(this.y)).flush();
    }

    public String getName() {
        return "TextRenderer";
    }

    public boolean handleMessage(Message message) {
        if (message.what == 0) {
            this.q.onCues((List) message.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    public final void i(Format[] formatArr, long j, long j2) {
        Format format = formatArr[0];
        this.x = format;
        if (this.y != null) {
            this.w = 1;
            return;
        }
        this.v = true;
        this.y = this.r.createDecoder((Format) Assertions.checkNotNull(format));
    }

    public boolean isEnded() {
        return this.u;
    }

    public boolean isReady() {
        return true;
    }

    public final long k() {
        if (this.ac == -1) {
            return Long.MAX_VALUE;
        }
        Assertions.checkNotNull(this.aa);
        if (this.ac >= this.aa.getEventTimeCount()) {
            return Long.MAX_VALUE;
        }
        return this.aa.getEventTime(this.ac);
    }

    public final void l(SubtitleDecoderException subtitleDecoderException) {
        String valueOf = String.valueOf(this.x);
        StringBuilder sb = new StringBuilder(valueOf.length() + 39);
        sb.append("Subtitle decoding failed. streamFormat=");
        sb.append(valueOf);
        Log.e("TextRenderer", sb.toString(), subtitleDecoderException);
        List emptyList = Collections.emptyList();
        Handler handler = this.p;
        if (handler != null) {
            handler.obtainMessage(0, emptyList).sendToTarget();
        } else {
            this.q.onCues(emptyList);
        }
        m();
        ((SubtitleDecoder) Assertions.checkNotNull(this.y)).release();
        this.y = null;
        this.w = 0;
        this.v = true;
        this.y = this.r.createDecoder((Format) Assertions.checkNotNull(this.x));
    }

    public final void m() {
        this.z = null;
        this.ac = -1;
        SubtitleOutputBuffer subtitleOutputBuffer = this.aa;
        if (subtitleOutputBuffer != null) {
            subtitleOutputBuffer.release();
            this.aa = null;
        }
        SubtitleOutputBuffer subtitleOutputBuffer2 = this.ab;
        if (subtitleOutputBuffer2 != null) {
            subtitleOutputBuffer2.release();
            this.ab = null;
        }
    }

    public void render(long j, long j2) {
        boolean z2;
        boolean z3;
        FormatHolder formatHolder = this.s;
        if (isCurrentStreamFinal()) {
            long j3 = this.ad;
            if (j3 != -9223372036854775807L && j >= j3) {
                m();
                this.u = true;
            }
        }
        if (!this.u) {
            if (this.ab == null) {
                ((SubtitleDecoder) Assertions.checkNotNull(this.y)).setPositionUs(j);
                try {
                    this.ab = (SubtitleOutputBuffer) ((SubtitleDecoder) Assertions.checkNotNull(this.y)).dequeueOutputBuffer();
                } catch (SubtitleDecoderException e) {
                    l(e);
                    return;
                }
            }
            if (getState() == 2) {
                if (this.aa != null) {
                    long k = k();
                    z2 = false;
                    while (k <= j) {
                        this.ac++;
                        k = k();
                        z2 = true;
                    }
                } else {
                    z2 = false;
                }
                SubtitleOutputBuffer subtitleOutputBuffer = this.ab;
                if (subtitleOutputBuffer != null) {
                    if (subtitleOutputBuffer.isEndOfStream()) {
                        if (!z2 && k() == Long.MAX_VALUE) {
                            if (this.w == 2) {
                                m();
                                ((SubtitleDecoder) Assertions.checkNotNull(this.y)).release();
                                this.y = null;
                                this.w = 0;
                                this.v = true;
                                this.y = this.r.createDecoder((Format) Assertions.checkNotNull(this.x));
                            } else {
                                m();
                                this.u = true;
                            }
                        }
                    } else if (subtitleOutputBuffer.f <= j) {
                        SubtitleOutputBuffer subtitleOutputBuffer2 = this.aa;
                        if (subtitleOutputBuffer2 != null) {
                            subtitleOutputBuffer2.release();
                        }
                        this.ac = subtitleOutputBuffer.getNextEventTimeIndex(j);
                        this.aa = subtitleOutputBuffer;
                        this.ab = null;
                        z2 = true;
                    }
                }
                if (z2) {
                    Assertions.checkNotNull(this.aa);
                    List<Cue> cues = this.aa.getCues(j);
                    Handler handler = this.p;
                    if (handler != null) {
                        handler.obtainMessage(0, cues).sendToTarget();
                    } else {
                        this.q.onCues(cues);
                    }
                }
                if (this.w != 2) {
                    while (!this.t) {
                        try {
                            SubtitleInputBuffer subtitleInputBuffer = this.z;
                            if (subtitleInputBuffer == null) {
                                subtitleInputBuffer = (SubtitleInputBuffer) ((SubtitleDecoder) Assertions.checkNotNull(this.y)).dequeueInputBuffer();
                                if (subtitleInputBuffer != null) {
                                    this.z = subtitleInputBuffer;
                                } else {
                                    return;
                                }
                            }
                            if (this.w == 1) {
                                subtitleInputBuffer.setFlags(4);
                                ((SubtitleDecoder) Assertions.checkNotNull(this.y)).queueInputBuffer(subtitleInputBuffer);
                                this.z = null;
                                this.w = 2;
                                return;
                            }
                            int j4 = j(formatHolder, subtitleInputBuffer, 0);
                            if (j4 == -4) {
                                if (subtitleInputBuffer.isEndOfStream()) {
                                    this.t = true;
                                    this.v = false;
                                } else {
                                    Format format = formatHolder.b;
                                    if (format != null) {
                                        subtitleInputBuffer.m = format.t;
                                        subtitleInputBuffer.flip();
                                        boolean z4 = this.v;
                                        if (!subtitleInputBuffer.isKeyFrame()) {
                                            z3 = true;
                                        } else {
                                            z3 = false;
                                        }
                                        this.v = z4 & z3;
                                    } else {
                                        return;
                                    }
                                }
                                if (!this.v) {
                                    ((SubtitleDecoder) Assertions.checkNotNull(this.y)).queueInputBuffer(subtitleInputBuffer);
                                    this.z = null;
                                }
                            } else if (j4 == -3) {
                                return;
                            }
                        } catch (SubtitleDecoderException e2) {
                            l(e2);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void setFinalStreamEndPositionUs(long j) {
        Assertions.checkState(isCurrentStreamFinal());
        this.ad = j;
    }

    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        ga.a(this, f, f2);
    }

    public int supportsFormat(Format format) {
        int i;
        if (this.r.supportsFormat(format)) {
            if (format.ai == null) {
                i = 4;
            } else {
                i = 2;
            }
            return ha.a(i);
        } else if (MimeTypes.isText(format.p)) {
            return ha.a(1);
        } else {
            return ha.a(0);
        }
    }

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper, SubtitleDecoderFactory subtitleDecoderFactory) {
        super(3);
        Handler handler;
        this.q = (TextOutput) Assertions.checkNotNull(textOutput);
        if (looper == null) {
            handler = null;
        } else {
            handler = Util.createHandler(looper, this);
        }
        this.p = handler;
        this.r = subtitleDecoderFactory;
        this.s = new FormatHolder();
        this.ad = -9223372036854775807L;
    }
}
