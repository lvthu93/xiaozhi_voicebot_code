package defpackage;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.mp4.a;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: r6  reason: default package */
public final class r6 {
    @VisibleForTesting
    public static final String[] a = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Afro-Punk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop", "Abstract", "Art Rock", "Baroque", "Bhangra", "Big beat", "Breakbeat", "Chillout", "Downtempo", "Dub", "EBM", "Eclectic", "Electro", "Electroclash", "Emo", "Experimental", "Garage", "Global", "IDM", "Illbient", "Industro-Goth", "Jam Band", "Krautrock", "Leftfield", "Lounge", "Math Rock", "New Romantic", "Nu-Breakz", "Post-Punk", "Post-Rock", "Psytrance", "Shoegaze", "Space Rock", "Trop Rock", "World Music", "Neoclassical", "Audiobook", "Audio theatre", "Neue Deutsche Welle", "Podcast", "Indie-Rock", "G-Funk", "Dubstep", "Garage Rock", "Psybient"};

    @Nullable
    public static CommentFrame a(int i, ParsableByteArray parsableByteArray) {
        String str;
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            String readNullTerminatedString = parsableByteArray.readNullTerminatedString(readInt - 16);
            return new CommentFrame("und", readNullTerminatedString, readNullTerminatedString);
        }
        String valueOf = String.valueOf(a.getAtomTypeString(i));
        if (valueOf.length() != 0) {
            str = "Failed to parse comment attribute: ".concat(valueOf);
        } else {
            str = new String("Failed to parse comment attribute: ");
        }
        Log.w("MetadataUtil", str);
        return null;
    }

    @Nullable
    public static ApicFrame b(ParsableByteArray parsableByteArray) {
        String str;
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            int parseFullAtomFlags = a.parseFullAtomFlags(parsableByteArray.readInt());
            if (parseFullAtomFlags == 13) {
                str = "image/jpeg";
            } else if (parseFullAtomFlags == 14) {
                str = "image/png";
            } else {
                str = null;
            }
            if (str == null) {
                y2.t(41, "Unrecognized cover art flags: ", parseFullAtomFlags, "MetadataUtil");
                return null;
            }
            parsableByteArray.skipBytes(4);
            int i = readInt - 16;
            byte[] bArr = new byte[i];
            parsableByteArray.readBytes(bArr, 0, i);
            return new ApicFrame(str, (String) null, 3, bArr);
        }
        Log.w("MetadataUtil", "Failed to parse cover art attribute");
        return null;
    }

    @Nullable
    public static TextInformationFrame c(ParsableByteArray parsableByteArray, int i, String str) {
        String str2;
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385 && readInt >= 22) {
            parsableByteArray.skipBytes(10);
            int readUnsignedShort = parsableByteArray.readUnsignedShort();
            if (readUnsignedShort > 0) {
                StringBuilder sb = new StringBuilder(11);
                sb.append(readUnsignedShort);
                String sb2 = sb.toString();
                int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                if (readUnsignedShort2 > 0) {
                    String valueOf = String.valueOf(sb2);
                    StringBuilder sb3 = new StringBuilder(valueOf.length() + 12);
                    sb3.append(valueOf);
                    sb3.append(MqttTopic.TOPIC_LEVEL_SEPARATOR);
                    sb3.append(readUnsignedShort2);
                    sb2 = sb3.toString();
                }
                return new TextInformationFrame(str, (String) null, sb2);
            }
        }
        String valueOf2 = String.valueOf(a.getAtomTypeString(i));
        if (valueOf2.length() != 0) {
            str2 = "Failed to parse index/count attribute: ".concat(valueOf2);
        } else {
            str2 = new String("Failed to parse index/count attribute: ");
        }
        Log.w("MetadataUtil", str2);
        return null;
    }

    @Nullable
    public static TextInformationFrame d(ParsableByteArray parsableByteArray, int i, String str) {
        String str2;
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return new TextInformationFrame(str, (String) null, parsableByteArray.readNullTerminatedString(readInt - 16));
        }
        String valueOf = String.valueOf(a.getAtomTypeString(i));
        if (valueOf.length() != 0) {
            str2 = "Failed to parse text attribute: ".concat(valueOf);
        } else {
            str2 = new String("Failed to parse text attribute: ");
        }
        Log.w("MetadataUtil", str2);
        return null;
    }

    @Nullable
    public static Id3Frame e(int i, String str, ParsableByteArray parsableByteArray, boolean z, boolean z2) {
        String str2;
        int f = f(parsableByteArray);
        if (z2) {
            f = Math.min(1, f);
        }
        if (f < 0) {
            String valueOf = String.valueOf(a.getAtomTypeString(i));
            if (valueOf.length() != 0) {
                str2 = "Failed to parse uint8 attribute: ".concat(valueOf);
            } else {
                str2 = new String("Failed to parse uint8 attribute: ");
            }
            Log.w("MetadataUtil", str2);
            return null;
        } else if (z) {
            return new TextInformationFrame(str, (String) null, Integer.toString(f));
        } else {
            return new CommentFrame("und", str, Integer.toString(f));
        }
    }

    public static int f(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return parsableByteArray.readUnsignedByte();
        }
        Log.w("MetadataUtil", "Failed to parse uint8 attribute value");
        return -1;
    }

    @Nullable
    public static Metadata.Entry parseIlstElement(ParsableByteArray parsableByteArray) {
        String str;
        String str2;
        int readInt = parsableByteArray.readInt() + parsableByteArray.getPosition();
        int readInt2 = parsableByteArray.readInt();
        int i = (readInt2 >> 24) & 255;
        Id3Frame id3Frame = null;
        if (i == 169 || i == 253) {
            int i2 = 16777215 & readInt2;
            if (i2 == 6516084) {
                CommentFrame a2 = a(readInt2, parsableByteArray);
                parsableByteArray.setPosition(readInt);
                return a2;
            } else if (i2 == 7233901 || i2 == 7631467) {
                TextInformationFrame d = d(parsableByteArray, readInt2, "TIT2");
                parsableByteArray.setPosition(readInt);
                return d;
            } else if (i2 == 6516589 || i2 == 7828084) {
                TextInformationFrame d2 = d(parsableByteArray, readInt2, "TCOM");
                parsableByteArray.setPosition(readInt);
                return d2;
            } else if (i2 == 6578553) {
                TextInformationFrame d3 = d(parsableByteArray, readInt2, "TDRC");
                parsableByteArray.setPosition(readInt);
                return d3;
            } else if (i2 == 4280916) {
                TextInformationFrame d4 = d(parsableByteArray, readInt2, "TPE1");
                parsableByteArray.setPosition(readInt);
                return d4;
            } else if (i2 == 7630703) {
                TextInformationFrame d5 = d(parsableByteArray, readInt2, "TSSE");
                parsableByteArray.setPosition(readInt);
                return d5;
            } else if (i2 == 6384738) {
                TextInformationFrame d6 = d(parsableByteArray, readInt2, "TALB");
                parsableByteArray.setPosition(readInt);
                return d6;
            } else if (i2 == 7108978) {
                TextInformationFrame d7 = d(parsableByteArray, readInt2, "USLT");
                parsableByteArray.setPosition(readInt);
                return d7;
            } else if (i2 == 6776174) {
                TextInformationFrame d8 = d(parsableByteArray, readInt2, "TCON");
                parsableByteArray.setPosition(readInt);
                return d8;
            } else if (i2 == 6779504) {
                TextInformationFrame d9 = d(parsableByteArray, readInt2, "TIT1");
                parsableByteArray.setPosition(readInt);
                return d9;
            }
        } else if (readInt2 == 1735291493) {
            try {
                int f = f(parsableByteArray);
                if (f <= 0 || f > 192) {
                    str2 = null;
                } else {
                    str2 = a[f - 1];
                }
                if (str2 != null) {
                    id3Frame = new TextInformationFrame("TCON", (String) null, str2);
                } else {
                    Log.w("MetadataUtil", "Failed to parse standard genre code");
                }
                return id3Frame;
            } finally {
                parsableByteArray.setPosition(readInt);
            }
        } else if (readInt2 == 1684632427) {
            TextInformationFrame c = c(parsableByteArray, readInt2, "TPOS");
            parsableByteArray.setPosition(readInt);
            return c;
        } else if (readInt2 == 1953655662) {
            TextInformationFrame c2 = c(parsableByteArray, readInt2, "TRCK");
            parsableByteArray.setPosition(readInt);
            return c2;
        } else if (readInt2 == 1953329263) {
            Id3Frame e = e(readInt2, "TBPM", parsableByteArray, true, false);
            parsableByteArray.setPosition(readInt);
            return e;
        } else if (readInt2 == 1668311404) {
            Id3Frame e2 = e(readInt2, "TCMP", parsableByteArray, true, true);
            parsableByteArray.setPosition(readInt);
            return e2;
        } else if (readInt2 == 1668249202) {
            ApicFrame b = b(parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return b;
        } else if (readInt2 == 1631670868) {
            TextInformationFrame d10 = d(parsableByteArray, readInt2, "TPE2");
            parsableByteArray.setPosition(readInt);
            return d10;
        } else if (readInt2 == 1936682605) {
            TextInformationFrame d11 = d(parsableByteArray, readInt2, "TSOT");
            parsableByteArray.setPosition(readInt);
            return d11;
        } else if (readInt2 == 1936679276) {
            TextInformationFrame d12 = d(parsableByteArray, readInt2, "TSO2");
            parsableByteArray.setPosition(readInt);
            return d12;
        } else if (readInt2 == 1936679282) {
            TextInformationFrame d13 = d(parsableByteArray, readInt2, "TSOA");
            parsableByteArray.setPosition(readInt);
            return d13;
        } else if (readInt2 == 1936679265) {
            TextInformationFrame d14 = d(parsableByteArray, readInt2, "TSOP");
            parsableByteArray.setPosition(readInt);
            return d14;
        } else if (readInt2 == 1936679791) {
            TextInformationFrame d15 = d(parsableByteArray, readInt2, "TSOC");
            parsableByteArray.setPosition(readInt);
            return d15;
        } else if (readInt2 == 1920233063) {
            Id3Frame e3 = e(readInt2, "ITUNESADVISORY", parsableByteArray, false, false);
            parsableByteArray.setPosition(readInt);
            return e3;
        } else if (readInt2 == 1885823344) {
            Id3Frame e4 = e(readInt2, "ITUNESGAPLESS", parsableByteArray, false, true);
            parsableByteArray.setPosition(readInt);
            return e4;
        } else if (readInt2 == 1936683886) {
            TextInformationFrame d16 = d(parsableByteArray, readInt2, "TVSHOWSORT");
            parsableByteArray.setPosition(readInt);
            return d16;
        } else if (readInt2 == 1953919848) {
            TextInformationFrame d17 = d(parsableByteArray, readInt2, "TVSHOW");
            parsableByteArray.setPosition(readInt);
            return d17;
        } else if (readInt2 == 757935405) {
            String str3 = null;
            String str4 = null;
            int i3 = -1;
            int i4 = -1;
            while (parsableByteArray.getPosition() < readInt) {
                int position = parsableByteArray.getPosition();
                int readInt3 = parsableByteArray.readInt();
                int readInt4 = parsableByteArray.readInt();
                parsableByteArray.skipBytes(4);
                if (readInt4 == 1835360622) {
                    str3 = parsableByteArray.readNullTerminatedString(readInt3 - 12);
                } else if (readInt4 == 1851878757) {
                    str4 = parsableByteArray.readNullTerminatedString(readInt3 - 12);
                } else {
                    if (readInt4 == 1684108385) {
                        i3 = position;
                        i4 = readInt3;
                    }
                    parsableByteArray.skipBytes(readInt3 - 12);
                }
            }
            if (!(str3 == null || str4 == null)) {
                if (i3 != -1) {
                    parsableByteArray.setPosition(i3);
                    parsableByteArray.skipBytes(16);
                    id3Frame = new InternalFrame(str3, str4, parsableByteArray.readNullTerminatedString(i4 - 16));
                }
            }
            parsableByteArray.setPosition(readInt);
            return id3Frame;
        }
        String valueOf = String.valueOf(a.getAtomTypeString(readInt2));
        if (valueOf.length() != 0) {
            str = "Skipped unknown metadata entry: ".concat(valueOf);
        } else {
            str = new String("Skipped unknown metadata entry: ");
        }
        Log.d("MetadataUtil", str);
        parsableByteArray.setPosition(readInt);
        return null;
    }

    @Nullable
    public static MdtaMetadataEntry parseMdtaMetadataEntryFromIlst(ParsableByteArray parsableByteArray, int i, String str) {
        while (true) {
            int position = parsableByteArray.getPosition();
            if (position >= i) {
                return null;
            }
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1684108385) {
                int readInt2 = parsableByteArray.readInt();
                int readInt3 = parsableByteArray.readInt();
                int i2 = readInt - 16;
                byte[] bArr = new byte[i2];
                parsableByteArray.readBytes(bArr, 0, i2);
                return new MdtaMetadataEntry(str, bArr, readInt3, readInt2);
            }
            parsableByteArray.setPosition(position + readInt);
        }
    }

    public static void setFormatGaplessInfo(int i, GaplessInfoHolder gaplessInfoHolder, Format.Builder builder) {
        if (i == 1 && gaplessInfoHolder.hasGaplessInfo()) {
            builder.setEncoderDelay(gaplessInfoHolder.a).setEncoderPadding(gaplessInfoHolder.b);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000b, code lost:
        if (r6 != null) goto L_0x003d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0040 A[LOOP:1: B:17:0x003e->B:18:0x0040, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setFormatMetadata(int r5, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.Metadata r6, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.Metadata r7, com.google.android.exoplayer2.Format.Builder r8, com.google.android.exoplayer2.metadata.Metadata... r9) {
        /*
            com.google.android.exoplayer2.metadata.Metadata r0 = new com.google.android.exoplayer2.metadata.Metadata
            r1 = 0
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r2 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r1]
            r0.<init>((com.google.android.exoplayer2.metadata.Metadata.Entry[]) r2)
            r2 = 1
            if (r5 != r2) goto L_0x000e
            if (r6 == 0) goto L_0x003c
            goto L_0x003d
        L_0x000e:
            r6 = 2
            if (r5 != r6) goto L_0x003c
            if (r7 == 0) goto L_0x003c
            r5 = 0
        L_0x0014:
            int r6 = r7.length()
            if (r5 >= r6) goto L_0x003c
            com.google.android.exoplayer2.metadata.Metadata$Entry r6 = r7.get(r5)
            boolean r3 = r6 instanceof com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry
            if (r3 == 0) goto L_0x0039
            com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry r6 = (com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry) r6
            java.lang.String r3 = r6.c
            java.lang.String r4 = "com.android.capture.fps"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0039
            com.google.android.exoplayer2.metadata.Metadata r5 = new com.google.android.exoplayer2.metadata.Metadata
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r7 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r2]
            r7[r1] = r6
            r5.<init>((com.google.android.exoplayer2.metadata.Metadata.Entry[]) r7)
            r6 = r5
            goto L_0x003d
        L_0x0039:
            int r5 = r5 + 1
            goto L_0x0014
        L_0x003c:
            r6 = r0
        L_0x003d:
            int r5 = r9.length
        L_0x003e:
            if (r1 >= r5) goto L_0x0049
            r7 = r9[r1]
            com.google.android.exoplayer2.metadata.Metadata r6 = r6.copyWithAppendedEntriesFrom(r7)
            int r1 = r1 + 1
            goto L_0x003e
        L_0x0049:
            int r5 = r6.length()
            if (r5 <= 0) goto L_0x0052
            r8.setMetadata(r6)
        L_0x0052:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.r6.setFormatMetadata(int, com.google.android.exoplayer2.metadata.Metadata, com.google.android.exoplayer2.metadata.Metadata, com.google.android.exoplayer2.Format$Builder, com.google.android.exoplayer2.metadata.Metadata[]):void");
    }
}
