# MDITTER v1.4

MDITTER 是一款专为 Minecraft Java版 开发的轻量化 Fabric 模组，提供数据包拦截功能。

## 核心功能
* **灵魂出窍 (SOUL MODE)**：开启后拦截玩家移动包（PlayerMoveC2SPacket），在保持服务器连接的同时允许玩家视角自由移动。
* **数据保护**：拦截除 KeepAliveC2SPacket 以外的所有关键数据包，防止在操作期间被服务器拉回。
* **假身同步**：开启功能时会在原位置生成一个客户端侧的同步假身，方便确认原始坐标。
* **双语支持**：内置简体中文与英文界面。

## 运行环境
* **游戏版本**：不限制
* **加载器**：Fabric Loader (>=0.15.11)
* **依赖项**：Fabric API (>=0.83.0+1.20.1)

## 使用说明
1. 进入游戏后，按下键盘 `J` 键（默认按键）打开 MDITTER 控制面板。
2. 后续操作已准备说明

## 开发信息
* **作者**：koponat
* **许可证**：GPL-3.0
* **项目地址**：[https://github.com/koponat/mditter](https://github.com/koponat/mditter)
* **项目地址**：mditter .jar文件下载: https://github.com/koponat/mditter/releases


旧版使用ui右边的shift 新版使用键盘J键
