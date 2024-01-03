package com.selfdot.battlepass.quest.listener;

import com.selfdot.battlepass.quest.Quest;

import java.util.HashSet;
import java.util.Set;

public abstract class QuestEventListener<T extends Quest> {

    protected final Set<T> listeners = new HashSet<>();

    public void add(T t) {
        listeners.add(t);
    }

    public void remove(T t) {
        listeners.remove(t);
    }

    public abstract void register();

}
