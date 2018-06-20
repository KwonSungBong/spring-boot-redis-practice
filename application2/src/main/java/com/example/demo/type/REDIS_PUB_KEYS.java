package com.example.demo.type;

import com.example.demo.util.StringUtils;

public enum REDIS_PUB_KEYS {
    UI_TRADE_PUB("UI_TRADE")
	,UI_WITHDRAW_PUB("UI_CANCEL")
	,UI_REST("UI_RESET");

    private String key;

    REDIS_PUB_KEYS(String key) {
        this.key = key;
    }

    public String getKey() {
    	return key;
    }

    public String getKey(String baseCrncy) {
        return StringUtils.replace(key, "{baseCrncy}", baseCrncy);
    }

    public String getKey(String baseCrncy, String crncy) {
        return StringUtils.replace(getKey(baseCrncy), "{crncy}", crncy);
    }

    public String getAllPattern() {
        return getKey("*", "*");
    }
}
