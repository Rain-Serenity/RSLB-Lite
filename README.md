# RSLB Lite

Minecraft Paper/Folia 插件，实现 Mojang 正版登录与 Yggdrasil 外置登录（如 LittleSkin）共存。

## 功能

- **双登录源共存**：正版玩家与外置登录玩家可在同一服务器内共存
- **皮肤修复**：通过 MineSkin 将外置登录皮肤转换为正版签名纹理
- **Profile Key 校验控制**：可关闭客户端公钥校验，兼容 LittleSkin 等外置登录
- **用户名正则约束**：可配置正则表达式过滤不合规用户名
- **配置热重载**：`/rslb reload` 无需重启即可生效

## 命令

| 命令                    | 说明          | 权限                  |
|-----------------------|-------------|---------------------|
| `/rslb help`          | 显示帮助列表      | `rslb.base`         |
| `/rslb info`          | 查看自己的登录档案   | `rslb.info.oneself` |
| `/rslb info <player>` | 查看指定玩家的登录档案 | `rslb.info.other`   |
| `/rslb reload`        | 重载配置文件      | `rslb.reload`       |

## 权限节点

| 节点                  | 默认   | 说明       |
|---------------------|------|----------|
| `rslb.base`         | true | 基础命令权限   |
| `rslb.tab.complete` | true | Tab 补全权限 |
| `rslb.reload`       | op   | 重载配置     |
| `rslb.info.oneself` | op   | 查询自己     |
| `rslb.info.other`   | op   | 查询他人     |

## 配置

### config.yml

```yaml
settings:
  debug: false              # 调试日志
  welcome-message: true     # 欢迎消息
  profile-key-verify: false # 客户端公钥校验（false=不校验，兼容外置登录）
  name-allowed-regular: '^[0-9a-zA-Z_]{3,16}$'  # 用户名正则
  metrics-enabled: true     # bStats 统计
```

### services/

每个验证服务一个 YAML 文件：

- `official.yml` - Mojang 正版验证
- `littleskin.yml` - LittleSkin 外置登录

配置项包括：`id`、`name`、`serviceType`、`initUUID`、`initNameFormat`、`skinRestorer`、`yggdrasilAuth` 等。

## 构建

```bash
./gradlew build
```

输出：`build/libs/RSLB-Lite-x.x-SNAPSHOT-all.jar`

## 环境要求

- Java 25+
- Paper/Folia 26.3
