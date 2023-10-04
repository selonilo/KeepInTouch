package com.salih.todo.controller;

public abstract class BaseController {
    static final String API = "/api";
    static final String AUTH = API + "/auth";
    static final String PUBLIC = API + "/public";

    //auth
    static final String AUTH_LOGIN = AUTH + "/login";
    static final String AUTH_LOGOUT = AUTH + "/logout";
    static final String AUTH_REGISTER = AUTH + "/register";
    static final String AUTH_REFRESH_TOKEN = AUTH + "/refreshtoken";
    static final String PUBLIC_AUTH_DELETE = PUBLIC + "/auth/delete";
    static final String PUBLIC_AUTH_GETALL = PUBLIC + "/auth/getAll";
    static final String PUBLIC_AUTH_UPDATE = PUBLIC + "/auth/update";
    static final String PUBLIC_AUTH_SAVE = PUBLIC + "/auth/save";

    //post
    static final String PUBLIC_FIND_ALL_POST = PUBLIC + "/post/getAll";
    static final String PUBLIC_SAVE_POST = PUBLIC + "/post/save";
    static final String PUBLIC_DELETE_POST = PUBLIC + "/post/delete";
    static final String PUBLIC_UPDATE_POST = PUBLIC + "/post/update";
    static final String PUBLIC_GET_BY_USER_ID = PUBLIC + "/post";


}
