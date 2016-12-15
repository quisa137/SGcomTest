package com.sgcom;

import org.json.JSONObject;


public class JSONTest {
    public static void main(String[] args) {
        JSONObject json = new JSONObject("{\"_shards\":{\"total\":50,\"successful\":50,\"failed\":0},\"_all\":{\"primaries\":{\"docs\":{\"count\":207000,\"deleted\":0}},\"total\":{\"docs\":{\"count\":414000,\"deleted\":0}}},\"indices\":{\"baramtwit-2016.05.01\":{\"primaries\":{\"docs\":{\"count\":41400,\"deleted\":0}},\"total\":{\"docs\":{\"count\":82800,\"deleted\":0}}},\"baramtwit-2016.05.04\":{\"primaries\":{\"docs\":{\"count\":41400,\"deleted\":0}},\"total\":{\"docs\":{\"count\":82800,\"deleted\":0}}},\"baramtwit-2016.05.05\":{\"primaries\":{\"docs\":{\"count\":41400,\"deleted\":0}},\"total\":{\"docs\":{\"count\":82800,\"deleted\":0}}},\"baramtwit-2016.05.02\":{\"primaries\":{\"docs\":{\"count\":41400,\"deleted\":0}},\"total\":{\"docs\":{\"count\":82800,\"deleted\":0}}},\"baramtwit-2016.05.03\":{\"primaries\":{\"docs\":{\"count\":41400,\"deleted\":0}},\"total\":{\"docs\":{\"count\":82800,\"deleted\":0}}}}}");
        System.out.println(json.optJSONObject("indices").has("baramtwit-2016.05.18"));
    }
}
