package defpackage;

import com.grack.nanojson.JsonObject;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: jg  reason: default package */
public final class jg extends mf {
    public final JsonObject b;
    public final JsonObject c;

    public jg(JsonObject jsonObject) {
        super(jsonObject);
        this.b = jsonObject.getObject("shortBylineText");
        this.c = jsonObject.getObject("longBylineText");
    }

    public String getUploaderName() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.c);
        if (Utils.isNullOrEmpty(textFromObject)) {
            textFromObject = YoutubeParsingHelper.getTextFromObject(this.b);
            if (Utils.isNullOrEmpty(textFromObject)) {
                throw new ParsingException("Could not get uploader name");
            }
        }
        return textFromObject;
    }

    public String getUploaderUrl() throws ParsingException {
        String urlFromObject = YoutubeParsingHelper.getUrlFromObject(this.c);
        if (urlFromObject != null || (urlFromObject = YoutubeParsingHelper.getUrlFromObject(this.b)) != null) {
            return urlFromObject;
        }
        throw new ParsingException("Could not get uploader URL");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
