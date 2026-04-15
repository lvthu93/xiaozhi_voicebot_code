package com.google.android.exoplayer2.extractor.jpeg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class a {
    public static final String[] a = {"Camera:MotionPhoto", "GCamera:MotionPhoto", "Camera:MicroVideo", "GCamera:MicroVideo"};
    public static final String[] b = {"Camera:MotionPhotoPresentationTimestampUs", "GCamera:MotionPhotoPresentationTimestampUs", "Camera:MicroVideoPresentationTimestampUs", "GCamera:MicroVideoPresentationTimestampUs"};
    public static final String[] c = {"Camera:MicroVideoOffset", "GCamera:MicroVideoOffset"};

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
        if (java.lang.Integer.parseInt(r9) == 1) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0067, code lost:
        if (r6 == -1) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0069, code lost:
        r6 = -9223372036854775807L;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription a(java.lang.String r22) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            org.xmlpull.v1.XmlPullParserFactory r0 = org.xmlpull.v1.XmlPullParserFactory.newInstance()
            org.xmlpull.v1.XmlPullParser r0 = r0.newPullParser()
            java.io.StringReader r1 = new java.io.StringReader
            r2 = r22
            r1.<init>(r2)
            r0.setInput(r1)
            r0.next()
            java.lang.String r1 = "x:xmpmeta"
            boolean r2 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r1)
            if (r2 == 0) goto L_0x00db
            com.google.common.collect.ImmutableList r2 = com.google.common.collect.ImmutableList.of()
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r5 = r3
        L_0x0027:
            r0.next()
            java.lang.String r7 = "rdf:Description"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            r8 = 0
            if (r7 == 0) goto L_0x00a7
            java.lang.String[] r2 = a
            r5 = 0
            r6 = 0
        L_0x0037:
            r7 = 4
            if (r6 >= r7) goto L_0x004a
            r9 = r2[r6]
            java.lang.String r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r9)
            if (r9 == 0) goto L_0x004c
            int r2 = java.lang.Integer.parseInt(r9)
            r6 = 1
            if (r2 != r6) goto L_0x004a
            goto L_0x004f
        L_0x004a:
            r6 = 0
            goto L_0x004f
        L_0x004c:
            int r6 = r6 + 1
            goto L_0x0037
        L_0x004f:
            if (r6 != 0) goto L_0x0052
            return r8
        L_0x0052:
            java.lang.String[] r2 = b
            r6 = 0
        L_0x0055:
            if (r6 >= r7) goto L_0x0069
            r9 = r2[r6]
            java.lang.String r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r9)
            if (r9 == 0) goto L_0x006b
            long r6 = java.lang.Long.parseLong(r9)
            r9 = -1
            int r2 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x006e
        L_0x0069:
            r6 = r3
            goto L_0x006e
        L_0x006b:
            int r6 = r6 + 1
            goto L_0x0055
        L_0x006e:
            java.lang.String[] r2 = c
        L_0x0070:
            r9 = 2
            if (r5 >= r9) goto L_0x00a1
            r9 = r2[r5]
            java.lang.String r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r9)
            if (r9 == 0) goto L_0x009e
            long r13 = java.lang.Long.parseLong(r9)
            com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription$ContainerItem r2 = new com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription$ContainerItem
            java.lang.String r16 = "image/jpeg"
            java.lang.String r17 = "Primary"
            r18 = 0
            r20 = 0
            r15 = r2
            r15.<init>(r16, r17, r18, r20)
            com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription$ContainerItem r5 = new com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription$ContainerItem
            java.lang.String r11 = "video/mp4"
            java.lang.String r12 = "MotionPhoto"
            r15 = 0
            r10 = r5
            r10.<init>(r11, r12, r13, r15)
            com.google.common.collect.ImmutableList r2 = com.google.common.collect.ImmutableList.of(r2, r5)
            goto L_0x00a5
        L_0x009e:
            int r5 = r5 + 1
            goto L_0x0070
        L_0x00a1:
            com.google.common.collect.ImmutableList r2 = com.google.common.collect.ImmutableList.of()
        L_0x00a5:
            r5 = r6
            goto L_0x00c8
        L_0x00a7:
            java.lang.String r7 = "Container:Directory"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x00b8
            java.lang.String r2 = "Container"
            java.lang.String r7 = "Item"
            com.google.common.collect.ImmutableList r2 = b(r0, r2, r7)
            goto L_0x00c8
        L_0x00b8:
            java.lang.String r7 = "GContainer:Directory"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x00c8
            java.lang.String r2 = "GContainer"
            java.lang.String r7 = "GContainerItem"
            com.google.common.collect.ImmutableList r2 = b(r0, r2, r7)
        L_0x00c8:
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r1)
            if (r7 == 0) goto L_0x0027
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x00d5
            return r8
        L_0x00d5:
            com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription r0 = new com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription
            r0.<init>(r5, r2)
            return r0
        L_0x00db:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Couldn't find xmp metadata"
            r0.<init>((java.lang.String) r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.jpeg.a.a(java.lang.String):com.google.android.exoplayer2.extractor.jpeg.MotionPhotoDescription");
    }

    public static ImmutableList<MotionPhotoDescription.ContainerItem> b(XmlPullParser xmlPullParser, String str, String str2) throws XmlPullParserException, IOException {
        long j;
        long j2;
        ImmutableList.Builder builder = ImmutableList.builder();
        String concat = str.concat(":Item");
        String concat2 = str.concat(":Directory");
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, concat)) {
                String concat3 = str2.concat(":Mime");
                String concat4 = str2.concat(":Semantic");
                String concat5 = str2.concat(":Length");
                String concat6 = str2.concat(":Padding");
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, concat3);
                String attributeValue2 = XmlPullParserUtil.getAttributeValue(xmlPullParser, concat4);
                String attributeValue3 = XmlPullParserUtil.getAttributeValue(xmlPullParser, concat5);
                String attributeValue4 = XmlPullParserUtil.getAttributeValue(xmlPullParser, concat6);
                if (attributeValue == null || attributeValue2 == null) {
                    return ImmutableList.of();
                }
                if (attributeValue3 != null) {
                    j = Long.parseLong(attributeValue3);
                } else {
                    j = 0;
                }
                if (attributeValue4 != null) {
                    j2 = Long.parseLong(attributeValue4);
                } else {
                    j2 = 0;
                }
                builder.add((Object) new MotionPhotoDescription.ContainerItem(attributeValue, attributeValue2, j, j2));
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, concat2));
        return builder.build();
    }

    @Nullable
    public static MotionPhotoDescription parse(String str) throws IOException {
        try {
            return a(str);
        } catch (ParserException | NumberFormatException | XmlPullParserException unused) {
            Log.w("MotionPhotoXmpParser", "Ignoring unexpected XMP metadata");
            return null;
        }
    }
}
