package org.schabi.newpipe.extractor.comments;

import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.Description;

public class CommentsInfoItem extends InfoItem {
    public String j;
    public Description k = Description.g;
    public String l;
    public List<Image> m = Collections.emptyList();
    public String n;
    public boolean o;
    public String p;
    public DateWrapper q;
    public int r;
    public String s;
    public boolean t;
    public boolean u;
    public int v;
    public int w;
    public Page x;
    public boolean y;
    public boolean z;

    public CommentsInfoItem(int i, String str, String str2) {
        super(InfoItem.InfoType.COMMENT, i, str, str2);
    }

    public String getCommentId() {
        return this.j;
    }

    public Description getCommentText() {
        return this.k;
    }

    public int getLikeCount() {
        return this.r;
    }

    public Page getReplies() {
        return this.x;
    }

    public int getReplyCount() {
        return this.w;
    }

    public int getStreamPosition() {
        return this.v;
    }

    public String getTextualLikeCount() {
        return this.s;
    }

    public String getTextualUploadDate() {
        return this.p;
    }

    public DateWrapper getUploadDate() {
        return this.q;
    }

    public List<Image> getUploaderAvatars() {
        return this.m;
    }

    public String getUploaderName() {
        return this.l;
    }

    public String getUploaderUrl() {
        return this.n;
    }

    public boolean hasCreatorReply() {
        return this.z;
    }

    public boolean isChannelOwner() {
        return this.y;
    }

    public boolean isHeartedByUploader() {
        return this.t;
    }

    public boolean isPinned() {
        return this.u;
    }

    public boolean isUploaderVerified() {
        return this.o;
    }

    public void setChannelOwner(boolean z2) {
        this.y = z2;
    }

    public void setCommentId(String str) {
        this.j = str;
    }

    public void setCommentText(Description description) {
        this.k = description;
    }

    public void setCreatorReply(boolean z2) {
        this.z = z2;
    }

    public void setHeartedByUploader(boolean z2) {
        this.t = z2;
    }

    public void setLikeCount(int i) {
        this.r = i;
    }

    public void setPinned(boolean z2) {
        this.u = z2;
    }

    public void setReplies(Page page) {
        this.x = page;
    }

    public void setReplyCount(int i) {
        this.w = i;
    }

    public void setStreamPosition(int i) {
        this.v = i;
    }

    public void setTextualLikeCount(String str) {
        this.s = str;
    }

    public void setTextualUploadDate(String str) {
        this.p = str;
    }

    public void setUploadDate(DateWrapper dateWrapper) {
        this.q = dateWrapper;
    }

    public void setUploaderAvatars(List<Image> list) {
        this.m = list;
    }

    public void setUploaderName(String str) {
        this.l = str;
    }

    public void setUploaderUrl(String str) {
        this.n = str;
    }

    public void setUploaderVerified(boolean z2) {
        this.o = z2;
    }
}
