package com.totu.service.parser;


public abstract class AbstractParser {

    public abstract void parse(String url);

    protected abstract void parseListPage(String url);

    protected abstract void parseItem(String url);


}
