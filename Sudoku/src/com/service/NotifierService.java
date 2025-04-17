package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifierService {
    //atributo
    private Map<EventEnum, List<EventListener>> listeners = new HashMap<>(){{
        put(EventEnum.CLEAR_SPACE, new ArrayList<>());
    }};

    //metodo
    public void subscribe(final EventEnum eventType, EventListener listener){
        List<EventListener> selectedListener = listeners.get(eventType);
        selectedListener.add(listener);
    }

    public void nofify(final EventEnum eventType){
        listeners.get(eventType).forEach(l -> l.update(eventType));
    }
}
