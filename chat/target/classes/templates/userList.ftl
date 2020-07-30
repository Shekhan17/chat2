<#import "parts/common.ftl" as common>

<@common.page>
    <table class="table">
        <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Email</th>
                <th scope="col">Role</th>
            </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>
                    <a href="/user/${user.id}/">
                        ${user.getId()}
                    </a>
                </td>
                <td>${user.getUsername()}</td>
                <td>${user.getEmail()?if_exists}</td>
                <td>
                    <#list user.roles as role>
                        ${role}<#sep>,
                    </#list>
                </td>
            </tr>
        <#else>
            <div>Users not found!</div>
        </#list>
        </tbody>
    </table>

</@common.page>

