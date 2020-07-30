<#import "parts/common.ftl" as common>
<#import "parts/auth.ftl" as login>

<@common.page>
    <#if RequestParameters.error?? && Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            User not correct!
        </div>
    </#if>
    <@login.inputUserData "/user/login" false false/>
</@common.page>