package web.enums;

import org.jetbrains.annotations.Nullable;

public enum WebSocketSignalTypeEnum {
  // 登录
  login,
  // 加入房间
  userJoin,
  // 创建房间
  createRoom,
  // 心跳
  heart,
  // 同步时间
  syncTime,
  listRoom,
  listUser,
  /**
   * [,userId]
   */
  userLeft,
  /**
   * [,toUserId,dspInfo or ice]
   */
  signal,
  ping,
  pong;
}
