<#import "parts/common.ftl" as common>

<@common.page>
    <div>User editor</div>
    <form action="/user" method="post">

        <input type="hidden" name = "userId" value="${user.id}" placeholder="User ID">
        <div>
            <input type="text" name = "username" value="${user.username}" placeholder="User name">
        </div>
        <div>
            <input type="text" name = "email" value="${user.email?if_exists}" placeholder="Email">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#list roles as role>
            <div>
                <label>
                    ${role} <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                </label>
            </div>
        </#list>
        <button type="submit">Save</button>


    </form>
</@common.page>

