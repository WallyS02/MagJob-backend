package com.keepitup.magjobbackend.configuration;

public class Constants {
    // Role Names
    public static final String ROLE_NAME_OWNER = "Owner";
    public static final String ROLE_NAME_MODERATOR = "Moderator";
    public static final String ROLE_NAME_MEMBER = "Member";
    public static final String[] DEFAULT_ROLE_NAMES = {ROLE_NAME_OWNER, ROLE_NAME_MODERATOR, ROLE_NAME_MEMBER};

    // Permission names
    public static final String PERMISSION_NAME_CAN_MANAGE_TASKS = "canManageTasks";
    public static final String PERMISSION_NAME_CAN_MANAGE_ANNOUNCEMENTS = "canManageAnnouncements";
    public static final String PERMISSION_NAME_CAN_MANAGE_INVITATIONS = "canManageInvitations";
    public static final String PERMISSION_NAME_CAN_MANAGE_ROLES = "canManageRoles";

    //Chats
    public static final String CHAT_DEFAULT_WEBSOCKET_ENDPOINT = "/topic/chat";

    public static final String CHAT_JOIN_MESSAGE = "Chat member %s has joined chat";
    public static final String CHAT_LEAVE_MESSAGE = "Chat member %s has left chat";
    public static final String CHAT_ADD_ADMIN_MESSAGE = "Chat member %s has been granted administrator privileges";
    public static final String CHAT_DELETE_ADMIN_MESSAGE = "Chat member %s is no longer administrator in this chat";
}
