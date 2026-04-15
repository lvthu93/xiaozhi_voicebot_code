package org.schabi.newpipe.extractor.exceptions;

public class YoutubeMusicPremiumContentException extends ContentNotAvailableException {
    public YoutubeMusicPremiumContentException() {
        super("This video is a YouTube Music Premium video");
    }

    public YoutubeMusicPremiumContentException(Throwable th) {
        super("This video is a YouTube Music Premium video", th);
    }
}
