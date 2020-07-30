<#macro inputUserData path isRegisterForm isChangeForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name:</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid','')}" value="<#if user??>${user.username}</#if>" placeholder="User name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid','')}" value="<#if user??>${user.password}</#if>" placeholder="password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm||isChangeForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid','')}" value="<#if user??>${user.email}</#if>" placeholder="example@example.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <#if isRegisterForm>
            <button class="btn btn-primary" type="submit">Registration</button>
        </#if>
        <#if isChangeForm>
            <button class="btn btn-primary" type="submit">Save</button>
            <input type="hidden" name = "userId" value="${user.id}" placeholder="User ID">
        </#if>
        <#if !isRegisterForm&&!isChangeForm>
            <button class="btn btn-primary" type="submit">Sign In</button>
            <a href="/user/registration">Add new user</a>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>

<#assign known = Session.SPRING_SECURITY_CONTEXT?? >
<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()

    >
<#else>
    <#assign
    name = "Guest"
    isAdmin = false
    >
</#if>