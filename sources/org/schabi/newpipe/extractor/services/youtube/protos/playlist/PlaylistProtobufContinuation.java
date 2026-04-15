package org.schabi.newpipe.extractor.services.youtube.protos.playlist;

import com.google.protobuf.a;
import com.google.protobuf.f;
import com.google.protobuf.i;
import com.google.protobuf.n;
import com.google.protobuf.p;
import com.google.protobuf.q;
import com.google.protobuf.x;
import defpackage.cp;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class PlaylistProtobufContinuation {

    /* renamed from: org.schabi.newpipe.extractor.services.youtube.protos.playlist.PlaylistProtobufContinuation$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        /* JADX WARNING: Can't wrap try/catch for region: R(17:0|(2:1|2)|3|5|6|7|8|9|(2:11|12)|13|15|16|17|18|19|20|22) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|5|6|7|8|9|(2:11|12)|13|15|16|17|18|19|20|22) */
        /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0021 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0026 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0013 */
        static {
            /*
                com.google.protobuf.n$f[] r0 = com.google.protobuf.n.f.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = r0
                r1 = 1
                r2 = 3
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x000d }
            L_0x000d:
                r0 = 2
                r3 = 4
                int[] r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke     // Catch:{ NoSuchFieldError -> 0x0013 }
                r4[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0013 }
            L_0x0013:
                int[] r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke     // Catch:{ NoSuchFieldError -> 0x0017 }
                r4[r0] = r2     // Catch:{ NoSuchFieldError -> 0x0017 }
            L_0x0017:
                r0 = 5
                int[] r2 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke     // Catch:{ NoSuchFieldError -> 0x001c }
                r2[r0] = r3     // Catch:{ NoSuchFieldError -> 0x001c }
            L_0x001c:
                r2 = 6
                int[] r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke     // Catch:{ NoSuchFieldError -> 0x0021 }
                r3[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0021 }
            L_0x0021:
                int[] r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke     // Catch:{ NoSuchFieldError -> 0x0026 }
                r3 = 0
                r0[r3] = r2     // Catch:{ NoSuchFieldError -> 0x0026 }
            L_0x0026:
                int[] r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke     // Catch:{ NoSuchFieldError -> 0x002b }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002b }
            L_0x002b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.protos.playlist.PlaylistProtobufContinuation.AnonymousClass1.<clinit>():void");
        }
    }

    public static final class ContinuationParams extends n<ContinuationParams, Builder> implements ContinuationParamsOrBuilder {
        public static final int BROWSEID_FIELD_NUMBER = 2;
        public static final int CONTINUATIONPROPERTIES_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final ContinuationParams DEFAULT_INSTANCE;
        private static volatile n8<ContinuationParams> PARSER = null;
        public static final int PLAYLISTID_FIELD_NUMBER = 35;
        private String browseId_ = "";
        private String continuationProperties_ = "";
        private String playlistId_ = "";

        public static final class Builder extends n.a<ContinuationParams, Builder> implements ContinuationParamsOrBuilder {
            private Builder() {
                super(ContinuationParams.DEFAULT_INSTANCE);
            }

            public /* synthetic */ Builder(int i) {
                this();
            }

            public Builder clearBrowseId() {
                copyOnWrite();
                ((ContinuationParams) this.instance).clearBrowseId();
                return this;
            }

            public Builder clearContinuationProperties() {
                copyOnWrite();
                ((ContinuationParams) this.instance).clearContinuationProperties();
                return this;
            }

            public Builder clearPlaylistId() {
                copyOnWrite();
                ((ContinuationParams) this.instance).clearPlaylistId();
                return this;
            }

            public String getBrowseId() {
                return ((ContinuationParams) this.instance).getBrowseId();
            }

            public cp getBrowseIdBytes() {
                return ((ContinuationParams) this.instance).getBrowseIdBytes();
            }

            public String getContinuationProperties() {
                return ((ContinuationParams) this.instance).getContinuationProperties();
            }

            public cp getContinuationPropertiesBytes() {
                return ((ContinuationParams) this.instance).getContinuationPropertiesBytes();
            }

            public String getPlaylistId() {
                return ((ContinuationParams) this.instance).getPlaylistId();
            }

            public cp getPlaylistIdBytes() {
                return ((ContinuationParams) this.instance).getPlaylistIdBytes();
            }

            public Builder setBrowseId(String str) {
                copyOnWrite();
                ((ContinuationParams) this.instance).setBrowseId(str);
                return this;
            }

            public Builder setBrowseIdBytes(cp cpVar) {
                copyOnWrite();
                ((ContinuationParams) this.instance).setBrowseIdBytes(cpVar);
                return this;
            }

            public Builder setContinuationProperties(String str) {
                copyOnWrite();
                ((ContinuationParams) this.instance).setContinuationProperties(str);
                return this;
            }

            public Builder setContinuationPropertiesBytes(cp cpVar) {
                copyOnWrite();
                ((ContinuationParams) this.instance).setContinuationPropertiesBytes(cpVar);
                return this;
            }

            public Builder setPlaylistId(String str) {
                copyOnWrite();
                ((ContinuationParams) this.instance).setPlaylistId(str);
                return this;
            }

            public Builder setPlaylistIdBytes(cp cpVar) {
                copyOnWrite();
                ((ContinuationParams) this.instance).setPlaylistIdBytes(cpVar);
                return this;
            }
        }

        static {
            ContinuationParams continuationParams = new ContinuationParams();
            DEFAULT_INSTANCE = continuationParams;
            n.registerDefaultInstance(ContinuationParams.class, continuationParams);
        }

        private ContinuationParams() {
        }

        /* access modifiers changed from: private */
        public void clearBrowseId() {
            this.browseId_ = getDefaultInstance().getBrowseId();
        }

        /* access modifiers changed from: private */
        public void clearContinuationProperties() {
            this.continuationProperties_ = getDefaultInstance().getContinuationProperties();
        }

        /* access modifiers changed from: private */
        public void clearPlaylistId() {
            this.playlistId_ = getDefaultInstance().getPlaylistId();
        }

        public static ContinuationParams getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static ContinuationParams parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ContinuationParams) n.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ContinuationParams parseFrom(ByteBuffer byteBuffer) throws q {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static n8<ContinuationParams> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* access modifiers changed from: private */
        public void setBrowseId(String str) {
            str.getClass();
            this.browseId_ = str;
        }

        /* access modifiers changed from: private */
        public void setBrowseIdBytes(cp cpVar) {
            String str;
            a.checkByteStringIsUtf8(cpVar);
            cpVar.getClass();
            Charset charset = p.a;
            if (cpVar.size() == 0) {
                str = "";
            } else {
                str = cpVar.r(charset);
            }
            this.browseId_ = str;
        }

        /* access modifiers changed from: private */
        public void setContinuationProperties(String str) {
            str.getClass();
            this.continuationProperties_ = str;
        }

        /* access modifiers changed from: private */
        public void setContinuationPropertiesBytes(cp cpVar) {
            String str;
            a.checkByteStringIsUtf8(cpVar);
            cpVar.getClass();
            Charset charset = p.a;
            if (cpVar.size() == 0) {
                str = "";
            } else {
                str = cpVar.r(charset);
            }
            this.continuationProperties_ = str;
        }

        /* access modifiers changed from: private */
        public void setPlaylistId(String str) {
            str.getClass();
            this.playlistId_ = str;
        }

        /* access modifiers changed from: private */
        public void setPlaylistIdBytes(cp cpVar) {
            String str;
            a.checkByteStringIsUtf8(cpVar);
            cpVar.getClass();
            Charset charset = p.a;
            if (cpVar.size() == 0) {
                str = "";
            } else {
                str = cpVar.r(charset);
            }
            this.playlistId_ = str;
        }

        public final Object dynamicMethod(n.f fVar, Object obj, Object obj2) {
            int ordinal = fVar.ordinal();
            if (ordinal == 0) {
                return (byte) 1;
            }
            if (ordinal == 2) {
                return n.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0002#\u0003\u0000\u0000\u0000\u0002Ȉ\u0003Ȉ#Ȉ", new Object[]{"browseId_", "continuationProperties_", "playlistId_"});
            } else if (ordinal == 3) {
                return new ContinuationParams();
            } else {
                if (ordinal == 4) {
                    return new Builder(0);
                }
                if (ordinal == 5) {
                    return DEFAULT_INSTANCE;
                }
                if (ordinal == 6) {
                    n8<ContinuationParams> n8Var = PARSER;
                    if (n8Var == null) {
                        synchronized (ContinuationParams.class) {
                            n8Var = PARSER;
                            if (n8Var == null) {
                                n8Var = new n.b<>(DEFAULT_INSTANCE);
                                PARSER = n8Var;
                            }
                        }
                    }
                    return n8Var;
                }
                throw null;
            }
        }

        public String getBrowseId() {
            return this.browseId_;
        }

        public cp getBrowseIdBytes() {
            String str = this.browseId_;
            cp.h hVar = cp.f;
            return new cp.h(str.getBytes(p.a));
        }

        public String getContinuationProperties() {
            return this.continuationProperties_;
        }

        public cp getContinuationPropertiesBytes() {
            String str = this.continuationProperties_;
            cp.h hVar = cp.f;
            return new cp.h(str.getBytes(p.a));
        }

        public String getPlaylistId() {
            return this.playlistId_;
        }

        public cp getPlaylistIdBytes() {
            String str = this.playlistId_;
            cp.h hVar = cp.f;
            return new cp.h(str.getBytes(p.a));
        }

        public static Builder newBuilder(ContinuationParams continuationParams) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(continuationParams);
        }

        public static ContinuationParams parseDelimitedFrom(InputStream inputStream, i iVar) throws IOException {
            return (ContinuationParams) n.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, iVar);
        }

        public static ContinuationParams parseFrom(ByteBuffer byteBuffer, i iVar) throws q {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, byteBuffer, iVar);
        }

        public static ContinuationParams parseFrom(cp cpVar) throws q {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, cpVar);
        }

        public static ContinuationParams parseFrom(cp cpVar, i iVar) throws q {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, cpVar, iVar);
        }

        public static ContinuationParams parseFrom(byte[] bArr) throws q {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static ContinuationParams parseFrom(byte[] bArr, i iVar) throws q {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, bArr, iVar);
        }

        public static ContinuationParams parseFrom(InputStream inputStream) throws IOException {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ContinuationParams parseFrom(InputStream inputStream, i iVar) throws IOException {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, inputStream, iVar);
        }

        public static ContinuationParams parseFrom(f fVar) throws IOException {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, fVar);
        }

        public static ContinuationParams parseFrom(f fVar, i iVar) throws IOException {
            return (ContinuationParams) n.parseFrom(DEFAULT_INSTANCE, fVar, iVar);
        }
    }

    public interface ContinuationParamsOrBuilder extends p6 {
        String getBrowseId();

        cp getBrowseIdBytes();

        String getContinuationProperties();

        cp getContinuationPropertiesBytes();

        /* synthetic */ x getDefaultInstanceForType();

        String getPlaylistId();

        cp getPlaylistIdBytes();

        /* synthetic */ boolean isInitialized();
    }

    public static final class PlaylistContinuation extends n<PlaylistContinuation, Builder> implements PlaylistContinuationOrBuilder {
        /* access modifiers changed from: private */
        public static final PlaylistContinuation DEFAULT_INSTANCE;
        public static final int PARAMETERS_FIELD_NUMBER = 80226972;
        private static volatile n8<PlaylistContinuation> PARSER;
        private int bitField0_;
        private ContinuationParams parameters_;

        static {
            PlaylistContinuation playlistContinuation = new PlaylistContinuation();
            DEFAULT_INSTANCE = playlistContinuation;
            n.registerDefaultInstance(PlaylistContinuation.class, playlistContinuation);
        }

        private PlaylistContinuation() {
        }

        /* access modifiers changed from: private */
        public void clearParameters() {
            this.parameters_ = null;
            this.bitField0_ &= -2;
        }

        public static PlaylistContinuation getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* access modifiers changed from: private */
        public void mergeParameters(ContinuationParams continuationParams) {
            continuationParams.getClass();
            ContinuationParams continuationParams2 = this.parameters_;
            if (continuationParams2 == null || continuationParams2 == ContinuationParams.getDefaultInstance()) {
                this.parameters_ = continuationParams;
            } else {
                this.parameters_ = (ContinuationParams) ((ContinuationParams.Builder) ContinuationParams.newBuilder(this.parameters_).mergeFrom(continuationParams)).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static PlaylistContinuation parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PlaylistContinuation) n.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static PlaylistContinuation parseFrom(ByteBuffer byteBuffer) throws q {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static n8<PlaylistContinuation> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* access modifiers changed from: private */
        public void setParameters(ContinuationParams continuationParams) {
            continuationParams.getClass();
            this.parameters_ = continuationParams;
            this.bitField0_ |= 1;
        }

        public final Object dynamicMethod(n.f fVar, Object obj, Object obj2) {
            int ordinal = fVar.ordinal();
            if (ordinal == 0) {
                return (byte) 1;
            }
            if (ordinal == 2) {
                return n.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0001♁♁\u0001\u0000\u0000\u0000♁ဉ\u0000", new Object[]{"bitField0_", "parameters_"});
            } else if (ordinal == 3) {
                return new PlaylistContinuation();
            } else {
                if (ordinal == 4) {
                    return new Builder(0);
                }
                if (ordinal == 5) {
                    return DEFAULT_INSTANCE;
                }
                if (ordinal == 6) {
                    n8<PlaylistContinuation> n8Var = PARSER;
                    if (n8Var == null) {
                        synchronized (PlaylistContinuation.class) {
                            n8Var = PARSER;
                            if (n8Var == null) {
                                n8Var = new n.b<>(DEFAULT_INSTANCE);
                                PARSER = n8Var;
                            }
                        }
                    }
                    return n8Var;
                }
                throw null;
            }
        }

        public ContinuationParams getParameters() {
            ContinuationParams continuationParams = this.parameters_;
            return continuationParams == null ? ContinuationParams.getDefaultInstance() : continuationParams;
        }

        public boolean hasParameters() {
            return (this.bitField0_ & 1) != 0;
        }

        public static final class Builder extends n.a<PlaylistContinuation, Builder> implements PlaylistContinuationOrBuilder {
            private Builder() {
                super(PlaylistContinuation.DEFAULT_INSTANCE);
            }

            public /* synthetic */ Builder(int i) {
                this();
            }

            public Builder clearParameters() {
                copyOnWrite();
                ((PlaylistContinuation) this.instance).clearParameters();
                return this;
            }

            public ContinuationParams getParameters() {
                return ((PlaylistContinuation) this.instance).getParameters();
            }

            public boolean hasParameters() {
                return ((PlaylistContinuation) this.instance).hasParameters();
            }

            public Builder mergeParameters(ContinuationParams continuationParams) {
                copyOnWrite();
                ((PlaylistContinuation) this.instance).mergeParameters(continuationParams);
                return this;
            }

            public Builder setParameters(ContinuationParams continuationParams) {
                copyOnWrite();
                ((PlaylistContinuation) this.instance).setParameters(continuationParams);
                return this;
            }

            public Builder setParameters(ContinuationParams.Builder builder) {
                copyOnWrite();
                ((PlaylistContinuation) this.instance).setParameters((ContinuationParams) builder.build());
                return this;
            }
        }

        public static Builder newBuilder(PlaylistContinuation playlistContinuation) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(playlistContinuation);
        }

        public static PlaylistContinuation parseDelimitedFrom(InputStream inputStream, i iVar) throws IOException {
            return (PlaylistContinuation) n.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, iVar);
        }

        public static PlaylistContinuation parseFrom(ByteBuffer byteBuffer, i iVar) throws q {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, byteBuffer, iVar);
        }

        public static PlaylistContinuation parseFrom(cp cpVar) throws q {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, cpVar);
        }

        public static PlaylistContinuation parseFrom(cp cpVar, i iVar) throws q {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, cpVar, iVar);
        }

        public static PlaylistContinuation parseFrom(byte[] bArr) throws q {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static PlaylistContinuation parseFrom(byte[] bArr, i iVar) throws q {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, bArr, iVar);
        }

        public static PlaylistContinuation parseFrom(InputStream inputStream) throws IOException {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static PlaylistContinuation parseFrom(InputStream inputStream, i iVar) throws IOException {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, inputStream, iVar);
        }

        public static PlaylistContinuation parseFrom(f fVar) throws IOException {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, fVar);
        }

        public static PlaylistContinuation parseFrom(f fVar, i iVar) throws IOException {
            return (PlaylistContinuation) n.parseFrom(DEFAULT_INSTANCE, fVar, iVar);
        }
    }

    public interface PlaylistContinuationOrBuilder extends p6 {
        /* synthetic */ x getDefaultInstanceForType();

        ContinuationParams getParameters();

        boolean hasParameters();

        /* synthetic */ boolean isInitialized();
    }

    private PlaylistProtobufContinuation() {
    }

    public static void registerAllExtensions(i iVar) {
    }
}
