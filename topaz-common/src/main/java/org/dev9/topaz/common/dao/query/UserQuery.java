package org.dev9.topaz.common.dao.query;

import org.dev9.topaz.common.dao.AbstractQuery;
import org.dev9.topaz.common.entity.User;

import java.util.List;

public class UserQuery extends AbstractQuery<User> {
    @QueryWord(column = "user_id", type = MatchType.EQUAL)
    private Integer userIdEqual;

    @QueryWord(column = "phone_number", type = MatchType.LIKE)
    private String phoneNumberLike;

    @QueryWord(column = "name", type = MatchType.LIKE)
    private String nameLike;

    @QueryWord(column = "profile", type = MatchType.LIKE)
    private String profileLike;

    public UserQuery(){}

    public void setUserIdEqual(Integer userIdEqual) {
        this.userIdEqual = userIdEqual;
    }

    public void setPhoneNumberLike(String phoneNumberLike) {
        this.phoneNumberLike = phoneNumberLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public void setProfileLike(String profileLike) {
        this.profileLike = profileLike;
    }
}
