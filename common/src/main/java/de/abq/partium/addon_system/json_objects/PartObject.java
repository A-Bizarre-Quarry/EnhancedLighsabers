package de.abq.partium.addon_system.json_objects;

import java.util.List;
import java.util.Locale;

public class PartObject {
    String id;
    Type type;
    List<String> cost;
;
    private enum Type{
        BLADE("blade"),
        EMITTER("emitter"),
        GUARD("guard"),
        GRIP("grip"),
        POMMEL("pommel"),
        ;
        Type(String emitter) {
        }
    }

    public PartObject(String id, String type){
        this.cost = null;
        this.id = id;
        this.type = Type.valueOf(type.toLowerCase());
    }
}


