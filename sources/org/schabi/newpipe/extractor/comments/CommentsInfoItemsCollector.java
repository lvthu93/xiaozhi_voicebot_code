package org.schabi.newpipe.extractor.comments;

import java.util.ArrayList;
import java.util.List;
import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public final class CommentsInfoItemsCollector extends InfoItemsCollector<CommentsInfoItem, CommentsInfoItemExtractor> {
    public CommentsInfoItemsCollector(int i) {
        super(i);
    }

    public List<CommentsInfoItem> getCommentsInfoItemList() {
        return new ArrayList(super.getItems());
    }

    public CommentsInfoItem extract(CommentsInfoItemExtractor commentsInfoItemExtractor) throws ParsingException {
        CommentsInfoItem commentsInfoItem = new CommentsInfoItem(getServiceId(), commentsInfoItemExtractor.getUrl(), commentsInfoItemExtractor.getName());
        try {
            commentsInfoItem.setCommentId(commentsInfoItemExtractor.getCommentId());
        } catch (Exception e) {
            a(e);
        }
        try {
            commentsInfoItem.setCommentText(commentsInfoItemExtractor.getCommentText());
        } catch (Exception e2) {
            a(e2);
        }
        try {
            commentsInfoItem.setUploaderName(commentsInfoItemExtractor.getUploaderName());
        } catch (Exception e3) {
            a(e3);
        }
        try {
            commentsInfoItem.setUploaderAvatars(commentsInfoItemExtractor.getUploaderAvatars());
        } catch (Exception e4) {
            a(e4);
        }
        try {
            commentsInfoItem.setUploaderUrl(commentsInfoItemExtractor.getUploaderUrl());
        } catch (Exception e5) {
            a(e5);
        }
        try {
            commentsInfoItem.setTextualUploadDate(commentsInfoItemExtractor.getTextualUploadDate());
        } catch (Exception e6) {
            a(e6);
        }
        try {
            commentsInfoItem.setUploadDate(commentsInfoItemExtractor.getUploadDate());
        } catch (Exception e7) {
            a(e7);
        }
        try {
            commentsInfoItem.setLikeCount(commentsInfoItemExtractor.getLikeCount());
        } catch (Exception e8) {
            a(e8);
        }
        try {
            commentsInfoItem.setTextualLikeCount(commentsInfoItemExtractor.getTextualLikeCount());
        } catch (Exception e9) {
            a(e9);
        }
        try {
            commentsInfoItem.setThumbnails(commentsInfoItemExtractor.getThumbnails());
        } catch (Exception e10) {
            a(e10);
        }
        try {
            commentsInfoItem.setHeartedByUploader(commentsInfoItemExtractor.isHeartedByUploader());
        } catch (Exception e11) {
            a(e11);
        }
        try {
            commentsInfoItem.setPinned(commentsInfoItemExtractor.isPinned());
        } catch (Exception e12) {
            a(e12);
        }
        try {
            commentsInfoItem.setStreamPosition(commentsInfoItemExtractor.getStreamPosition());
        } catch (Exception e13) {
            a(e13);
        }
        try {
            commentsInfoItem.setReplyCount(commentsInfoItemExtractor.getReplyCount());
        } catch (Exception e14) {
            a(e14);
        }
        try {
            commentsInfoItem.setReplies(commentsInfoItemExtractor.getReplies());
        } catch (Exception e15) {
            a(e15);
        }
        try {
            commentsInfoItem.setChannelOwner(commentsInfoItemExtractor.isChannelOwner());
        } catch (Exception e16) {
            a(e16);
        }
        try {
            commentsInfoItem.setCreatorReply(commentsInfoItemExtractor.hasCreatorReply());
        } catch (Exception e17) {
            a(e17);
        }
        return commentsInfoItem;
    }

    public void commit(CommentsInfoItemExtractor commentsInfoItemExtractor) {
        try {
            this.a.add(extract(commentsInfoItemExtractor));
        } catch (Exception e) {
            a(e);
        }
    }
}
