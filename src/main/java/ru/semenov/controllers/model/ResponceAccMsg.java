package ru.semenov.controllers.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ResponceAccMsg {
    @Getter
    @Setter
    private Data data;

    public static class Data {
        @Getter
        @Setter
        private Integer accountId;
    }

    public ResponceAccMsg(Integer accountId) {
        this.data = new Data();
        this.data.setAccountId(accountId);
    }
}
