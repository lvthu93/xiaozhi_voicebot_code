package org.schabi.newpipe.extractor.comments;

import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.Description;

public interface CommentsInfoItemExtractor extends InfoItemExtractor {
    String getCommentId() throws ParsingException;

    Description getCommentText() throws ParsingException;

    int getLikeCount() throws ParsingException;

    Page getReplies() throws ParsingException;

    int getReplyCount() throws ParsingException;

    int getStreamPosition() throws ParsingException;

    String getTextualLikeCount() throws ParsingException;

    String getTextualUploadDate() throws ParsingException;

    DateWrapper getUploadDate() throws ParsingException;

    List<Image> getUploaderAvatars() throws ParsingException;

    String getUploaderName() throws ParsingException;

    String getUploaderUrl() throws ParsingException;

    boolean hasCreatorReply() throws ParsingException;

    boolean isChannelOwner() throws ParsingException;

    boolean isHeartedByUploader() throws ParsingException;

    boolean isPinned() throws ParsingException;

    boolean isUploaderVerified() throws ParsingException;
}
