package com.pozpl.nerannotator.ner.impl.dao.model.user;

import com.pozpl.neraannotator.user.api.UserIntDto;

import java.util.Objects;

public class UserId {
    private Long id;

    public UserId(Long id) {
        this.id = id;
    }


    public UserId() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static UserId of(Long userId){
        return new UserId(userId);
    }

    public static UserId of(UserIntDto user){
        if(user != null && user.getId() != 0){
            return new UserId(user.getId());
        }else{
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId that = (UserId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserId{" +
                "id=" + id +
                '}';
    }
}
