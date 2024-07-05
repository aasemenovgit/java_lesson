package ru.semenov.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ModelAu {
    @Getter
    @Setter
    List <List<String>> automod;

    public ModelAu(List<List<String>> automod) {
        this.automod = automod;
    }

    @Override
    public String toString() {
        return "Model{" +
                "automod=" + automod +
                '}';
    }
}
