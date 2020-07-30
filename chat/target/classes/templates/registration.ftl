<#import "parts/common.ftl" as common>
<#import "parts/auth.ftl" as registation>

<@common.page>
    <div class="mb-1">Add new user</div>
    <#if message ??> ${message}</#if>
    <@registation.inputUserData "/user/registration" true false/>
</@common.page>