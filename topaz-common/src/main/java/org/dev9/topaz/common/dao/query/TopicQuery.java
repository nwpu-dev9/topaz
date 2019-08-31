package org.dev9.topaz.common.dao.query;

import org.dev9.topaz.common.dao.AbstractQuery;
import org.dev9.topaz.common.entity.Topic;

public class TopicQuery extends AbstractQuery<Topic> {
    @QueryWord(column = "topic_id", type = MatchType.EQUAL)
    private Integer topicIdEqual;

    @QueryWord(column = "title", type = MatchType.LIKE)
    private String titleLike;

    @QueryWord(column = "content", type = MatchType.LIKE)
    private String contentLike;

    public TopicQuery() { }

    public void setTopicIdEqual(Integer topicIdEqual) {
        this.topicIdEqual = topicIdEqual;
    }

    public void setTitleLike(String titleLike) {
        this.titleLike = titleLike;
    }

    public void setContentLike(String contentLike) {
        this.contentLike = contentLike;
    }
}
