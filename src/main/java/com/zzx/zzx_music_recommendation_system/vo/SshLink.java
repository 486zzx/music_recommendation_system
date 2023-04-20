package com.zzx.zzx_music_recommendation_system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/20 14:20
 */
@Data
public class SshLink {

    @ApiModelProperty("ip")
    private String host;

    @ApiModelProperty("端口")
    private Integer port;

    @ApiModelProperty("远程服务器用户名")
    private String userName;

    @ApiModelProperty("远程服务器密码")
    private String password;

    @ApiModelProperty("项目根目录")
    private String projectRootDirectory;

    @ApiModelProperty("命令执行根目录")
    private String commandExecutionDirectory;

}
