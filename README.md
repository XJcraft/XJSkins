# XJCraft皮肤切换插件

## 功能

- 切换为正版玩家的皮肤
- 恢复默认皮肤

## 安装

### 前置条件

- 服务端`online-mode`为`false`
- 依赖插件 [protocollib](https://www.spigotmc.org/resources/protocollib.1997/)

## 指令
### 普通玩家
`/skin change 皮肤名` -- 切换为正版皮肤

`/skin revert` -- 恢复默认皮肤

### op
`/skina change 玩家 皮肤名` -- 改变一个玩家的皮肤

`/skina revert 玩家` 将一个玩家的皮肤恢复为默认

## 原理

参照 https://wiki.vg/Mojang_API#UUID_-.3E_Profile_.2B_Skin.2FCape

1. 通过`nickname`获取`uuid` （*`https://api.mojang.com/profiles/minecraft`*）
2. 通过`uuid`获取`textures`[皮肤和披风数据] （*`https://sessionserver.mojang.com/session/minecraft/profile/<uuid>`*）
3. 将`textures`注入到Player Properties
