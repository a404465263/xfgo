# xfgo
Fate/Grand Order B服过sign检测Xposed模块

作者: LocBytes

当前游戏客户端版本: 1.21.2

## 说明

B服FGO经测试无法在VirtualXposed中运行，并非本模块造成的问题。

Android版本需求: >=4.2.2

Xposed API需求: >=54

经测试可以通过[Magisk](https://forum.xda-developers.com/apps/magisk)以框中框的方式来使用本模块。

## 使用方法

1、安装并应用此模块。

2、在科技服务端将`response`的`sign`改为`""`即可(由科技开发者进行操作)。

```json
{
    "response": {},
    "cache": {},
    "sign": ""
}
```
