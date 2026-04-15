package org.schabi.newpipe.extractor.services.peertube;

import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class PeertubeInstance {
    public static final PeertubeInstance c = new PeertubeInstance("https://framatube.org", "FramaTube");
    public final String a;
    public String b;

    public PeertubeInstance(String str) {
        this.a = str;
        this.b = "PeerTube";
    }

    public void fetchInstanceMetaData() throws Exception {
        String str = this.a;
        try {
            Downloader downloader = NewPipe.getDownloader();
            Response response = downloader.get(str + "/api/v1/config");
            if (response == null || Utils.isBlank(response.responseBody())) {
                throw new Exception("unable to configure instance " + str);
            }
            try {
                this.b = JsonUtils.getString(JsonParser.object().from(response.responseBody()), "instance.name");
            } catch (JsonParserException | ParsingException e) {
                throw new Exception("unable to parse instance config", e);
            }
        } catch (IOException | ReCaptchaException e2) {
            throw new Exception("unable to configure instance " + str, e2);
        }
    }

    public String getName() {
        return this.b;
    }

    public String getUrl() {
        return this.a;
    }

    public PeertubeInstance(String str, String str2) {
        this.a = str;
        this.b = str2;
    }
}
