package com.gamekeys.gameshop.constant;

import static com.gamekeys.gameshop.constant.SecurityConstant.SERVER_CONTEXT_PATH;

public class FileConstant {
    public static final String USER_IMAGE_PATH = "/api/user/image/";
    public static final String JPG_EXTENSION = "jpg";
    public static final String USER_FOLDER = System.getProperty("user.home") + "/gameshop/user/"; // regardless of the user system, it will create the folder in the user home
    public static final String DIRECTORY_CREATED = "Created directory for: ";
    public static final String DEFAULT_USER_IMAGE_PATH = SERVER_CONTEXT_PATH + "user/image/profile/";
    //public static final String DEFAULT_USER_IMAGE_PATH = SERVER_CONTEXT_PATH + "/image/profile/"; //"/image/profile/"
    public static final String FILE_SAVED_IN_FILE_SYSTEM = "Saved file in file system by name: ";
    public static final String DOT = ".";
    public static final String FORWARD_SLASH = "/";
    public static final String NOT_AN_IMAGE_FILE = " is not an image file. Please upload an image file";
    public static final String TEMP_PROFILE_IMAGE_BASE_URL = "https://robohash.org/";
}
