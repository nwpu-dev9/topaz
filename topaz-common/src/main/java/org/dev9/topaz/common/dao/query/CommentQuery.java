package org.dev9.topaz.common.dao.query;

import org.dev9.topaz.common.dao.AbstractQuery;
import org.dev9.topaz.common.entity.Comment;

public class CommentQuery extends AbstractQuery<Comment> {
    @QueryWord(column = "comment_id", type = MatchType.EQUAL)
    private Integer commentIdEqual;

    @QueryWord(column = "content", type = MatchType.LIKE)
    private String contentLike;

    @QueryWord(column = "audited", type = MatchType.EQUAL)
    private Boolean auditedEqual;

    public CommentQuery() { }

    public void setCommentIdEqual(Integer commentIdEqual) {
        this.commentIdEqual = commentIdEqual;
    }

    public void setContentLike(String contentLike) {
        this.contentLike = contentLike;
    }

    public void setAuditedEqual(Boolean auditedEqual) {
        this.auditedEqual = auditedEqual;
    }
}
