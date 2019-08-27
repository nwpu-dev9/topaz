package org.dev9.topaz.common.dao.query;

import org.dev9.topaz.common.dao.AbstractQuery;
import org.dev9.topaz.common.entity.Topic;

import java.util.List;

public class TopicQuery extends AbstractQuery<Topic> {
    @QueryWord(column = "title", type = MatchType.LIKE)
    private List<String> titleLikeList;

    @QueryWord(column = "content", type = MatchType.LIKE)
    private List<String> contentLikeList;

    public List<String> getTitleLikeList() {
        return titleLikeList;
    }

    public void setTitleLikeList(List<String> titleLikeList) {
        this.titleLikeList = titleLikeList;
    }

    public List<String> getContentLikeList() {
        return contentLikeList;
    }

    public void setContentLikeList(List<String> contentLikeList) {
        this.contentLikeList = contentLikeList;
    }
}
