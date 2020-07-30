<#import "parts/common.ftl" as common>
<#import "parts/auth.ftl" as login>

<@common.page>
    ${message?ifExists}
    <@login.inputUserData "/user/profile" false true/>
</@common.page>